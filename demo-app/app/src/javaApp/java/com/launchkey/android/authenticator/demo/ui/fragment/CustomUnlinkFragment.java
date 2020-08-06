package com.launchkey.android.authenticator.demo.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.util.Utils;
import com.launchkey.android.authenticator.sdk.SimpleOperationCallback;
import com.launchkey.android.authenticator.sdk.error.BaseError;
import com.launchkey.android.authenticator.sdk.error.CommunicationError;

public class CustomUnlinkFragment extends BaseDemoFragment {

    private ProgressDialog mUnlinkingDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mUnlinkingDialog = new ProgressDialog(getActivity(), R.style.Theme_WhiteLabel_Dialog);
        mUnlinkingDialog.setIndeterminate(true);
        mUnlinkingDialog.setCancelable(false);
        mUnlinkingDialog.setMessage("Custom Unlinking...");

        unlink();
    }

    private void unlink() {

        Utils.show(mUnlinkingDialog);

        getAuthenticatorManager().unlinkCurrentDevice(new SimpleOperationCallback() {

            @Override
            public void onResult(boolean successful, BaseError error, Object o) {

                Utils.dismiss(mUnlinkingDialog);

                if (successful) {
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        getActivity().finish();
                    }
                } else {
                    String httpCode = error instanceof CommunicationError
                            ? String.format("(%s)", error.getCode()) : "";

                    String message = error == null
                            ? "Null error"
                            : String.format("Error %s obj=%s message=%s",
                            httpCode, error.getClass().getSimpleName(), error.getMessage());
                    Utils.alert(getActivity(), "Problem Unlinking", message);
                }
            }
        });
    }
}
