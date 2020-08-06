package com.launchkey.android.authenticator.demo.util;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.launchkey.android.authenticator.sdk.error.ApiError;
import com.launchkey.android.authenticator.sdk.error.BaseError;
import com.launchkey.android.authenticator.sdk.error.CommunicationError;
import com.launchkey.android.authenticator.sdk.error.DeviceNotFoundError;
import com.launchkey.android.authenticator.sdk.error.DeviceNotLinkedError;
import com.launchkey.android.authenticator.sdk.error.ExpiredAuthRequestError;
import com.launchkey.android.authenticator.sdk.error.GcmSetupError;
import com.launchkey.android.authenticator.sdk.error.MalformedLinkingCodeError;
import com.launchkey.android.authenticator.sdk.error.NoInternetConnectivityError;
import com.launchkey.android.authenticator.sdk.error.NoSecurityFactorsError;
import com.launchkey.android.authenticator.sdk.error.RequestArgumentError;
import com.launchkey.android.authenticator.sdk.error.UnexpectedCertificateError;


public final class Utils {
    public static void show(Dialog d) {
        if (d != null && !d.isShowing()) {
            d.show();
        }
    }

    public static void dismiss(Dialog d) {
        if (d != null && d.isShowing()) {
            d.dismiss();
        }
    }

    public static String getMessageForBaseError(BaseError e) {

        String m = "";

        if (e == null) {
            return m;
        }

        if (e instanceof NoInternetConnectivityError) {
            m = "No internet connectivity--check your connection";
        } else if (e instanceof NoSecurityFactorsError) {
            m = "User authentication is not possible when no Security factors are set.";
        } else if (e instanceof RequestArgumentError) {
            m = "Problem setting things up. Details=" + e.getMessage();
        } else if (e instanceof CommunicationError) {
            m = "Error contacting service (" + e + ")";
        } else if (e instanceof DeviceNotLinkedError) {
            m = "Device is yet to be linked or it has been marked as unlinked by the service";
        } else if (e instanceof DeviceNotFoundError) {
            m = "Could not find device to delete";
        } else if (e instanceof MalformedLinkingCodeError) {
            m = "The linking code is invalid";
        } else if (e instanceof GcmSetupError) {
            m = "GCM (Push notifications) could not fully finish (" + e.getCode() + ")";
        } else if (e instanceof ExpiredAuthRequestError) {
            m = "Auth Request has expired";
        } else if (e instanceof ApiError) {
            m = getMessageForApiError((ApiError) e);
        } else if (e instanceof UnexpectedCertificateError) {
            m = "Your Internet traffic could be monitored. Make sure you are on a reliable network.";
        } else {
            m = "Unknown error=" + e.getMessage();
        }

        return m;
    }

    public static String getMessageForApiError(ApiError e) {

        if (e == null) {
            return null;
        }

        switch (e.getCodeInt()) {
            case ApiError.DEVICE_NAME_ALREADY_USED:
                return "The device name you chose is already assigned to another device associated with your account.  Please choose an alternative name or unlink the conflicting device, and then try again.";
            case ApiError.INCORRECT_SDK_KEY:
                return "Mobile SDK key incorrect. Please check with your service provider.";
            case ApiError.INVALID_LINKING_CODE:
                return "Invalid linking code used.";
            default:
                return "Extras:\n".concat(e.getMessage());
        }
    }

    public static void simpleSnackbarForBaseError(View v, BaseError e) {
        if (v == null || e == null) {
            return;
        }

        String m = getMessageForBaseError(e);

        simpleSnackbar(v, m, Snackbar.LENGTH_INDEFINITE);
    }

    public static void simpleSnackbar(View v, String m) {
        simpleSnackbar(v, m, Snackbar.LENGTH_LONG);
    }

    public static void simpleSnackbar(View v, String m, int duration) {
        Snackbar.make(v, m, duration).show();
    }

    public static void finish(Fragment f) {
        if (f != null && f.getActivity() != null && !f.getActivity().isFinishing()) {
            f.getActivity().finish();
        }
    }

    public static void alert(Context context, String title, String message) {

        if (context == null || message == null) {
            return;
        }

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }
}
