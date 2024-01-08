package com.example.mytraveljournal.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.mytraveljournal.R
import com.example.mytraveljournal.databinding.FragmentSettingsBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textViewLight: TextView = binding.textViewLight
        textViewLight.text = getString(R.string.light)

        val textViewDark: TextView = binding.textViewDark
        textViewDark.text = getString(R.string.dark)

        val textViewSelect: TextView = binding.textViewSelect
        textViewSelect.text = getString(R.string.select_theme)

        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        val sharedPrefEditor = sharedPreferences?.edit()

        val switchTheme: SwitchMaterial = binding.switchTheme
        val darkThemeOn: Boolean? = sharedPreferences?.getBoolean("isDarkTheme", false)
        if (darkThemeOn!!)
            switchTheme.isChecked = true
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharedPrefEditor?.apply {
                    putBoolean("isDarkTheme", true)
                    apply()
                }
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else {
                sharedPrefEditor?.apply {
                    putBoolean("isDarkTheme", false)
                    apply()
                }
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}