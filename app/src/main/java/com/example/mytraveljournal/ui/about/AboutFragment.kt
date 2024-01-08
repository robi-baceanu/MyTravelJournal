package com.example.mytraveljournal.ui.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mytraveljournal.R
import com.example.mytraveljournal.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textViewTitle: TextView = binding.textViewTitle
        textViewTitle.text = getString(R.string.app_name)

        val textViewVersion: TextView = binding.textViewVersion
        textViewVersion.text = getString(R.string.version)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}