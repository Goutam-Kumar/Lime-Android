package com.lime.android.fragments.orderhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.lime.android.R
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment
import com.lime.android.util.LimeUtils

class OrderHistoryFragment: BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_order_history

    private val viewModel: OrderHistoryViewModel by lazy {
        obtainViewModel {
            OrderHistoryViewModel()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNavigationListener(viewModel.navigationLiveDataField)
        configureUI(view)
        setViewModelObservers(view)
    }

    private fun setViewModelObservers(view: View) {
        view.apply {

        }
    }

    private fun configureUI(view: View) {
        view.apply {
            (activity as MainActivity).apply {
                setTitle(getString(R.string.history_details))
                setBackButtonVisibility(View.GONE)
                setMenuVisibility(View.VISIBLE)
            }
            val tabAdapter = OrderHistoryAdapter(requireContext(),requireFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )
            val jobViewPager = findViewById<ViewPager>(R.id.view_pager_order)
            jobViewPager.adapter = tabAdapter
            findViewById<TabLayout>(R.id.tab_layout_order).apply {
                setupWithViewPager(jobViewPager)
                for(i in 0..this.tabCount){
                    val tavText = LayoutInflater.from(requireContext()).inflate(R.layout.layout_custom_tablayout,null)
                    tavText.findViewById<TextView>(R.id.tv_tab_name).text = if(i == 0) getString(R.string.in_progress) else getString(R.string.completed)
                    getTabAt(i)?.customView = tavText
                }
            }
        }
    }
}
