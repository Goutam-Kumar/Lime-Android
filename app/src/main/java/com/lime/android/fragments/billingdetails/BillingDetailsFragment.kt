package com.lime.android.fragments.billingdetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.androidbuffer.kotlinfilepicker.KotConstants
import com.androidbuffer.kotlinfilepicker.KotRequest
import com.androidbuffer.kotlinfilepicker.KotResult
import com.androidbuffer.kotlinfilepicker.KotUtil
import com.lime.android.R
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment
import com.lime.android.util.LimeUtils


private const val REQUEST_CERTIFICATE: Int = 1000
private const val REQUEST_CON_BIL: Int = 1001

internal class BillingDetailsFragment: BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_billing_details
    private val viewModel by lazy {
        obtainViewModel {
            BillingDetailsViewModel(requireContext(), requireArguments())
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
            serviceException.observe(viewLifecycleOwner, Observer { LimeUtils.showServiceError(it, requireContext()) })
            spinner.observe(viewLifecycleOwner, Observer { handleSpinner(it) })
        }
    }

    private fun configureUI(view: View) {
        view.apply {
            (activity as MainActivity).apply {
                setTitle(getString(R.string.billing_details))
                setBackButtonVisibility(View.VISIBLE)
                setMenuVisibility(View.GONE)
            }
            findViewById<EditText>(R.id.et_bs_name).addTextChangedListener {
                viewModel.businessName = it.toString()
            }
            findViewById<EditText>(R.id.et_bs_email).addTextChangedListener{
                viewModel.businessEmail = it.toString()
            }
            findViewById<EditText>(R.id.et_cn_name).addTextChangedListener{
                viewModel.contactName = it.toString()
            }
            findViewById<EditText>(R.id.et_address).addTextChangedListener{
                viewModel.address = it.toString()
            }
            findViewById<EditText>(R.id.et_nat_id).addTextChangedListener{
                viewModel.natId = it.toString()
            }
            findViewById<RadioGroup>(R.id.radioGroup_type).setOnCheckedChangeListener { _, checkedId ->
                val radioButton = findViewById<RadioButton>(checkedId)
                viewModel.bookingType = radioButton.text.toString()
            }
            findViewById<TextView>(R.id.tv_certificate).setOnClickListener {
                KotRequest.File(requireActivity(), REQUEST_CERTIFICATE).isMultiple(false).setMimeType(
                    KotConstants.FILE_TYPE_FILE_ALL).pick()

            }

            findViewById<TextView>(R.id.tv_cons_bill).setOnClickListener {
                KotRequest.File(requireActivity(), REQUEST_CON_BIL).isMultiple(false).setMimeType(
                    KotConstants.FILE_TYPE_FILE_ALL).pick()
            }
            findViewById<Button>(R.id.btn_book).setOnClickListener { viewModel.onBookNowClicked() }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CERTIFICATE == requestCode && resultCode == Activity.RESULT_OK) {
            val result = data?.getParcelableArrayListExtra<KotResult>(KotConstants.EXTRA_FILE_RESULTS)
            createDetailsFromResult(result!![0],requestCode)
        }else if (REQUEST_CON_BIL == requestCode && resultCode == Activity.RESULT_OK) {
            val result = data?.getParcelableArrayListExtra<KotResult>(KotConstants.EXTRA_FILE_RESULTS)
            createDetailsFromResult(result!![0],requestCode)
        }
    }

    private fun createDetailsFromResult(kotResult: KotResult, requestCode: Int) {
        val rawFile = KotUtil.getFileDetails(requireContext(),kotResult.uri)
        val file = KotUtil.getFileDetails(requireContext(),kotResult.uri)?.path
        if (REQUEST_CERTIFICATE == requestCode){
            viewModel.certificate = rawFile
            viewModel.certificateUri = kotResult.uri
            view?.findViewById<TextView>(R.id.tv_certificate)?.text = file.orEmpty()
        }else if (REQUEST_CON_BIL == requestCode){
            viewModel.bill = rawFile
            viewModel.billUri = kotResult.uri
            view?.findViewById<TextView>(R.id.tv_cons_bill)?.text = file.orEmpty()
        }
    }

    private fun handleSpinner(showSpinner: Boolean) = if (showSpinner) showSpinner(isCancellable = false, isTransparent = false) else hideSpinner()


}
