package com.launchkey.android.authenticator.demo.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle

import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.util.Utils
import com.launchkey.android.authenticator.sdk.AuthenticatorManager
import com.launchkey.android.authenticator.sdk.SimpleOperationCallback
import com.launchkey.android.authenticator.sdk.error.BaseError

class CustomUnlinkFragment2 : BaseDemoFragment() {

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

        AuthenticatorManager.getInstance().unlinkCurrentDevice(object : SimpleOperationCallback<Any> {

            override fun onResult(successful: Boolean, error: BaseError?, o: Any?) {

                Utils.dismiss(mUnlinkingDialog)

                if (successful) {
                    if (activity != null && !activity!!.isFinishing) {
                        activity!!.finish()
                    }
                } else {

                    val message = Utils.getMessageForBaseError(error)
                    Utils.alert(activity, "Error", message)
                }
            }
        })
    }
}
