package com.launchkey.android.authenticator.demo.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.util.Utils;
import com.launchkey.android.authenticator.sdk.AuthenticatorManager;
import com.launchkey.android.authenticator.sdk.SimpleOperationCallback;
import com.launchkey.android.authenticator.sdk.error.BaseError;

public class CustomUnlinkFragment2 extends BaseDemoFragment {

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

        AuthenticatorManager.getInstance().unlinkCurrentDevice(new SimpleOperationCallback() {

            @Override
            public void onResult(boolean successful, BaseError error, Object o) {

                Utils.dismiss(mUnlinkingDialog);

                if (successful) {
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        getActivity().finish();
                    }
                } else {

                    String message = Utils.getMessageForBaseError(error);
                    Utils.alert(getActivity(), "Error", message);
                }
            }
        });
    }
}
