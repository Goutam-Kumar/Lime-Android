package com.lime.android.fragments.bidconfirmation

import android.os.Bundle
import android.view.View
import com.lime.android.R
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment

class BidConfirmationFragment : BaseFragment(){
    override val layoutResourceId: Int = R.layout.fragment_bid_confirmation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureUI(view)
    }

    private fun configureUI(view: View) {
        view.apply {
            (activity as MainActivity).apply {
                setTitle(getString(R.string.bid_conf))
                setBackButtonVisibility(View.VISIBLE)
                setMenuVisibility(View.GONE)
            }
        }
    }
}
