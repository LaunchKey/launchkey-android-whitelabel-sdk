package com.launchkey.android.authenticator.demo.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle

import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.util.Utils
import com.launchkey.android.authenticator.sdk.SimpleOperationCallback
import com.launchkey.android.authenticator.sdk.error.BaseError
import com.launchkey.android.authenticator.sdk.session.SessionManager

class CustomLogoutFragment2 : BaseDemoFragment() {

    private var mLogoutDialog: ProgressDialog? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mLogoutDialog = ProgressDialog(activity, R.style.Theme_WhiteLabel_Dialog)
        mLogoutDialog!!.isIndeterminate = true
        mLogoutDialog!!.setCancelable(false)
        mLogoutDialog!!.setMessage("Custom Logout...")

        logOut()
    }

    private fun logOut() {

        Utils.show(mLogoutDialog)

        SessionManager
                .getInstance(activity)
                .endAllSessions(object : SimpleOperationCallback<Any> {

                    override fun onResult(successful: Boolean, error: BaseError?, o: Any?) {

                        Utils.dismiss(mLogoutDialog)

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
