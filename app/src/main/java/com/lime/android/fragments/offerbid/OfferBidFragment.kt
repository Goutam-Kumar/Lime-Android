package com.lime.android.fragments.offerbid

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.lime.android.R
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment

class OfferBidFragment: BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_offer_bid
    private val viewModel by lazy {
        obtainViewModel {
            OfferBidViewModel()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNavigationListener(viewModel.navigationLiveDataField)
        configureUI(view)
    }

    private fun configureUI(view: View) {
        view.apply {
            (activity as MainActivity).apply {
                setTitle(getString(R.string.bid_amount))
                setBackButtonVisibility(View.VISIBLE)
                setMenuVisibility(View.GONE)
            }

            findViewById<Button>(R.id.btn_send_bid).setOnClickListener { viewModel.onSendBidClicked() }
        }
    }
}
