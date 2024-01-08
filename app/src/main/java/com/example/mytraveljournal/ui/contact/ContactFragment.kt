package com.example.mytraveljournal.ui.contact

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mytraveljournal.databinding.FragmentContactBinding

class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val githubTextView: TextView = binding.textViewGithub
        githubTextView.movementMethod = LinkMovementMethod.getInstance()

        val linkedinTextView: TextView = binding.textViewLinkedin
        linkedinTextView.movementMethod = LinkMovementMethod.getInstance()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}