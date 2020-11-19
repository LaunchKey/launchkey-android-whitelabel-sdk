package com.launchkey.android.authenticator.demo.util

import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.launchkey.android.authenticator.sdk.core.exception.*

object Utils {
    fun show(d: Dialog?) {
        if (d != null && !d.isShowing) {
            d.show()
        }
    }

    fun dismiss(d: Dialog?) {
        if (d != null && d.isShowing) {
            d.dismiss()
        }
    }

    fun getMessageForBaseError(e: Exception?): String? {
        var m: String? = ""
        if (e == null) {
            return m
        }
        m = if (e is NoInternetConnectivityException) {
            "No internet connectivity--check your connection"
        } else if (e is RequestArgumentException) {
            "Problem setting things up. Details=" + e.message
        } else if (e is CommunicationException) {
            "Error contacting service ($e)"
        } else if (e is DeviceNotLinkedException) {
            "Device is yet to be linked or it has been marked as unlinked by the service"
        } else if (e is DeviceNotFoundException) {
            "Could not find device to delete"
        } else if (e is MalformedLinkingCodeException) {
            "The linking code is invalid"
        } else if (e is AuthRequestNotFoundException) {
            "Auth Request has expired"
        } else if (e is LaunchKeyApiException) {
            getMessageForApiError(e as LaunchKeyApiException?)
        } else if (e is UnexpectedCertificateException) {
            "Your Internet traffic could be monitored. Make sure you are on a reliable network."
        } else {
            "Unknown error=" + e.message
        }
        return m
    }

    fun getMessageForApiError(e: LaunchKeyApiException?): String? {
        if (e == null) {
            return null
        }
        return if (e is DeviceNameAlreadyUsedException) {
            "The device name you chose is already assigned to another device associated with your account.  Please choose an alternative name or unlink the conflicting device, and then try again."
        } else if (e is IncorrectSdkKeyException) {
            "Mobile SDK key incorrect. Please check with your service provider."
        } else if (e is InvalidLinkingCodeException) {
            "Invalid linking code used."
        } else {
            """
     Extras:
     ${e.message}
     """.trimIndent()
        }
    }

    fun simpleSnackbarForBaseError(v: View?, e: Exception?) {
        if (v == null || e == null) {
            return
        }
        val m = getMessageForBaseError(e)
        simpleSnackbar(v, m, Snackbar.LENGTH_INDEFINITE)
    }

    @JvmOverloads
    fun simpleSnackbar(v: View?, m: String?, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(v!!, m!!, duration).show()
    }

    fun finish(f: Fragment?) {
        if (f != null && f.activity != null && !f.activity!!.isFinishing) {
            f.activity!!.finish()
        }
    }

    fun alert(context: Context?, title: String?, message: String?) {
        if (context == null || message == null) {
            return
        }
        AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .create()
                .show()
    }
}