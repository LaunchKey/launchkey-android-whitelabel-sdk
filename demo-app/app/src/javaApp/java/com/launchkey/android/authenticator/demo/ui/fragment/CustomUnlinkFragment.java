package com.launchkey.android.authenticator.demo.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.util.Utils;
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager;
import com.launchkey.android.authenticator.sdk.core.authentication_management.Device;
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.UnlinkDeviceEventCallback;

public class CustomUnlinkFragment extends BaseDemoFragment {
    @NonNull
    private final AuthenticatorManager authenticatorManager = AuthenticatorManager.instance;

    private ProgressDialog unlinkingDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        unlinkingDialog = new ProgressDialog(getActivity(), R.style.Theme_WhiteLabel_Dialog);
        unlinkingDialog.setIndeterminate(true);
        unlinkingDialog.setCancelable(false);
        unlinkingDialog.setMessage("Custom Unlinking...");

        unlink();
    }

    private void unlink() {

        Utils.show(unlinkingDialog);

        authenticatorManager.unlinkDevice(null, new UnlinkDeviceEventCallback() {

            @Override
            public void onSuccess(@NonNull Device device) {
                Utils.dismiss(unlinkingDialog);

                if (getActivity() != null && !getActivity().isFinishing()) {
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                Utils.dismiss(unlinkingDialog);
                String message = e.getMessage();
                Utils.alert(getActivity(), "Error", message);
            }
        });
    }
}
