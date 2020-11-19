package com.launchkey.android.authenticator.demo.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.databinding.DemoFragmentLinkBinding;
import com.launchkey.android.authenticator.demo.util.Utils;
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager;
import com.launchkey.android.authenticator.sdk.core.authentication_management.Device;
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.DeviceLinkedEventCallback;
import com.launchkey.android.authenticator.sdk.core.util.CompositeDisposable;

public class CustomLinkingFragment extends BaseDemoFragment<DemoFragmentLinkBinding> {
    private ProgressDialog mLinkingDialog;

    private final @NonNull AuthenticatorManager mAuthenticatorManager = AuthenticatorManager.instance;
    private final @NonNull CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CustomLinkingFragment() {
        super(R.layout.demo_fragment_link);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUi(view);
    }

    private void setupUi(View root) {
        binding = DemoFragmentLinkBinding.bind(root);

        binding.demoLinkCheckboxDevicenameCustom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.demoLinkEditName.setEnabled(isChecked);
            }
        });


        binding.demoLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLink();
            }
        });

        mLinkingDialog = new ProgressDialog(getActivity(), R.style.Theme_WhiteLabel_Dialog);
        mLinkingDialog.setIndeterminate(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }

    private void onLink() {
        String linkingCode = binding.demoLinkEditCode.getText().toString().trim();
        String customDeviceName = binding.demoLinkEditName.getText().toString().trim();

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

        final String deviceName = binding.demoLinkCheckboxDevicenameCustom.isChecked() ? customDeviceName : null;
        final boolean overrideNameIfUsed = binding.demoLinkCheckboxDevicenameOverride.isChecked();

        compositeDisposable.add(mAuthenticatorManager.linkDevice(getString(R.string.authenticator_sdk_key), linkingCode, deviceName, overrideNameIfUsed, new DeviceLinkedEventCallback() {

            @Override
            public void onSuccess(@NonNull final Device device) {
                mLinkingDialog.dismiss();
                Utils.finish(CustomLinkingFragment.this);
            }

            @Override
            public void onFailure(final @NonNull Exception e) {
                mLinkingDialog.dismiss();
                showAlert("Error", e.getMessage());
            }
        }));
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
