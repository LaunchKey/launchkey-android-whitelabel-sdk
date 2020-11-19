package com.launchkey.android.authenticator.demo.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.databinding.DemoActivityConfigsBinding;
import com.launchkey.android.authenticator.sdk.core.auth_method_management.AuthMethod;
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorConfig;
import com.launchkey.android.authenticator.sdk.core.authentication_management.Device;
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.UnlinkDeviceEventCallback;
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIConfig;

public class AppConfigsActivity extends BaseDemoActivity<DemoActivityConfigsBinding> {
    private String originalSdkKey;
    public AppConfigsActivity() {
        super(R.layout.demo_activity_configs);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DemoActivityConfigsBinding.bind(findViewById(R.id.configs_root));

        binding.configsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReinitSdk();
            }
        });

        binding.configsBuildHash.setText(
                getString(
                        R.string.demo_commit_hash_title,
                        getString(R.string.demo_commit_hash)));

        updateUi();
    }

    @SuppressLint("SetTextI18n")
    private void updateUi() {
        final Intent i = getIntent();
        if (i != null) {
            originalSdkKey = i.getStringExtra(ListDemoActivity.EXTRA_SDK_KEY);
            binding.configsSdkKey.setText(originalSdkKey == null ? "" : originalSdkKey);
        }

        // Prep all hints at runtime

        final int delaySecondsMin = 0;
        final int delaySecondsMax = 60 * 60 * 24;
        final String delayHints = getString(
                R.string.demo_activity_list_feature_config_hints_format, delaySecondsMin, delaySecondsMax);
        binding.configsDelayWearables.setHint(delayHints);
        binding.configsDelayLocations.setHint(delayHints);

        final String authFailureHint = getString(
                R.string.demo_activity_list_feature_config_hints_format,
                AuthenticatorConfig.Builder.THRESHOLD_AUTHFAILURE_MINIMUM,
                AuthenticatorConfig.Builder.THRESHOLD_AUTHFAILURE_MAXIMUM);
        binding.configsAuthfailure.setHint(authFailureHint);

        final String autoUnlinkHint = getString(
                R.string.demo_activity_list_feature_config_hints_format,
                AuthenticatorConfig.Builder.THRESHOLD_AUTOUNLINK_MINIMUM,
                AuthenticatorConfig.Builder.THRESHOLD_AUTOUNLINK_MAXIMUM);
        binding.configsAutounlink.setHint(autoUnlinkHint);

        // For the warning, max is derived from Auto Unlink max - 1
        final String autoUnlinkWarningHint = getString(
                R.string.demo_activity_list_feature_config_hints_format,
                AuthenticatorConfig.Builder.THRESHOLD_AUTOUNLINK_WARNING_NONE,
                AuthenticatorConfig.Builder.THRESHOLD_AUTOUNLINK_MAXIMUM - 1);
        binding.configsAutounlinkwarning.setHint(autoUnlinkWarningHint);

        final AuthenticatorConfig config = getAuthenticatorManager().getConfig();

        // Update UI to match SDK config passed upon initialization

        binding.configsPc.setChecked(config.isMethodAllowed(AuthMethod.PIN_CODE));
        binding.configsCc.setChecked(config.isMethodAllowed(AuthMethod.CIRCLE_CODE));
        binding.configsW.setChecked(config.isMethodAllowed(AuthMethod.WEARABLES));
        binding.configsL.setChecked(config.isMethodAllowed(AuthMethod.LOCATIONS));
        binding.configsFs.setChecked(config.isMethodAllowed(AuthMethod.BIOMETRIC));
        binding.configsDelayWearables.setText(Integer.toString(config.activationDelayWearablesSeconds()));
        binding.configsDelayLocations.setText(Integer.toString(config.activationDelayLocationsSeconds()));
        binding.configsAuthfailure.setText(Integer.toString(config.thresholdAuthFailure()));
        binding.configsAutounlink.setText(Integer.toString(config.thresholdAutoUnlink()));
        binding.configsAutounlinkwarning.setText(Integer.toString(config.thresholdAutoUnlinkWarning()));
        binding.configsAllowchangesunlinked.setChecked(getAuthenticatorUIManager().getConfig().areSecurityChangesAllowedWhenUnlinked());

        try {
            // If overriding domain or subdomain is null, default values will be used when specs are built
            final Resources res = getResources();
            final int subdomainOverrideResId =
                    res.getIdentifier("lk_auth_sdk_oendsub", "string", getPackageName());

            final String subdomainOverride;
            if (subdomainOverrideResId > 0) {
                subdomainOverride = res.getString(subdomainOverrideResId);
            } else {
                subdomainOverride = null;
            }

            final String subdomain;
            if (subdomainOverride == null) {
                subdomain = "mapi";
            } else {
                subdomain = subdomainOverride;
            }

            final int domainOverrideResId =
                    res.getIdentifier("lk_auth_sdk_oenddom", "string", getPackageName());

            final String domainOverride;
            if (domainOverrideResId > 0) {
                domainOverride = res.getString(domainOverrideResId);
            } else {
                domainOverride = null;
            }

            final String domain;
            if (domainOverride == null) {
                domain = "launchkey.com";
            } else {
                domain = domainOverride;
            }
            final String endpoint = getString(
                    R.string.demo_activity_list_feature_config_endpoint_format, subdomain, domain);
            binding.configsEndpoint.setText(endpoint);
            binding.configsEndpoint.setVisibility(View.VISIBLE);
        } catch (Resources.NotFoundException e) {
            // Do nothing
        }
    }

    private void onReinitSdk() {
        // Reinitialize the SDK with whichever key we had last
        final String key = binding.configsSdkKey.getText().toString();

        if (key.trim().isEmpty()) {
            showError("Key cannot be null or empty.");
            return;
        }

        final boolean sslPinningEnabled = binding.configsSslPinning.isChecked();
        final boolean allowPinCode = binding.configsPc.isChecked();
        final boolean allowCircleCode = binding.configsCc.isChecked();
        final boolean allowWearables = binding.configsW.isChecked();
        final boolean allowLocations = binding.configsL.isChecked();
        final boolean allowFingerprintScan = binding.configsFs.isChecked();

        final String delayWearablesStr = binding.configsDelayWearables.getText().toString();
        if (delayWearablesStr.trim().isEmpty()) {
            showError("Activation delay for Wearables cannot be empty");
            return;
        }

        final int delayWearables;
        try {
            delayWearables = Integer.parseInt(delayWearablesStr);
        } catch (NumberFormatException e) {
            showError("Activation delay for Wearables must be a number");
            return;
        }

        final String delayLocationsStr = binding.configsDelayLocations.getText().toString();
        if (delayLocationsStr.trim().isEmpty()) {
            showError("Activation delay for Locations cannot be empty");
            return;
        }

        final int delayLocations;
        try {
            delayLocations = Integer.parseInt(delayLocationsStr);
        } catch (NumberFormatException e) {
            showError("Activation delay for Locations must be a number");
            return;
        }

        final String authFailureStr = binding.configsAuthfailure.getText().toString();

        if (authFailureStr.trim().isEmpty()) {
            showError("Auth Failure threshold cannot be empty");
            return;
        }

        final int authFailure;
        try {
            authFailure = Integer.parseInt(authFailureStr);
        } catch (NumberFormatException e) {
            showError("Auth Failure threshold must be a number");
            return;
        }

        final String autoUnlinkStr = binding.configsAutounlink.getText().toString();

        if (autoUnlinkStr.trim().isEmpty()) {
            showError("Auto Unlink threshold cannot be empty");
            return;
        }

        final int autoUnlink;
        try {
            autoUnlink = Integer.parseInt(autoUnlinkStr);
        } catch (NumberFormatException e) {
            showError("Auto Unlink threshold must be a number");
            return;
        }

        final String autoUnlinkWarningStr = binding.configsAutounlinkwarning.getText().toString();

        if (autoUnlinkWarningStr.trim().isEmpty()) {
            showError("Auto Unlink warning threshold cannot be empty");
            return;
        }

        final int autoUnlinkWarning;
        try {
            autoUnlinkWarning = Integer.parseInt(autoUnlinkWarningStr);
        } catch (NumberFormatException e) {
            showError("Auto Unlink warning threshold must be a number");
            return;
        }

        final boolean allowChangesWhenUnlinked = binding.configsAllowchangesunlinked.isChecked();

        // Let Authenticator SDK validate threshold arguments

        final AuthenticatorConfig config;
        final AuthenticatorUIConfig uiConfig;
        try {
            AuthenticatorConfig.Builder builder = new AuthenticatorConfig.Builder();
            disallowAuthMethod(builder, AuthMethod.PIN_CODE, allowPinCode);
            disallowAuthMethod(builder, AuthMethod.CIRCLE_CODE, allowCircleCode);
            disallowAuthMethod(builder, AuthMethod.WEARABLES, allowWearables);
            disallowAuthMethod(builder, AuthMethod.LOCATIONS, allowLocations);
            disallowAuthMethod(builder, AuthMethod.BIOMETRIC, allowFingerprintScan);
            config = builder
                    .activationDelayWearable(delayWearables)
                    .activationDelayLocation(delayLocations)
                    .thresholdAuthFailure(authFailure)
                    .thresholdAutoUnlink(autoUnlink)
                    .thresholdAutoUnlinkWarning(autoUnlinkWarning)
                    .keyPairSize(AuthenticatorConfig.Builder.KEYSIZE_MINIMUM)
                    .sslPinning(sslPinningEnabled)
                    .build();

            uiConfig = new AuthenticatorUIConfig.Builder()
                    .allowSecurityChangesWhenUnlinked(allowChangesWhenUnlinked)
                    .theme(R.style.DemoAppTheme)
                    .build(this);
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
            return;
        }

        if (getAuthenticatorManager().isDeviceLinked()) {
            // Force-unlink to clear any data before re-initializing
            getAuthenticatorManager().unlinkDevice(null, new UnlinkDeviceEventCallback() {
                @Override
                public void onSuccess(final @NonNull Device device) {
                    getAuthenticatorManager().initialize(config);
                    getAuthenticatorUIManager().initialize(uiConfig);
                    final Intent i = new Intent();
                    i.putExtra(ListDemoActivity.EXTRA_SDK_KEY, key);
                    setResult(0, i);
                    finish();
                }

                @Override
                public void onFailure(final @NonNull Exception e) {
                    getAuthenticatorManager().initialize(config);
                    getAuthenticatorUIManager().initialize(uiConfig);
                    final Intent i = new Intent();
                    i.putExtra(ListDemoActivity.EXTRA_SDK_KEY, key);
                    setResult(0, i);
                    finish();
                }
            });
        } else {
            getAuthenticatorManager().initialize(config);
            getAuthenticatorUIManager().initialize(uiConfig);
            final Intent i = new Intent();
            i.putExtra(ListDemoActivity.EXTRA_SDK_KEY, key);
            setResult(0, i);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        final Intent i = new Intent();
        i.putExtra(ListDemoActivity.EXTRA_SDK_KEY, originalSdkKey);
        setResult(0, i);
        super.onBackPressed();
    }

    private static void disallowAuthMethod(final @NonNull AuthenticatorConfig.Builder configBuilder, final @NonNull AuthMethod authMethod, final boolean allow) {
        if (!allow) {
            configBuilder.disallowAuthMethod(authMethod);
        }
    }

    private void showError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Could not reinitialize SDK")
                .setMessage(message)
                .setNeutralButton(R.string.demo_generic_ok, null)
                .create()
                .show();
    }
}
