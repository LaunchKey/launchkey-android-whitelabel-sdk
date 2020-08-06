package com.launchkey.android.authenticator.demo.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle

import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.util.Utils
import com.launchkey.android.authenticator.sdk.SimpleOperationCallback
import com.launchkey.android.authenticator.sdk.error.BaseError
import com.launchkey.android.authenticator.sdk.error.CommunicationError

class CustomUnlinkFragment : BaseDemoFragment() {

    private var mUnlinkingDialog: ProgressDialog? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mUnlinkingDialog = ProgressDialog(activity, R.style.Theme_WhiteLabel_Dialog)
        mUnlinkingDialog!!.isIndeterminate = true
        mUnlinkingDialog!!.setCancelable(false)
        mUnlinkingDialog!!.setMessage("Custom Unlinking...")

        unlink()
    }

    private fun unlink() {

        Utils.show(mUnlinkingDialog)

        authenticatorManager!!.unlinkCurrentDevice(object : SimpleOperationCallback<Any> {

            override fun onResult(successful: Boolean, error: BaseError?, o: Any?) {

                Utils.dismiss(mUnlinkingDialog)

                if (successful) {
                    if (activity != null && !activity!!.isFinishing) {
                        activity!!.finish()
                    }
                } else {
                    val httpCode = if (error is CommunicationError)
                        String.format("(%s)", error.code)
                    else
                        ""

                    val message = if (error == null)
                        "Null error"
                    else
                        String.format("Error %s obj=%s message=%s",
                                httpCode, error.javaClass.simpleName, error.message)
                    Utils.alert(activity, "Problem Unlinking", message)
                }
            }
        })
    }
}
