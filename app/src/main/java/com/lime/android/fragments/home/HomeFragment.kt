package com.lime.android.fragments.home

import android.os.Bundle
import android.view.View
import com.lime.android.R
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment

class HomeFragment: BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureUI(view)
    }

    private fun configureUI(view: View) {
        view.apply {
            (activity as MainActivity).apply {
                setTitle(getString(R.string.dashboard))
                setBackButtonVisibility(View.GONE)
                setMenuVisibility(View.VISIBLE)
            }
        }
    }
}
