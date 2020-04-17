package com.lime.android.fragments.orderdetails

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import com.lime.android.R
import com.lime.android.datarepository.DataHolder
import com.lime.android.fragments.offerbid.OfferBidViewModel
import com.lime.android.models.vehicles.Vehicle
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment
import kotlin.math.roundToInt

class OrderDetailsFragment: BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_order_details
    private val viewModel by lazy {
        obtainViewModel {
            OrderDetailsViewModel(requireArguments(), requireContext())
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNavigationListener(viewModel.navigationLiveDataField)
        configureUI(view)
        setViewModelObservers(view)
    }

    private fun setViewModelObservers(view: View) {
        viewModel.run {
            limeDataHolder.observe(viewLifecycleOwner, Observer {
                drawConfirmationLayout(view, it)
            })
        }
    }

    private fun drawConfirmationLayout(view: View, dataHolder: DataHolder?) {
        view.apply {
            dataHolder?.let {
                val vehicle = it.vehicle
                vehicle?.let {
                    findViewById<TextView>(R.id.tv_price).text =
                        context.getString(R.string.currency).plus(" ").plus((vehicle.per_km_price!!.toFloat() * dataHolder.distance).roundToInt() )
                    findViewById<TextView>(R.id.tv_fare).text =
                        context.getString(R.string.currency).plus(" ").plus((vehicle.per_km_price.toFloat() * dataHolder.distance).roundToInt() )
                    findViewById<TextView>(R.id.tv_pickup).text = dataHolder.pickUpAddress
                    findViewById<TextView>(R.id.tv_drop).text = dataHolder.dropAddress
                    findViewById<TextView>(R.id.tv_vehicle).text = vehicle.model
                    findViewById<TextView>(R.id.tv_weight).text = vehicle.vehicle_load
                    findViewById<TextView>(R.id.tv_service_type).text = vehicle.engine_type
                }
            }
        }
    }

    private fun configureUI(view: View) {
        view.apply {
            (activity as MainActivity).apply {
                setTitle(getString(R.string.confirmation))
                setBackButtonVisibility(View.VISIBLE)
                setMenuVisibility(View.GONE)
            }

            findViewById<Button>(R.id.btn_continue).setOnClickListener { viewModel.onContinueClick() }
        }
    }
}
