package com.launchkey.android.authenticator.demo.util;

import android.app.Dialog;
import android.content.Context;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.View;

import com.launchkey.android.authenticator.sdk.core.exception.AuthRequestNotFoundException;
import com.launchkey.android.authenticator.sdk.core.exception.CommunicationException;
import com.launchkey.android.authenticator.sdk.core.exception.DeviceNameAlreadyUsedException;
import com.launchkey.android.authenticator.sdk.core.exception.DeviceNotFoundException;
import com.launchkey.android.authenticator.sdk.core.exception.DeviceNotLinkedException;
import com.launchkey.android.authenticator.sdk.core.exception.IncorrectSdkKeyException;
import com.launchkey.android.authenticator.sdk.core.exception.InvalidLinkingCodeException;
import com.launchkey.android.authenticator.sdk.core.exception.LaunchKeyApiException;
import com.launchkey.android.authenticator.sdk.core.exception.MalformedLinkingCodeException;
import com.launchkey.android.authenticator.sdk.core.exception.NoInternetConnectivityException;
import com.launchkey.android.authenticator.sdk.core.exception.RequestArgumentException;
import com.launchkey.android.authenticator.sdk.core.exception.UnexpectedCertificateException;

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

    public static String getMessageForBaseError(Exception e) {

        String m = "";

        if (e == null) {
            return m;
        }

        if (e instanceof NoInternetConnectivityException) {
            m = "No internet connectivity--check your connection";
        } else if (e instanceof RequestArgumentException) {
            m = "Problem setting things up. Details=" + e.getMessage();
        } else if (e instanceof CommunicationException) {
            m = "Error contacting service (" + e + ")";
        } else if (e instanceof DeviceNotLinkedException) {
            m = "Device is yet to be linked or it has been marked as unlinked by the service";
        } else if (e instanceof DeviceNotFoundException) {
            m = "Could not find device to delete";
        } else if (e instanceof MalformedLinkingCodeException) {
            m = "The linking code is invalid";
        } else if (e instanceof AuthRequestNotFoundException) {
            m = "Auth Request has expired";
        } else if (e instanceof LaunchKeyApiException) {
            m = getMessageForApiError((LaunchKeyApiException) e);
        } else if (e instanceof UnexpectedCertificateException) {
            m = "Your Internet traffic could be monitored. Make sure you are on a reliable network.";
        } else {
            m = "Unknown error=" + e.getMessage();
        }

        return m;
    }

    public static String getMessageForApiError(LaunchKeyApiException e) {

        if (e == null) {
            return null;
        }

        if (e instanceof DeviceNameAlreadyUsedException) {
            return "The device name you chose is already assigned to another device associated with your account.  Please choose an alternative name or unlink the conflicting device, and then try again.";
        } else if (e instanceof IncorrectSdkKeyException) {
            return "Mobile SDK key incorrect. Please check with your service provider.";
        } else if (e instanceof InvalidLinkingCodeException) {
            return "Invalid linking code used.";
        } else {
            return "Extras:\n".concat(e.getMessage());
        }
    }

    public static void simpleSnackbarForBaseError(View v, Exception e) {
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
