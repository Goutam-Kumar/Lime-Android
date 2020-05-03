package com.lime.android.fragments.offerbid

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.lime.android.R
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment
import com.lime.android.util.LimeUtils

class OfferBidFragment: BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_offer_bid
    private val viewModel by lazy {
        obtainViewModel {
            OfferBidViewModel(requireArguments(),requireContext())
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNavigationListener(viewModel.navigationLiveDataField)
        configureUI(view)
        setViewModelObservers()
    }

    private fun setViewModelObservers() {
        viewModel.run {
            spinner.observe(viewLifecycleOwner, Observer { handleSpinner(it) })
            serviceException.observe(viewLifecycleOwner, Observer { LimeUtils.showServiceError(it, requireContext()) })
        }
    }

    private fun configureUI(view: View) {
        view.apply {
            (activity as MainActivity).apply {
                setTitle(getString(R.string.bid_amount))
                setBackButtonVisibility(View.VISIBLE)
                setMenuVisibility(View.GONE)
            }

            findViewById<Button>(R.id.btn_send_bid).setOnClickListener { viewModel.onSendBidClicked() }
            findViewById<EditText>(R.id.et_price_box).addTextChangedListener {
                viewModel.onBidPriceChanged(it.toString())
            }
        }
    }

    private fun handleSpinner(showSpinner: Boolean) = if (showSpinner) showSpinner(isCancellable = false, isTransparent = false) else hideSpinner()

}
