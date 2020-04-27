package com.lime.android.fragments.orderhistory.completedorder

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lime.android.R
import com.lime.android.fragments.orderhistory.OrderItemDecoration
import com.lime.android.fragments.orderhistory.OrderListAdapter
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment
import com.lime.android.ui.customview.emptybox.EmptyBox

class CompletedOrderFragment: BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_orders_list
    private val viewModel: CompletedOrderViewHolder by lazy {
        obtainViewModel {
            CompletedOrderViewHolder(requireContext())
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNavigationListener(viewModel.navigationLiveDataField)
        (activity as MainActivity).setTitle(getString(R.string.history_details))
        setViewModelObserver(view)
    }

    private fun setViewModelObserver(view: View) {
        viewModel.run {
            view.apply {
                listOfRides.observe(viewLifecycleOwner, Observer {
                    view.apply {
                        findViewById<EmptyBox>(R.id.tv_empty_order)?.apply {
                            visibility =  if(it.isNullOrEmpty()) View.VISIBLE else View.GONE
                            setEmptyText(getString(R.string.no_completed_order))
                        }
                        findViewById<RecyclerView>(R.id.rcv_order_list).apply {
                            layoutManager = LinearLayoutManager(requireContext(),
                                RecyclerView.VERTICAL,false)
                            addItemDecoration(OrderItemDecoration(resources.getDimension(R.dimen.dim_15_dp).toInt()))
                            it?.let {

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
    }
}
