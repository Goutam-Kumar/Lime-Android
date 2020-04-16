package com.lime.android.fragments.trucklist

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lime.android.R
import com.lime.android.fragments.home.HomeViewModel
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment
import com.lime.android.util.LimeUtils

class TruckListFragment: BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_truck_list
    private val viewModel by lazy {
        obtainViewModel {
            TruckListViewModel(requireArguments(),requireContext())
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNavigationListener(viewModel.navigationLiveDataField)
        configureUI(view)
        setViewModelObservers()
        viewModel.getVehicles()

    }

    private fun setViewModelObservers() {
        viewModel.run {
            spinner.observe(viewLifecycleOwner, Observer { handleSpinner(it) })
            serviceException.observe(viewLifecycleOwner, Observer { LimeUtils.showServiceError(it, requireContext()) })
            vehicleList.observe(viewLifecycleOwner, Observer {
                view?.apply {
                    findViewById<RecyclerView>(R.id.rcv_truck_list).apply {
                        layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                        it?.let {
                            adapter = TruckListAdapter(
                                {vehicle ->
                                    viewModel.selectedVehicle = vehicle
                                },requireContext(),it,viewModel.distance)
                        }
                    }
                }
            })
        }
    }

    private fun handleSpinner(showSpinner: Boolean) = if (showSpinner) showSpinner(isCancellable = false, isTransparent = false) else hideSpinner()

    private fun configureUI(view: View) {
        (activity as MainActivity).apply {
            setTitle(getString(R.string.estimated_fare))
            setBackButtonVisibility(View.VISIBLE)
            setMenuVisibility(View.GONE)
        }
        view.apply {
            findViewById<Button>(R.id.btn_proceed).setOnClickListener { viewModel.onClickProceed() }
        }

    }
}
