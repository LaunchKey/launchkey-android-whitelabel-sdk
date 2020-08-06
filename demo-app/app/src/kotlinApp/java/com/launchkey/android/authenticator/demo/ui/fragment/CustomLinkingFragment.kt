package com.launchkey.android.authenticator.demo.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.util.Utils
import com.launchkey.android.authenticator.sdk.AuthenticatorManager
import com.launchkey.android.authenticator.sdk.DeviceLinkedEventCallback
import com.launchkey.android.authenticator.sdk.device.Device
import com.launchkey.android.authenticator.sdk.error.BaseError

class CustomLinkingFragment : BaseDemoFragment() {

    private var mCode: EditText? = null
    private var mName: EditText? = null
    private var mProvideName: CheckBox? = null
    private var mOverrideNameIfUsed: CheckBox? = null
    private var mLinkingDialog: ProgressDialog? = null

    private var mAuthenticatorManager: AuthenticatorManager? = null
    private var mOnDeviceLinked: DeviceLinkedEventCallback? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.demo_fragment_link, container, false)
        return postInflationSetup(root)
    }

    private fun postInflationSetup(root: View): View {

        mCode = root.findViewById<View>(R.id.demo_link_edit_code) as EditText
        mName = root.findViewById<View>(R.id.demo_link_edit_name) as EditText

        mProvideName = root.findViewById<View>(R.id.demo_link_checkbox_devicename_custom) as CheckBox
        mProvideName!!.setOnCheckedChangeListener { buttonView, isChecked -> mName!!.isEnabled = isChecked }

        mOverrideNameIfUsed = root.findViewById<View>(R.id.demo_link_checkbox_devicename_override) as CheckBox

        val link = root.findViewById<View>(R.id.demo_link_button) as Button
        link.setOnClickListener { onLink() }

        mLinkingDialog = ProgressDialog(activity, R.style.Theme_WhiteLabel_Dialog)
        mLinkingDialog!!.isIndeterminate = true

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAuthenticatorManager = AuthenticatorManager.getInstance()
        mOnDeviceLinked = object : DeviceLinkedEventCallback() {

            override fun onEventResult(successful: Boolean, error: BaseError?, device: Device?) {

                mLinkingDialog!!.dismiss()

                if (successful) {
                    Utils.finish(this@CustomLinkingFragment)
                } else {
                    showAlert("Error", Utils.getMessageForBaseError(error))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mAuthenticatorManager!!.registerForEvents(mOnDeviceLinked)
    }

    override fun onPause() {
        super.onPause()
        mAuthenticatorManager!!.unregisterForEvents(mOnDeviceLinked)
    }

    private fun onLink() {
        val linkingCode = mCode!!.text.toString().trim { it <= ' ' }
        val customDeviceName = mName!!.text.toString().trim { it <= ' ' }

        if (!linkingCode.matches(AuthenticatorManager.REGEX_LINKING_CODE.toRegex())) {
            showAlert("Error", "Linking code has illegal characters. Allowed structure: " + AuthenticatorManager.REGEX_LINKING_CODE)
            return
        }

        //depending on the desired approach, it is possible to provide a custom device name
        // if no custom device name is provided, a default one will be generated
        // based on the model and manufacturer.

        mLinkingDialog!!.show()
        mLinkingDialog!!.setMessage("Verifying linking code...")

        val deviceName = if (mProvideName!!.isChecked) customDeviceName else null
        val overrideNameIfUsed = mOverrideNameIfUsed!!.isChecked

        mAuthenticatorManager!!.linkDevice(linkingCode, deviceName, overrideNameIfUsed, null)
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
