package com.example.mytraveljournal.ui.edit

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.mytraveljournal.R
import com.example.mytraveljournal.data.Trip
import com.example.mytraveljournal.data.TripViewModel
import com.example.mytraveljournal.databinding.FragmentEditBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.InputStream

class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null

    private val binding get() = _binding!!

    private val galleryImageRequestCode: Int = 3

    private val args by navArgs<EditFragmentArgs>()

    private lateinit var mTripViewModel: TripViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setHasOptionsMenu(true)

        mTripViewModel = ViewModelProvider(this).get(TripViewModel::class.java)

        val locationEditText: EditText = binding.editTextLocationEdit
        locationEditText.setText(args.currentTrip.location)

        val pictureImageView: ImageView = binding.imageViewEditPicture
        pictureImageView.setImageBitmap(args.currentTrip.picture)

        val datePicker: DatePicker = binding.datePickerEdit
        datePicker.updateDate(args.currentTrip.year, args.currentTrip.month - 1, args.currentTrip.day)

        val descriptionEditText: EditText = binding.editTextDescriptionEdit
        descriptionEditText.setText(args.currentTrip.description)

        val editImageButton: FloatingActionButton = binding.buttonEditImage
        editImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, galleryImageRequestCode)
        }

        val editTripButton: Button = binding.buttonEditTrip
        editTripButton.setOnClickListener {
            updateItem()
        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == galleryImageRequestCode) {

            val pictureImageView: ImageView = binding.imageViewEditPicture

            val inputStream: InputStream? = context?.contentResolver?.openInputStream(data?.data!!)

            val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

            pictureImageView.setImageBitmap(bitmap)
        }
    }

    private fun updateItem() {
        val locationEditText: EditText = binding.editTextLocationEdit
        val pictureImageView: ImageView = binding.imageViewEditPicture
        val datePicker: DatePicker = binding.datePickerEdit
        val descriptionEditText: EditText = binding.editTextDescriptionEdit

        val location: String = locationEditText.text.toString()
        val day: Int = datePicker.dayOfMonth
        val month: Int = datePicker.month + 1
        val year: Int = datePicker.year
        val description: String = descriptionEditText.text.toString()
        val picture: Bitmap = pictureImageView.drawable.toBitmap()

        if (inputCheck(location, description)) {
            val updatedTrip = Trip(args.currentTrip.id, location, day, month, year, description, picture)
            mTripViewModel.updateTrip(updatedTrip)
            Toast.makeText(this.context, "Trip edited successfully!", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        } else {
            Toast.makeText(this.context, "Please fill all fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(location: String, description: String): Boolean {
        return !(location.isEmpty() || description.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteTrip()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteTrip() {
        val builder = AlertDialog.Builder(this.context)
        builder.setPositiveButton("Yes") { _, _ ->
            mTripViewModel.deleteTrip(args.currentTrip)
            Toast.makeText(this.context, "Trip deleted successfully!", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete trip to ${args.currentTrip.location}")
        builder.setMessage("Are you sure you want to delete this trip?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}