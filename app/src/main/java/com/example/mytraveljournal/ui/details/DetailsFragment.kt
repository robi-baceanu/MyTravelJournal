package com.example.mytraveljournal.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mytraveljournal.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null

    private val binding get() = _binding!!

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val locationTextView: TextView = binding.textViewLocationDetails
        locationTextView.text = args.currentTrip.location

        val pictureImageView: ImageView = binding.imageViewDetailsPicture
        pictureImageView.setImageBitmap(args.currentTrip.picture)

        val dateTextView: TextView = binding.textViewDateDetails
        val day = args.currentTrip.day
        val month = args.currentTrip.month
        val year = args.currentTrip.year
        val dateMessage = "Visited on $day/$month/$year"
        dateTextView.text = dateMessage

        val descriptionTextView: TextView = binding.textViewDescriptionDetails
        descriptionTextView.text = args.currentTrip.description

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}