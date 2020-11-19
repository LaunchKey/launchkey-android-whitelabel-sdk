package com.launchkey.android.authenticator.demo.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.databinding.DemoFragmentLinkBinding
import com.launchkey.android.authenticator.demo.util.Utils.finish
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager
import com.launchkey.android.authenticator.sdk.core.authentication_management.Device
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.DeviceLinkedEventCallback
import com.launchkey.android.authenticator.sdk.core.util.CompositeDisposable
import java.util.regex.Pattern

class CustomLinkingFragment : BaseDemoFragment<DemoFragmentLinkBinding>(R.layout.demo_fragment_link) {
    private var mLinkingDialog: ProgressDialog? = null
    private val mAuthenticatorManager = AuthenticatorManager.instance
    private val compositeDisposable = CompositeDisposable()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi(view)
    }

    private fun setupUi(root: View) {
        binding = DemoFragmentLinkBinding.bind(root)
        binding!!.demoLinkCheckboxDevicenameCustom.setOnCheckedChangeListener { buttonView, isChecked -> binding!!.demoLinkEditName.isEnabled = isChecked }
        binding!!.demoLinkButton.setOnClickListener { onLink() }
        mLinkingDialog = ProgressDialog(activity, R.style.Theme_WhiteLabel_Dialog)
        mLinkingDialog!!.isIndeterminate = true
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    private fun onLink() {
        val linkingCode = binding!!.demoLinkEditCode.text.toString().trim()
        val customDeviceName = binding!!.demoLinkEditName.text.toString().trim()
        if (!Pattern.matches(AuthenticatorManager.REGEX_LINKING_CODE, linkingCode)) {
            showAlert("Error", "Linking code has illegal characters. Allowed structure: "
                    + AuthenticatorManager.REGEX_LINKING_CODE)
            return
        }

        //depending on the desired approach, it is possible to provide a custom device name
        // if no custom device name is provided, a default one will be generated
        // based on the model and manufacturer.
        mLinkingDialog!!.show()
        mLinkingDialog!!.setMessage("Verifying linking code...")
        val deviceName = if (binding!!.demoLinkCheckboxDevicenameCustom.isChecked) customDeviceName else null
        val overrideNameIfUsed = binding!!.demoLinkCheckboxDevicenameOverride.isChecked
        compositeDisposable.add(mAuthenticatorManager.linkDevice(getString(R.string.authenticator_sdk_key), linkingCode, deviceName, overrideNameIfUsed, object : DeviceLinkedEventCallback() {
            override fun onSuccess(device: Device) {
                mLinkingDialog!!.dismiss()
                finish(this@CustomLinkingFragment)
            }

            override fun onFailure(e: Exception) {
                mLinkingDialog!!.dismiss()
                showAlert("Error", e.message)
            }
        }))
    }

    private fun showAlert(title: String, message: String?) {
        AlertDialog.Builder(activity!!)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .setOnDismissListener(null)
                .create()
                .show()
    }
}