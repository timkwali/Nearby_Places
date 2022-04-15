package com.adyen.android.assignment.nearbyplaces.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.adyen.android.assignment.databinding.FragmentPlacesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlacesFragment : Fragment() {
    private var _binding: FragmentPlacesBinding? = null
    private val binding: FragmentPlacesBinding get() = _binding!!
    private val placesViewModel: PlacesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        placesViewModel.getPlaces()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}