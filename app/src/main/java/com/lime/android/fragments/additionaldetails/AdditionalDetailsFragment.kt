package com.lime.android.fragments.additionaldetails

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import com.lime.android.R
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment
import java.util.*

class AdditionalDetailsFragment: BaseFragment(),AdapterView.OnItemSelectedListener {

    private val vehicles = arrayOf("Mini Truck", "Tata 407", "Tata Pickup", "Truck")
    private val goodsType = arrayOf("Home Shifting", "Furniture Delivery")
    private val weightType = arrayOf("Ton", "Kg")

    override val layoutResourceId: Int = R.layout.fragment_additional_details
    private val viewModel by lazy {
        obtainViewModel {
            AdditionalDetailsViewModel()
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
                setTitle(getString(R.string.additional_details))
                setBackButtonVisibility(View.VISIBLE)
                setMenuVisibility(View.GONE)
            }
        }

        view.apply {
            val vehiclesAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,vehicles)
            vehiclesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val goodsAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,goodsType)
            goodsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val weightsAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,weightType)
            weightsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val vehicleSpinner = findViewById<Spinner>(R.id.spinner_vehicle)
            val goodsSpinner = findViewById<Spinner>(R.id.spinner_goods)
            val weightSpinner = findViewById<Spinner>(R.id.spinner_weight)

            findViewById<TextView>(R.id.tv_date).apply {
                setOnClickListener { displayDatePickerDlg() }
            }
            findViewById<TextView>(R.id.tv_vehicle).apply { setOnClickListener { vehicleSpinner.performClick() } }
            findViewById<TextView>(R.id.tv_goods).apply { setOnClickListener { goodsSpinner.performClick() } }
            findViewById<TextView>(R.id.tv_weight).apply { setOnClickListener { weightSpinner.performClick() } }

            vehicleSpinner.apply {
                adapter = vehiclesAdapter
                setSelection(0,false)
                onItemSelectedListener = this@AdditionalDetailsFragment
                prompt = getString(R.string.select_vehicle_type)
                gravity = Gravity.CENTER
            }

            goodsSpinner.apply {
                adapter = goodsAdapter
                setSelection(0,false)
                onItemSelectedListener = this@AdditionalDetailsFragment
                prompt = getString(R.string.select_good_type)
                gravity = Gravity.CENTER
            }
            weightSpinner.apply {
                adapter = weightsAdapter
                setSelection(0,false)
                onItemSelectedListener = this@AdditionalDetailsFragment
                prompt = getString(R.string.weight_ton_kg)
                gravity = Gravity.CENTER
            }

            findViewById<Button>(R.id.btn_bid).apply { setOnClickListener { viewModel.onBidNowClicked() } }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        //Nothing
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p0?.id) {
            R.id.spinner_vehicle -> view?.findViewById<TextView>(R.id.tv_vehicle)?.text = vehicles[p2]
            R.id.spinner_goods -> view?.findViewById<TextView>(R.id.tv_goods)?.text = goodsType[p2]
            else ->
                view?.findViewById<TextView>(R.id.tv_weight)?.text = weightType[p2]

        }
    }

    private fun displayDatePickerDlg(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dateTxtView = view?.findViewById<TextView>(R.id.tv_date)
        val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            dateTxtView?.text = StringBuilder()
                .append(viewModel.formattedDate(dayOfMonth))
                .append("/").append(viewModel.formattedDate(monthOfYear+1))
                .append("/").append(viewModel.formattedDate(year))
                .toString()
        }, year, month, day)
        dpd.show()
    }
}
