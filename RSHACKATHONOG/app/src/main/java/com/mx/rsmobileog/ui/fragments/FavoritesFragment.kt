package com.mx.rsmobileog.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mx.rsmobileog.databinding.FavoritesFragmentBinding

class FavoritesFragment : Fragment() {

    // View Binding
    private lateinit var bindingView: FavoritesFragmentBinding

    companion object {
        fun getInstance() = FavoritesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate layout
        bindingView = FavoritesFragmentBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        return bindingView.root
    }
}