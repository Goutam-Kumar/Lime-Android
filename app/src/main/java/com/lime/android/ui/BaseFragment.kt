package com.lime.android.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.lime.android.screens.dashboard.MainActivity
import com.lime.android.R
import com.lime.android.ui.navigationui.LiveNavigationField
import com.lime.android.ui.navigationui.NavigationEvent
import com.rigsit.android.ui.CustomSpinnerConfig
import com.rigsit.android.ui.CustomSpinnerFragment

abstract class BaseFragment : Fragment(){

    protected abstract val layoutResourceId: Int

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResourceId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureActionBarContent()
    }

    private fun configureActionBarContent() {
        (activity as MainActivity).apply {
            setTitle(getString(R.string.app_name))
            setBackButtonVisibility(View.GONE)
        }
        requireActivity().findViewById<ImageView>(R.id.img_back).setOnClickListener {
            findNavController().navigateUp()
        }
    }

    protected fun configureNavigationListener(navigationLiveDataField: LiveNavigationField<NavigationEvent>) {
        navigationLiveDataField.observe(viewLifecycleOwner, Observer { navigate(it) })
    }

    protected fun showSpinner(
        isCancellable: Boolean = false,
        isTransparent: Boolean = true,
        tag: String = "",
        cancelContentDescription: String = "",
        hintMessage: Int = R.string.please_wait
    ) = showSpinner(
        CustomSpinnerConfig(
            isCancellable,
            isTransparent,
            tag,
            cancelContentDescription,
            hintMessage
        )
    )


    @Suppress("unused")
    protected fun hideSpinner() = findSpinnerFragment()?.dismiss()


    open fun spinnerCanceled(tag: String) {
        //no-op
    }

    @Synchronized
    private fun showSpinner(config: CustomSpinnerConfig) {
        val spinnerFragment = findSpinnerFragment()
        if (spinnerFragment != null) return
        hideKeyboard()

        fragmentManager?.run {
            val spinner = CustomSpinnerFragment.newInstance(config)
            spinner.setTargetFragment(this@BaseFragment, REQUEST_SPINNER)
            spinner.show(this, CustomSpinnerFragment.UNIQUE_TAG)
        }
    }

    private fun findSpinnerFragment(): CustomSpinnerFragment? =
        fragmentManager?.run {
            findFragmentByTag(CustomSpinnerFragment.UNIQUE_TAG) as? CustomSpinnerFragment
        }

    private fun hideKeyboard() {
        activity?.currentFocus?.let {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun navigate(event: NavigationEvent) {
        hideSpinner()
        findNavController().navigate(event.navId, event.argumentsBundle())
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified V : ViewModel> Fragment.obtainViewModel(crossinline instance: () -> V): V {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return instance() as T
            }
        }
        return ViewModelProviders.of(this, factory).get(V::class.java)
    }

    companion object {
        private const val REQUEST_SPINNER = 987
    }
}
