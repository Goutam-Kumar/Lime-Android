package com.lime.android.fragments.orderconfirmation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.lime.android.R
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment
import com.lime.android.util.LimeUtils

class OrderConfirmationFragment: BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_order_confirmation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureUI(view)
    }

    private fun configureUI(view: View) {
        view.apply {
            (activity as MainActivity).apply {
                setTitle(getString(R.string.order_summary))
                setBackButtonVisibility(View.VISIBLE)
                setMenuVisibility(View.GONE)
            }

            findViewById<ImageView>(R.id.img_success).apply {
                LimeUtils.blinkAnimation(this)
            }
        }
    }
}
