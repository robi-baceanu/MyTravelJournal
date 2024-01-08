package com.example.mytraveljournal.ui.add

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import com.example.mytraveljournal.data.Trip
import com.example.mytraveljournal.data.TripViewModel
import com.example.mytraveljournal.databinding.FragmentAddBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.InputStream

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null

    private val binding get() = _binding!!

    private val galleryImageRequestCode: Int = 3

    private lateinit var mTripViewModel: TripViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mTripViewModel = ViewModelProvider(this).get(TripViewModel::class.java)

        val addImageButton: FloatingActionButton = binding.buttonAddImage
        addImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, galleryImageRequestCode)
        }

        val addTripButton: Button = binding.buttonAddTrip
        addTripButton.setOnClickListener {
            insertDataToDatabase()
        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == galleryImageRequestCode) {

            val pictureImageView: ImageView = binding.imageViewAddPicture

            val inputStream: InputStream? = context?.contentResolver?.openInputStream(data?.data!!)

            val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

            pictureImageView.setImageBitmap(bitmap)
        }
    }

    private fun insertDataToDatabase() {
        val locationEditText: EditText = binding.editTextLocation
        val pictureImageView: ImageView = binding.imageViewAddPicture
        val datePicker: DatePicker = binding.datePicker
        val descriptionEditText: EditText = binding.editTextDescription

        val location: String = locationEditText.text.toString()
        val day: Int = datePicker.dayOfMonth
        val month: Int = datePicker.month + 1
        val year: Int = datePicker.year
        val description: String = descriptionEditText.text.toString()
        val picture: Bitmap = pictureImageView.drawable.toBitmap()

        if (inputCheck(location, description)) {
            val trip = Trip(0, location, day, month, year, description, picture)
            mTripViewModel.addTrip(trip)
            Toast.makeText(this.context, "Trip added successfully!", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        } else {
            Toast.makeText(this.context, "Please fill all fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(location: String, description: String): Boolean {
        return !(location.isEmpty() || description.isEmpty())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}