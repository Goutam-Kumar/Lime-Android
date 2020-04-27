package com.lime.android.fragments.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lime.android.R
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment
import com.lime.android.util.GLOBAL_TAG
import com.lime.android.util.LimeUtils

private const val AUTO_COMPLETE_REQ_CODE: Int = 2000
private const val TAG: String = "AutoCompleteResult"

class HomeFragment: BaseFragment(),OnMapReadyCallback {
    private var placesClient: PlacesClient? = null
    private lateinit var mMap: GoogleMap
    private var currentClickedView: View? = null
    private lateinit var pickUpLin: LinearLayout
    private lateinit var dropInLin: LinearLayout

    override val layoutResourceId: Int = R.layout.fragment_home
    private val viewModel by lazy {
        obtainViewModel {
            HomeViewModel(requireContext())
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNavigationListener(viewModel.navigationLiveDataField)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        configureUI(view)
        setViewModelObservers()
        if(!Places.isInitialized())
            Places.initialize(requireContext(), getString(R.string.api_key));
        placesClient = Places.createClient(requireContext())
        viewModel.getVehicleCategories()
    }

    private fun setViewModelObservers() {
        viewModel.run {
            spinner.observe(viewLifecycleOwner, Observer { handleSpinner(it) })
            serviceException.observe(viewLifecycleOwner, Observer { LimeUtils.showServiceError(it, requireContext()) })
            vehicleTypes.observe(viewLifecycleOwner, Observer {
                view?.apply {
                    findViewById<RecyclerView>(R.id.rcv_vehicle_category).apply {
                        layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
                        addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.dim_10_dp).toInt()))
                        adapter = VehicleCategoryAdapter(
                            {position: Int, vehicleId: Int ->
                                viewModel.onVehicleClicked(position, vehicleId)
                            },requireContext(), it
                        )
                    }
                }
            })
        }
    }

    private fun configureUI(view: View) {
        view.apply {
            (activity as MainActivity).apply {
                setTitle(getString(R.string.dashboard))
                setBackButtonVisibility(View.GONE)
                setMenuVisibility(View.VISIBLE)
            }
            pickUpLin = findViewById(R.id.lin_pickup)
            dropInLin = findViewById(R.id.lin_drop)
            pickUpLin.apply {
                setOnClickListener {
                    currentClickedView = pickUpLin
                    onSearchCalled()
                }
            }
            dropInLin.apply {
                setOnClickListener {
                    currentClickedView = dropInLin
                    onSearchCalled()
                }
            }

            findViewById<Button>(R.id.btn_continue).apply {
                setOnClickListener { viewModel.onContinueClicked() }
            }

            findViewById<FloatingActionButton>(R.id.fab_current_location).setOnClickListener {
            }
        }
    }

    private fun onSearchCalled() {
        view.let {
            val fields: List<Place.Field> = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
            val intent: Intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).setCountry("MWI").build(requireContext())
            startActivityForResult(intent,AUTO_COMPLETE_REQ_CODE)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data.let {
            if (requestCode == AUTO_COMPLETE_REQ_CODE){
                when (resultCode) {
                    AutocompleteActivity.RESULT_OK -> {
                        val place = Autocomplete.getPlaceFromIntent(data!!)
                        Log.i(TAG, "Place: " + place.name + ", " + place.id + ", " + place.address)
                        val address = place.address
                        if (pickUpLin  == currentClickedView){
                            view?.findViewById<TextView>(R.id.tv_pickup)?.text = address
                            viewModel.pickupLat = place.latLng?.latitude ?: 0.0
                            viewModel.pickupLng = place.latLng?.longitude ?: 0.0
                            viewModel.pickUpAddress = address
                        }
                        else if (dropInLin == currentClickedView){
                            view?.findViewById<TextView>(R.id.tv_drop)?.text =  address
                            viewModel.dropLat = place.latLng?.latitude ?: 0.0
                            viewModel.dropLng = place.latLng?.longitude ?: 0.0
                            viewModel.dropAddress = address
                        }
                    }
                    AutocompleteActivity.RESULT_ERROR -> {
                        val status = Autocomplete.getStatusFromIntent(data!!)
                        Toast.makeText(requireContext(), "Error: " + status.statusMessage, Toast.LENGTH_LONG).show()
                        Log.i(TAG, status.statusMessage.orEmpty())
                    }
                    AutocompleteActivity.RESULT_CANCELED -> {
                        // The user canceled the operation.
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.apply {
            try{
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(),R.raw.lime_map_style))
            }catch (e: Resources.NotFoundException){
                Log.e(GLOBAL_TAG,"Raw resource not found!")
            }
            val sydney = LatLng(-13.254308, 34.301525)
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            mMap.isTrafficEnabled = true
            mMap.addMarker(MarkerOptions().position(sydney).title("Malawi"))

            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        sydney.latitude,
                        sydney.longitude
                    ), 17.0f
                )
            )
            uiSettings.apply {
                isMyLocationButtonEnabled = true
                setAllGesturesEnabled(true)
            }
        }
    }

    private fun handleSpinner(showSpinner: Boolean) = if (showSpinner) showSpinner(isCancellable = false, isTransparent = false) else hideSpinner()
}
