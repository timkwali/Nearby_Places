package com.adyen.android.assignment.placedetails.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.adyen.android.assignment.R
import com.adyen.android.assignment.common.data.api.model.Category
import com.adyen.android.assignment.common.data.cache.model.Place
import com.adyen.android.assignment.common.utils.Resource
import com.adyen.android.assignment.databinding.FragmentPlaceDetailsBinding
import com.adyen.android.assignment.nearbyplaces.utils.Constants
import com.adyen.android.assignment.placedetails.presentation.adapter.CategoryRvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PlaceDetailsFragment : Fragment() {

    private var _binding: FragmentPlaceDetailsBinding? = null
    private val binding: FragmentPlaceDetailsBinding get() = _binding!!
    private val placeDetailsViewModel: PlaceDetailsViewModel by activityViewModels()
    private var categoriesList: List<Category> = emptyList()
    private lateinit var categoryRvAdapter: CategoryRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        placeDetailsViewModel.placeId = arguments?.getInt(Constants.ID) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaceDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if(savedInstanceState == null) {
                placeDetailsViewModel.getPlaceById()
            }
            backIb.setOnClickListener { findNavController().popBackStack() }
            retryBtn.setOnClickListener { placeDetailsViewModel.getPlaceById() }
            categoryRvAdapter = CategoryRvAdapter(categoriesList)

            lifecycleScope.launchWhenStarted {
                placeDetailsViewModel.place.collectLatest{
                    progressBarPb.isVisible = it is Resource.Loading
                    placeDetailsCl.isVisible = it is Resource.Success && it.data != null
                    retryBtn.isVisible = it is Resource.Error || (it is Resource.Success && it.data == null)
                    emptyListTv.isVisible = it is Resource.Error || (it is Resource.Success && it.data == null)
                    emptyListTv.text = it.message
                    if(it.data == null) emptyListTv.text = getString(R.string.failed_to_get_place_data)
                    if(it.data != null) {
                        Log.d("ldfka", it.data.toString())
                        setUpUI(it.data)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpUI(place: Place) {
        binding.apply {
            placeNameTv.text = place.name
            distanceTv.text = "${place.distance}${getString(R.string.m_away)}"
            addressValueTv.text = place.address
            timezoneTv.text = place.timeZone
            latitudeTv.text = "${place.coordinates?.latitude} ${getString(R.string.latitude)}"
            longitudeTv.text = "${place.coordinates?.longitude} ${getString(R.string.longitude)}"
            categoriesList = place.categories ?: emptyList()
            categoryRvAdapter.categoryList = categoriesList
            categoriesRv.adapter = categoryRvAdapter
            categoriesRv.setHasFixedSize(true)
        }
    }
}