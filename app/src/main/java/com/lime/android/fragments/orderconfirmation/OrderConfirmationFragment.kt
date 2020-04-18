package com.lime.android.fragments.orderconfirmation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.lime.android.OrderConfirmationDestination
import com.lime.android.OrderDetailsDestination
import com.lime.android.R
import com.lime.android.fragments.billingdetails.BillingDetailsViewModel
import com.lime.android.fragments.home.HomeViewModel
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment
import com.lime.android.util.LimeUtils
import kotlinx.android.synthetic.main.fragment_order_confirmation.view.*
import kotlin.math.roundToInt

class OrderConfirmationFragment: BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_order_confirmation
    private val viewModel by lazy {
        obtainViewModel {
            OrderConfirmationViewModel(requireContext(),requireArguments())
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureUI(view)
        setViewModelObservers(view)
    }

    private fun setViewModelObservers(view: View) {
        viewModel.run {
            data.observe(viewLifecycleOwner, Observer {
                view.apply {
                    findViewById<TextView>(R.id.tv_order_id).text = "Order ID: ".plus("#").plus(
                        OrderConfirmationDestination.getBookingID(requireArguments()))
                    findViewById<TextView>(R.id.tv_order_dt).text = "Date: ".plus(LimeUtils.getTodaysDate())
                    val dataHolder = it
                    val vehicle = dataHolder?.vehicle
                    vehicle?.let {
                        findViewById<TextView>(R.id.tv_fare).text =
                            context.getString(R.string.currency).plus(" ").plus((vehicle.per_km_price!!.toFloat() * dataHolder.distance).roundToInt() )
                        findViewById<TextView>(R.id.tv_pickup).text = dataHolder.pickUpAddress
                        findViewById<TextView>(R.id.tv_drop).text = dataHolder.dropAddress
                        findViewById<TextView>(R.id.tv_vehicle).text = vehicle.model
                        findViewById<TextView>(R.id.tv_weight).text = vehicle.vehicle_load
                        findViewById<TextView>(R.id.tv_service_type).text = vehicle.engine_type
                    }
                }
            })
        }
    }

    private fun configureUI(view: View) {
        view.apply {
            (activity as MainActivity).apply {
                setTitle(getString(R.string.order_summary))
                setBackButtonVisibility(View.VISIBLE)
                setMenuVisibility(View.GONE)
            }

            findViewById<ImageView>(R.id.img_success).apply {
                LimeUtils.blinkAnimation(this)
            }
        }
    }
}
