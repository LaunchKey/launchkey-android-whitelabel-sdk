package com.launchkey.android.authenticator.demo.util

import android.app.Dialog
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.View
import com.launchkey.android.authenticator.sdk.error.*


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

    fun getMessageForBaseError(e: BaseError?): String? {

        var m: String? = ""

        if (e == null) {
            return m
        }

        if (e is NoInternetConnectivityError) {
            m = "No internet connectivity--check your connection"
        } else if (e is NoSecurityFactorsError) {
            m = "User authentication is not possible when no Security factors are set."
        } else if (e is RequestArgumentError) {
            m = "Problem setting things up. Details=" + e.message
        } else if (e is CommunicationError) {
            m = "Error contacting service ($e)"
        } else if (e is DeviceNotLinkedError) {
            m = "Device is yet to be linked or it has been marked as unlinked by the service"
        } else if (e is DeviceNotFoundError) {
            m = "Could not find device to delete"
        } else if (e is MalformedLinkingCodeError) {
            m = "The linking code is invalid"
        } else if (e is GcmSetupError) {
            m = "GCM (Push notifications) could not fully finish (" + e.code + ")"
        } else if (e is ExpiredAuthRequestError) {
            m = "Auth Request has expired"
        } else if (e is ApiError) {
            m = getMessageForApiError(e as ApiError?)
        } else if (e is UnexpectedCertificateError) {
            m = "Your Internet traffic could be monitored. Make sure you are on a reliable network."
        } else {
            m = "Unknown error=" + e.message
        }

        return m
    }

    fun getMessageForApiError(e: ApiError?): String? {

        if (e == null) {
            return null
        }

        when (e.codeInt) {
            ApiError.DEVICE_NAME_ALREADY_USED -> return "The device name you chose is already assigned to another device associated with your account.  Please choose an alternative name or unlink the conflicting device, and then try again."
            ApiError.INCORRECT_SDK_KEY -> return "Mobile SDK key incorrect. Please check with your service provider."
            ApiError.INVALID_LINKING_CODE -> return "Invalid linking code used."
            else -> return "Extras:\n" + e.message
        }
    }

    fun simpleSnackbarForBaseError(v: View?, e: BaseError?) {
        if (v == null || e == null) {
            return
        }

        val m = getMessageForBaseError(e)

        simpleSnackbar(v, m, Snackbar.LENGTH_INDEFINITE)
    }

    @JvmOverloads
    fun simpleSnackbar(v: View, m: String?, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(v, m!!, duration).show()
    }

    fun finish(f: Fragment?) {
        if (f != null && f.activity != null && !f.activity!!.isFinishing) {
            f.activity!!.finish()
        }
    }

    fun alert(context: Context?, title: String, message: String?) {

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
