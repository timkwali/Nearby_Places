package com.adyen.android.assignment.nearbyplaces.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.adyen.android.assignment.R
import com.adyen.android.assignment.common.data.cache.model.Place
import com.adyen.android.assignment.common.utils.OnItemClick
import com.adyen.android.assignment.common.utils.Resource
import com.adyen.android.assignment.databinding.FragmentPlacesBinding
import com.adyen.android.assignment.nearbyplaces.presentation.adpater.PlacesListRVAdapter
import com.adyen.android.assignment.nearbyplaces.utils.DialogHelper
import com.adyen.android.assignment.nearbyplaces.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import android.content.Intent
import android.location.Location
import android.provider.Settings;

import android.os.Looper
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.adyen.android.assignment.common.data.api.model.Category
import com.adyen.android.assignment.common.data.api.model.Icon
import com.adyen.android.assignment.common.data.api.model.Main
import com.adyen.android.assignment.common.utils.Utils.showSnackBar
import com.adyen.android.assignment.nearbyplaces.utils.Constants
import com.google.android.gms.location.*


@AndroidEntryPoint
class PlacesFragment : Fragment(), OnItemClick<Place>, DialogHelper {
    private var _binding: FragmentPlacesBinding? = null
    private val binding: FragmentPlacesBinding get() = _binding!!
    private val placesViewModel: PlacesViewModel by viewModels()
    private var placesList = listOf<Place>()
    private lateinit var placesListRVAdapter: PlacesListRVAdapter
    private lateinit var locationPermissionRequest: ActivityResultLauncher<String>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var intentLaunched = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
//        registerLocationPermission()
//        requestLocationPermission()
    }

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

        binding.apply {
            placesListRVAdapter = PlacesListRVAdapter(this@PlacesFragment)

//            placesListRVAdapter.submitList(list)
//            placesListRv.adapter = placesListRVAdapter
//            placesListRv.setHasFixedSize(true)

            retryBtn.setOnClickListener { placesViewModel.getPlaces() }
            swipeSrl.setOnRefreshListener { placesViewModel.getPlaces() }
            swipeSrl.setColorSchemeResources(R.color.blue)

            lifecycleScope.launchWhenStarted {
                placesViewModel.placesList.collectLatest {
                    swipeSrl.isRefreshing = it is Resource.Loading
                    emptyListTv.text = it.message
                    emptyListTv.isVisible = it is Resource.Error
                    retryBtn.isVisible = it is Resource.Error
                    placesListRv.isVisible = it !is Resource.Loading
                    if(it is Resource.Error) {
                        showSnackBar("\uD83D\uDE28 Wooops ${it.message}")
                    }
                    if(it.data != null) {
                        placesList = it.data
                        placesListRVAdapter.submitList(placesList)
                        placesListRv.adapter = placesListRVAdapter
                        placesListRv.setHasFixedSize(true)
                    }
                }
            }
        }
    }

    val list =  listOf(
        Place(id=2, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
        Place(id=9, name="Foam Fotografiemuseum Amsterdam (Foam)", distance=38.0, address="Keizersgracht 609 1017 DS Amsterdam, North Holland", timeZone="Europe/Amsterdam", coordinates=Main(latitude=52.364049, longitude=4.893104), categories=listOf(Category(icon=Icon(prefix="https://ss3.4sqi.net/img/categories_v2/arts_entertainment/museum_art_", suffix=".png"), id="10028", name="Art Museum"))),
    )

    override fun onResume() {
        super.onResume()
//        if(intentLaunched) {
//            intentLaunched = false
//            if (checkLocationPermission()) {
//                Log.d("ldkfaf", "permcheck-> TRUE")
//
//                if(isLocationOn()) getLastLocation() else {
//                    showDialog(getString(R.string.location_not_on), getString(R.string.turn_on_location), LOCATION_TAG)
//                }
//            } else {
//                Log.d("ldkfaf", "permcheck-> FALSE")
//                showDialog(getString(R.string.permission_not_granted), getString(R.string.request_permission), PERMISSION_TAG)
//            }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(item: Place, position: Int) {
        val bundle = bundleOf(Constants.ID to item.id)
        findNavController().navigate(R.id.placeDetailsFragment, bundle)
    }

    override fun onOkClick(tag: Int) {
        when(tag) {
            PERMISSION_TAG -> requestLocationPermission()
            LOCATION_TAG -> turnOnLocation()
        }
    }

    override fun onCancelClick(tag: Int) {
        when(tag) {
            PERMISSION_TAG -> requireActivity().finish()
            LOCATION_TAG -> {}
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    }

    private fun showDialog(title: String, message: String, tag: Int) {
        Utils.showAlertDialog(
            requireActivity(),
            title, message,
            tag, this
        )
    }


    private fun registerLocationPermission() {
        if (checkLocationPermission()) {
            locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    if(isLocationOn()) getLastLocation() else turnOnLocation()
                } else {
                    showDialog(
                        getString(R.string.permission_not_granted),
                        getString(R.string.request_permission),
                        PERMISSION_TAG
                    )
                }
            }
        }
    }

    private fun requestLocationPermission() {
        locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun isLocationOn(): Boolean {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun turnOnLocation() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
        intentLaunched = true
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation
            .addOnCompleteListener { task ->
                val location: Location? = task.result
                if (location == null) {
                    requestNewLocationData()
                } else {
//                    placesViewModel.latitude = location.latitude
//                    placesViewModel.longitude = location.longitude
                    Toast.makeText(requireContext(), "latlong from lastlocation ${location.latitude} ${location.longitude}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 5
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        Looper.myLooper()?.let {
            fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, it)
        }
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
//            placesViewModel.latitude = lastLocation.latitude
//            placesViewModel.longitude = lastLocation.longitude
            Toast.makeText(requireContext(), "lotlong from callback-> ${lastLocation.latitude} ${lastLocation.longitude}", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val PERMISSION_TAG = 1
        const val LOCATION_TAG = 2
    }
}