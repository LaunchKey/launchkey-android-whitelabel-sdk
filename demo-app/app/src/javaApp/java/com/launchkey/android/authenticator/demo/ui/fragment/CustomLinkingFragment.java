package com.launchkey.android.authenticator.demo.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.util.Utils;
import com.launchkey.android.authenticator.sdk.AuthenticatorManager;
import com.launchkey.android.authenticator.sdk.DeviceLinkedEventCallback;
import com.launchkey.android.authenticator.sdk.device.Device;
import com.launchkey.android.authenticator.sdk.error.BaseError;

public class CustomLinkingFragment extends BaseDemoFragment {

    private EditText mCode;
    private EditText mName;
    private CheckBox mProvideName;
    private CheckBox mOverrideNameIfUsed;
    private ProgressDialog mLinkingDialog;

    private AuthenticatorManager mAuthenticatorManager;
    private DeviceLinkedEventCallback mOnDeviceLinked;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.demo_fragment_link, container, false);
        return postInflationSetup(root);
    }

    private View postInflationSetup(View root) {

        mCode = root.findViewById(R.id.demo_link_edit_code);
        mName = root.findViewById(R.id.demo_link_edit_name);

        mProvideName = root.findViewById(R.id.demo_link_checkbox_devicename_custom);
        mProvideName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mName.setEnabled(isChecked);
            }
        });

        mOverrideNameIfUsed = root.findViewById(R.id.demo_link_checkbox_devicename_override);

        Button link = root.findViewById(R.id.demo_link_button);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLink();
            }
        });

        mLinkingDialog = new ProgressDialog(getActivity(), R.style.Theme_WhiteLabel_Dialog);
        mLinkingDialog.setIndeterminate(true);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuthenticatorManager = AuthenticatorManager.getInstance();
        mOnDeviceLinked = new DeviceLinkedEventCallback() {

            @Override
            public void onEventResult(boolean successful, BaseError error, Device device) {

                mLinkingDialog.dismiss();

                if (successful) {
                    Utils.finish(CustomLinkingFragment.this);
                } else {
                    showAlert("Error", Utils.getMessageForBaseError(error));
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        mAuthenticatorManager.registerForEvents(mOnDeviceLinked);
    }

    @Override
    public void onPause() {
        super.onPause();
        mAuthenticatorManager.unregisterForEvents(mOnDeviceLinked);
    }

    private void onLink() {
        String linkingCode = mCode.getText().toString().trim();
        String customDeviceName = mName.getText().toString().trim();

        if (!linkingCode.matches(AuthenticatorManager.REGEX_LINKING_CODE)) {
            showAlert("Error", "Linking code has illegal characters. Allowed structure: "
                    + AuthenticatorManager.REGEX_LINKING_CODE);
            return;
        }

        //depending on the desired approach, it is possible to provide a custom device name
        // if no custom device name is provided, a default one will be generated
        // based on the model and manufacturer.

        mLinkingDialog.show();
        mLinkingDialog.setMessage("Verifying linking code...");

        final String deviceName = mProvideName.isChecked() ? customDeviceName : null;
        final boolean overrideNameIfUsed = mOverrideNameIfUsed.isChecked();

        mAuthenticatorManager.linkDevice(linkingCode, deviceName, overrideNameIfUsed, null);
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .setOnDismissListener(null)
                .create()
                .show();
    }
}
