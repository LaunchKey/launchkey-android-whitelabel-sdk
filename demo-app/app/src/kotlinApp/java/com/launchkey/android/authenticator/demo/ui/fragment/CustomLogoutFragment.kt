package com.launchkey.android.authenticator.demo.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.util.Utils.alert
import com.launchkey.android.authenticator.demo.util.Utils.dismiss
import com.launchkey.android.authenticator.demo.util.Utils.getMessageForBaseError
import com.launchkey.android.authenticator.demo.util.Utils.show
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.EndAllSessionsEventCallback

class CustomLogoutFragment : BaseDemoFragment<ViewBinding>() {
    private var logoutDialog: ProgressDialog? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        logoutDialog = ProgressDialog(activity, R.style.Theme_WhiteLabel_Dialog)
        logoutDialog!!.isIndeterminate = true
        logoutDialog!!.setCancelable(false)
        logoutDialog!!.setMessage("Custom Logout...")
        logOut()
    }

    private fun logOut() {
        show(logoutDialog)
        authenticatorManager.endAllSessions(object : EndAllSessionsEventCallback() {
            override fun onSuccess(result: Void?) {
                dismiss(logoutDialog)
                if (activity != null && !activity!!.isFinishing) {
                    activity!!.finish()
                }
            }

            override fun onFailure(e: Exception) {
                dismiss(logoutDialog)
                val message = getMessageForBaseError(e)
                alert(activity, "Error", message)
            }
        })
    }
}