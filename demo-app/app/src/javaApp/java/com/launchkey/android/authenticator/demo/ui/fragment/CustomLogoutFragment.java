package com.launchkey.android.authenticator.demo.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.util.Utils;
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager;
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.EndAllSessionsEventCallback;

public class CustomLogoutFragment extends BaseDemoFragment {
    @NonNull
    private final AuthenticatorManager authenticatorManager = AuthenticatorManager.instance;

    private ProgressDialog logoutDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        logoutDialog = new ProgressDialog(getActivity(), R.style.Theme_WhiteLabel_Dialog);
        logoutDialog.setIndeterminate(true);
        logoutDialog.setCancelable(false);
        logoutDialog.setMessage("Custom Logout...");

        logOut();
    }

    private void logOut() {

        Utils.show(logoutDialog);

        authenticatorManager.endAllSessions(new EndAllSessionsEventCallback() {
            @Override
            public void onSuccess(final Void result) {
                Utils.dismiss(logoutDialog);
                if (getActivity() != null && !getActivity().isFinishing()) {
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(final @NonNull Exception e) {
                Utils.dismiss(logoutDialog);
                String message = Utils.getMessageForBaseError(e);
                Utils.alert(getActivity(), "Error", message);
            }
        });
    }
}
