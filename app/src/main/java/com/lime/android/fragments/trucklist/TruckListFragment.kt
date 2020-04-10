package com.lime.android.fragments.trucklist

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lime.android.R
import com.lime.android.fragments.home.HomeViewModel
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment

class TruckListFragment: BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_truck_list
    private val viewModel by lazy {
        obtainViewModel {
            TruckListViewModel()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNavigationListener(viewModel.navigationLiveDataField)
        configureUI(view)
    }

    private fun configureUI(view: View) {
        (activity as MainActivity).apply {
            setTitle(getString(R.string.list_of_truck))
            setBackButtonVisibility(View.VISIBLE)
            setMenuVisibility(View.GONE)
        }
        view.apply {
            findViewById<RecyclerView>(R.id.rcv_truck_list).apply {
                layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                adapter = TruckListAdapter(requireContext())
            }

            findViewById<Button>(R.id.btn_proceed).setOnClickListener { viewModel.onClickProceed() }
        }

    }
}
