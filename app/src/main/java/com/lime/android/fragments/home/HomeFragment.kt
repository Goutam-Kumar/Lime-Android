package com.lime.android.fragments.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.lime.android.R
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment


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
            HomeViewModel()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNavigationListener(viewModel.navigationLiveDataField)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        configureUI(view)
        if(!Places.isInitialized())
            Places.initialize(requireContext(), getString(R.string.api_key));
        placesClient = Places.createClient(requireContext())

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
            findViewById<RecyclerView>(R.id.rcv_vehicle_category).apply {
                layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
                addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.dim_10_dp).toInt()))
                adapter = VehicleCategoryAdapter(
                    {position: Int ->  
                        viewModel.onVehicleClicked(position)
                    },requireContext()
                )
            }
            findViewById<Button>(R.id.btn_continue).apply {
                setOnClickListener { viewModel.onContinueClicked() }
            }

        }
    }

    private fun onSearchCalled() {
        view.let {
            val fields: List<Place.Field> = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
            val intent: Intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).setCountry("IN").build(requireContext())
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
                        if (pickUpLin  == currentClickedView)
                            view?.findViewById<TextView>(R.id.tv_pickup)?.text = address
                        else if (dropInLin == currentClickedView)
                            view?.findViewById<TextView>(R.id.tv_drop)?.text =  address
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
            val sydney = LatLng(20.5937, 78.9629)
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in India"))
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        sydney.latitude,
                        sydney.longitude
                    ), 22.0f
                )
            )
            uiSettings.apply {
                isMyLocationButtonEnabled = true
                setAllGesturesEnabled(true)
            }
        }

    }
}
