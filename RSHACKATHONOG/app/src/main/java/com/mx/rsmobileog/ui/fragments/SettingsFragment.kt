package com.mx.rsmobileog.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mx.rsmobileog.databinding.SettingsFragmentBinding

class SettingsFragment : Fragment() {

    // View Binding
    private lateinit var bindingView: SettingsFragmentBinding

    companion object {
        fun getInstance() = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate layout
        bindingView = SettingsFragmentBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        return bindingView.root
    }
}