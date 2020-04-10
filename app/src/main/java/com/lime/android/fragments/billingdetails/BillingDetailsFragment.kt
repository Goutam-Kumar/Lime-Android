package com.lime.android.fragments.billingdetails

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.lime.android.R
import com.lime.android.fragments.offerbid.OfferBidViewModel
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment

class BillingDetailsFragment: BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_billing_details
    private val viewModel by lazy {
        obtainViewModel {
            BillingDetailsViewModel()
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
                setTitle(getString(R.string.billing_details))
                setBackButtonVisibility(View.VISIBLE)
                setMenuVisibility(View.GONE)
            }
            findViewById<Button>(R.id.btn_book).setOnClickListener { viewModel.onBookNowClicked() }
        }
    }
}
