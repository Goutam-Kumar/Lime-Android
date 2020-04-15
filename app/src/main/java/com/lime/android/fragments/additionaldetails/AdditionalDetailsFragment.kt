package com.lime.android.fragments.additionaldetails

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import com.lime.android.R
import com.lime.android.models.vehicleandgoods.GoodsType
import com.lime.android.models.vehicleandgoods.Vehicle
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.ui.BaseFragment
import com.lime.android.util.LimeUtils
import java.util.*

class AdditionalDetailsFragment: BaseFragment(), AdapterView.OnItemSelectedListener {

    private val weightType = arrayOf("Ton", "Kg")
    private lateinit var vehiclesAdapter: VehiclesAdapter
    private lateinit var goodsAdapter: GoodsAdapter

    override val layoutResourceId: Int = R.layout.fragment_additional_details
    private val viewModel by lazy {
        obtainViewModel {
            AdditionalDetailsViewModel(requireArguments(), requireContext())
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNavigationListener(viewModel.navigationLiveDataField)
        configureUI(view)
        setViewModelObservers()
        viewModel.getVehicleAndGoods()
    }

    private fun setViewModelObservers() {
        viewModel.run {
            spinner.observe(viewLifecycleOwner, Observer { handleSpinner(it) })
            serviceException.observe(viewLifecycleOwner, Observer { LimeUtils.showServiceError(it, requireContext()) })
            vehicles.observe(viewLifecycleOwner, Observer { reDrawVehicleSpinner(it) })
            goodsType.observe(viewLifecycleOwner, Observer { reDrawGoodsSpinner(it) })
        }
    }

    private fun reDrawGoodsSpinner(listOfGoods: List<GoodsType>?) {
        listOfGoods?.let {
            view?.apply {
                goodsAdapter = GoodsAdapter(requireContext(), listOfGoods)
                val goodsSpinner = findViewById<Spinner>(R.id.spinner_goods).apply {
                    adapter = goodsAdapter
                    setSelection(0,false)
                    onItemSelectedListener = this@AdditionalDetailsFragment
                    prompt = getString(R.string.select_good_type)
                    gravity = Gravity.CENTER
                }
                //findViewById<TextView>(R.id.tv_goods).apply { setOnClickListener { goodsSpinner.performClick() } }
            }
        }
    }

    private fun reDrawVehicleSpinner(listOfVehicles: List<Vehicle>?) {
        listOfVehicles?.let {
            view?.apply {
                vehiclesAdapter = VehiclesAdapter(requireContext(), listOfVehicles)
                val vehicleSpinner = findViewById<Spinner>(R.id.spinner_vehicle)
                findViewById<TextView>(R.id.tv_vehicle).apply { setOnClickListener { vehicleSpinner?.performClick() } }
                vehicleSpinner.apply {
                    adapter = vehiclesAdapter
                    setSelection(0,false)
                    onItemSelectedListener = this@AdditionalDetailsFragment
                    prompt = getString(R.string.select_vehicle_type)
                    gravity = Gravity.CENTER
                }
            }
        }
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
            val weightsAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,weightType)
            weightsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val weightSpinner = findViewById<Spinner>(R.id.spinner_weight)
            findViewById<TextView>(R.id.tv_date).apply {
                setOnClickListener { displayDatePickerDlg() }
            }
            findViewById<TextView>(R.id.tv_weight).apply { setOnClickListener { weightSpinner.performClick() } }
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
            R.id.spinner_vehicle -> view?.findViewById<TextView>(R.id.tv_vehicle)?.text = vehiclesAdapter.getItem(p2).make.plus(" ").plus(vehiclesAdapter.getItem(p2).model)
            R.id.spinner_goods -> LimeUtils.makeToast(requireContext(),goodsAdapter.getItem(p2).name)
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

    private fun handleSpinner(showSpinner: Boolean) = if (showSpinner) showSpinner(isCancellable = false, isTransparent = false) else hideSpinner()
}
