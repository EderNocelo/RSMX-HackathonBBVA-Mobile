package com.mx.rsmobileog.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mx.rsmobileog.databinding.OffersFragmentBinding

class OffersNearFragment : Fragment() {

    // View Binding
    private lateinit var bindingView: OffersFragmentBinding

    companion object {
        fun getInstance() = OffersNearFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate layout
        bindingView = OffersFragmentBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        return bindingView.root
    }
}