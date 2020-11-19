package com.launchkey.android.authenticator.demo.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.util.Utils.alert
import com.launchkey.android.authenticator.demo.util.Utils.dismiss
import com.launchkey.android.authenticator.demo.util.Utils.show
import com.launchkey.android.authenticator.sdk.core.authentication_management.Device
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.UnlinkDeviceEventCallback

class CustomUnlinkFragment : BaseDemoFragment<ViewBinding>() {
    private var unlinkingDialog: ProgressDialog? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        unlinkingDialog = ProgressDialog(activity, R.style.Theme_WhiteLabel_Dialog)
        unlinkingDialog!!.isIndeterminate = true
        unlinkingDialog!!.setCancelable(false)
        unlinkingDialog!!.setMessage("Custom Unlinking...")
        unlink()
    }

    private fun unlink() {
        show(unlinkingDialog)
        authenticatorManager.unlinkDevice(null, object : UnlinkDeviceEventCallback() {
            override fun onSuccess(device: Device) {
                dismiss(unlinkingDialog)
                if (activity != null && !activity!!.isFinishing) {
                    activity!!.finish()
                }
            }

            override fun onFailure(e: Exception) {
                dismiss(unlinkingDialog)
                val message = e.message
                alert(activity, "Error", message)
            }
        })
    }
}