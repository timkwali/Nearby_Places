package com.adyen.android.assignment.nearbyplaces.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.adyen.android.assignment.R
import com.adyen.android.assignment.common.data.cache.model.Place
import com.adyen.android.assignment.common.utils.OnItemClick
import com.adyen.android.assignment.common.utils.Resource
import com.adyen.android.assignment.common.utils.Utils.showSnackBar
import com.adyen.android.assignment.databinding.FragmentPlacesBinding
import com.adyen.android.assignment.nearbyplaces.presentation.adpater.PlacesListRVAdapter
import com.adyen.android.assignment.nearbyplaces.utils.Constants
import com.adyen.android.assignment.nearbyplaces.utils.LocationService
import com.adyen.android.assignment.nearbyplaces.utils.Utils
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class PlacesFragment : Fragment(), OnItemClick<Place> {
    private var _binding: FragmentPlacesBinding? = null
    private val binding: FragmentPlacesBinding get() = _binding!!
    private val placesViewModel: PlacesViewModel by viewModels()
    private lateinit var placesListRVAdapter: PlacesListRVAdapter
    private lateinit var locationService: LocationService
    private var intentLaunched = false

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationService = LocationService(this)
        locationService.registerLocationPermission({ locationSetup() }, { permissionSetup() })
        if(savedInstanceState == null) {
            locationService.requestLocationPermission()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            placesListRVAdapter = PlacesListRVAdapter(this@PlacesFragment)
            retryBtn.setOnClickListener { checkLocation() }
            swipeSrl.setOnRefreshListener { checkLocation() }
            swipeSrl.setColorSchemeResources(R.color.blue)
            setUpSearch()

            lifecycleScope.launchWhenStarted {
                placesViewModel.placesList.collectLatest {
                    swipeSrl.isRefreshing = it is Resource.Loading
                    emptyListTv.text = it.message
                    emptyListTv.isVisible = it is Resource.Error && it.data.isNullOrEmpty()
                    retryBtn.isVisible = it is Resource.Error && it.data.isNullOrEmpty()
                    placesListRv.isVisible = it !is Resource.Loading
                    if(it is Resource.Error) {
                        showSnackBar("\uD83D\uDE28 Wooops ${it.message}")
                    }
                    if(it.data != null) {
                        placesListRVAdapter.submitList(it.data)
                        placesListRv.adapter = placesListRVAdapter
                        placesListRv.setHasFixedSize(true)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(intentLaunched) {
            intentLaunched = false
            checkLocation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(item: Place, position: Int) {
        val bundle = bundleOf(Constants.ID to item.id)
        findNavController().navigate(R.id.placeDetailsFragment, bundle)
    }

    private fun checkLocation() {
        if (locationService.isLocationPermissionGranted()) {
            locationSetup()
        } else {
            permissionSetup()
        }
    }

    private fun turnOnLocation() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
        intentLaunched = true
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationService.getLastLocation {
            getData()
        }
    }

    private fun getData() {
        placesViewModel.latitude = locationService.latitude
        placesViewModel.longitude = locationService.longitude
        placesViewModel.getPlaces()
    }

    val locationSetup = {
        if(locationService.isLocationOn()) getLocation() else {
            Utils.showAlertDialog(
                requireActivity(),
                getString(R.string.location_not_on),
                getString(R.string.turn_on_location),
                { turnOnLocation() }, { binding.swipeSrl.isRefreshing = false }
            )
        }
    }

    val permissionSetup = {
        Utils.showAlertDialog(
                requireActivity(),
                getString(R.string.permission_not_granted),
                getString(R.string.request_permission),
                { locationService.requestLocationPermission() }, { requireActivity().finish() }
            )
    }

    private fun setUpSearch() {
        binding.apply {
            searchSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    searchPlaces(p0.toString())
                    return false
                }
            })
        }
    }

    private fun searchPlaces(searchQuery: String) {
        val filterList = placesViewModel.placesList.value.data?.filter {
            it.name?.contains(searchQuery.trim(), true) ?: false
        }
        placesListRVAdapter.submitList(filterList)
    }
}