package com.lime.android.fragments.orderhistory.currentorder

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lime.android.R
import com.lime.android.fragments.home.MarginItemDecoration
import com.lime.android.fragments.orderhistory.OrderItemDecoration
import com.lime.android.fragments.orderhistory.OrderListAdapter
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment
import com.lime.android.ui.customview.emptybox.EmptyBox
import com.lime.android.util.LimeUtils

class CurrentOrderFragment: BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_orders_list
    private val viewModel: CurrentOrderViewModel by lazy {
        obtainViewModel {
            CurrentOrderViewModel(requireContext())
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNavigationListener(viewModel.navigationLiveDataField)
        setViewModelObserver(view)
        (activity as MainActivity).setTitle(getString(R.string.history_details))
        viewModel.getCurrentOrders()
    }

    private fun setViewModelObserver(view: View) {
        viewModel.run {
            spinner.observe(viewLifecycleOwner, Observer { handleSpinner(it) })
            serviceException.observe(viewLifecycleOwner, Observer { LimeUtils.showServiceError(it, requireContext()) })
            listOfRides.observe(viewLifecycleOwner, Observer {
                view.apply {
                    findViewById<RecyclerView>(R.id.rcv_order_list).apply {
                        layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                        addItemDecoration(OrderItemDecoration(resources.getDimension(R.dimen.dim_15_dp).toInt()))
                        it?.let {
                            findViewById<EmptyBox>(R.id.tv_empty_order)?.visibility =  if(it.isNullOrEmpty()) View.VISIBLE else View.GONE
                            adapter = OrderListAdapter(
                                {ride->
                                    //TODO do the adapter click event
                                },requireContext(),it
                            )
                        }
                    }
                }
            })
        }
    }
    private fun handleSpinner(showSpinner: Boolean) = if (showSpinner) showSpinner(isCancellable = false, isTransparent = false) else hideSpinner()

}
