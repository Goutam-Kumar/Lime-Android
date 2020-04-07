package com.rigsit.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.lime.android.R
import com.lime.android.ui.BaseFragment
import com.lime.android.ui.customview.dotloader.DotLoader
import java.util.*

private const val ARG_CONFIG = "PROGRESS_DIALOG_CONFIG"

class CustomSpinnerFragment : DialogFragment() {

    private lateinit var config: CustomSpinnerConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readSpinnerConfig()
        configWindowStyle()
        configSpinner()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_spinner, container, false).apply {
            configProgressBar(this)
            //configCancelImage(this)
            //configHintMessage(this)
        }

    override fun dismiss() {
        if (fragmentManager != null)
            super.dismiss()
    }

    private fun configWindowStyle() {
        setStyle(STYLE_NO_FRAME, config.getWindowStyle())
    }

    private fun readSpinnerConfig() {
        config = (arguments?.getParcelable(ARG_CONFIG) as? CustomSpinnerConfig) ?: DEFAULT_CONFIG
    }

    private fun configSpinner() {
        isCancelable = config.isCancellable
    }

    private fun configProgressBar(parentView: View) {
        val progressBar = parentView.findViewById(R.id.dot_loader) as DotLoader
    }

    /*private fun configCancelImage(parentView: View) {
        if (!config.isCancellable) return
        val cancelImage = parentView.findViewById(R.id.common_ui_spinner_dismissImage) as ImageView
        cancelImage.visibility = View.VISIBLE
        cancelImage.setOnClickListener { cancelPerformed() }
        cancelImage.contentDescription = config.getCancelContentDescription(requireContext())
    }*/

    /*private fun configHintMessage(parentView: View) {
        parentView.findViewById<TextView>(R.id.common_ui_spinner_hintText).setText(config.hintMessageId)
    }*/

    private fun cancelPerformed() {
        (targetFragment as? BaseFragment)?.run {
            spinnerCanceled(config.spinnerTag)
        }

        dismiss()
    }

    companion object {
        fun newInstance(config: CustomSpinnerConfig) =
            CustomSpinnerFragment().apply {
                arguments = newBundle(config)
            }

        fun newBundle(config: CustomSpinnerConfig) =
            Bundle().apply {
                putParcelable(ARG_CONFIG, config)
            }

        const val UNIQUE_TAG = "Unique CustomProgress"

        private val DEFAULT_CONFIG = CustomSpinnerConfig(
            isCancellable = false,
            isTransparent = false,
            spinnerTag = UUID.randomUUID().toString(),
            cancelContentDescription = ""
        )
    }
}

