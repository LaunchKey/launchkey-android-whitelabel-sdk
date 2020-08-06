package com.launchkey.android.authenticator.demo.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.util.Utils;
import com.launchkey.android.authenticator.sdk.SimpleOperationCallback;
import com.launchkey.android.authenticator.sdk.error.BaseError;
import com.launchkey.android.authenticator.sdk.session.SessionManager;

public class CustomLogoutFragment2 extends BaseDemoFragment {

    private ProgressDialog mLogoutDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLogoutDialog = new ProgressDialog(getActivity(), R.style.Theme_WhiteLabel_Dialog);
        mLogoutDialog.setIndeterminate(true);
        mLogoutDialog.setCancelable(false);
        mLogoutDialog.setMessage("Custom Logout...");

        logOut();
    }

    private void logOut() {

        Utils.show(mLogoutDialog);

        SessionManager
                .getInstance()
                .endAllSessions(new SimpleOperationCallback() {

                    @Override
                    public void onResult(boolean successful, BaseError error, Object o) {

                        Utils.dismiss(mLogoutDialog);

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
