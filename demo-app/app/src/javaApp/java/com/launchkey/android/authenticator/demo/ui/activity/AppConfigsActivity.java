package com.launchkey.android.authenticator.demo.ui.activity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.sdk.AuthenticatorConfig;
import com.launchkey.android.authenticator.sdk.AuthenticatorManager;
import com.launchkey.android.authenticator.sdk.SimpleOperationCallback;
import com.launchkey.android.authenticator.sdk.error.BaseError;
import com.launchkey.android.authenticator.sdk.security.SecurityService;

public class AppConfigsActivity extends BaseDemoActivity {
    private EditText mSdkKey;
    private Switch mSslPinning;
    private Switch mPinCode, mCircleCode, mWearables, mLocations, mFingerprintScan;
    private EditText mDelayWearables, mDelayLocations;
    private EditText mAuthFailure, mAutoUnlink, mAutoUnlinkWarning;
    private Switch mAllowChangesWhenUnlinked;
    private TextView mEndpoint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_configs);

        mSdkKey = findViewById(R.id.configs_sdk_key);
        mSslPinning = findViewById(R.id.configs_ssl_pinning);

        mPinCode = findViewById(R.id.configs_pc);
        mCircleCode = findViewById(R.id.configs_cc);
        mWearables = findViewById(R.id.configs_w);
        mLocations = findViewById(R.id.configs_l);
        mFingerprintScan = findViewById(R.id.configs_fs);

        mDelayWearables = findViewById(R.id.configs_delay_wearables);
        mDelayLocations = findViewById(R.id.configs_delay_locations);

        mAuthFailure = findViewById(R.id.configs_authfailure);
        mAutoUnlink = findViewById(R.id.configs_autounlink);
        mAutoUnlinkWarning = findViewById(R.id.configs_autounlinkwarning);

        mAllowChangesWhenUnlinked = findViewById(R.id.configs_allowchangesunlinked);
        mEndpoint = findViewById(R.id.configs_endpoint);

        findViewById(R.id.configs_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReinitSdk();
            }
        });

        ((TextView) findViewById(R.id.configs_build_hash)).setText(
                getString(
                        R.string.demo_commit_hash_title,
                        getString(R.string.demo_commit_hash)));

        updateUi();
    }

    @SuppressLint("SetTextI18n")
    private void updateUi() {

        // Prep all hints at runtime

        final int delaySecondsMin = 0;
        final int delaySecondsMax = 60 * 60 * 24;
        final String delayHints = getString(
                R.string.demo_activity_list_feature_config_hints_format, delaySecondsMin, delaySecondsMax);
        mDelayWearables.setHint(delayHints);
        mDelayLocations.setHint(delayHints);

        final String authFailureHint = getString(
                R.string.demo_activity_list_feature_config_hints_format,
                AuthenticatorConfig.Builder.THRESHOLD_AUTHFAILURE_MINIMUM,
                AuthenticatorConfig.Builder.THRESHOLD_AUTHFAILURE_MAXIMUM);
        mAuthFailure.setHint(authFailureHint);

        final String autoUnlinkHint = getString(
                R.string.demo_activity_list_feature_config_hints_format,
                AuthenticatorConfig.Builder.THRESHOLD_AUTOUNLINK_MINIMUM,
                AuthenticatorConfig.Builder.THRESHOLD_AUTOUNLINK_MAXIMUM);
        mAutoUnlink.setHint(autoUnlinkHint);

        // For the warning, max is derived from Auto Unlink max - 1
        final String autoUnlinkWarningHint = getString(
                R.string.demo_activity_list_feature_config_hints_format,
                AuthenticatorConfig.Builder.THRESHOLD_AUTOUNLINK_WARNING_NONE,
                AuthenticatorConfig.Builder.THRESHOLD_AUTOUNLINK_MAXIMUM - 1);
        mAutoUnlinkWarning.setHint(autoUnlinkWarningHint);

        if (!getAuthenticatorManager().isInitialized()) {
            return;
        }

        final AuthenticatorConfig config = AuthenticatorManager.getInstance().getConfig();

        // Update UI to match SDK config passed upon initialization
        mSdkKey.setText(config.sdkKey());
        mSslPinning.setChecked(config.isSslPinningRequired());
        mPinCode.setChecked(config.isMethodAllowedSimple(SecurityService.FACTOR_PIN));
        mCircleCode.setChecked(config.isMethodAllowedSimple(SecurityService.FACTOR_CIRCLE));
        mWearables.setChecked(config.isMethodAllowedSimple(SecurityService.FACTOR_PROXIMITY));
        mLocations.setChecked(config.isMethodAllowedSimple(SecurityService.FACTOR_GEOFENCING));
        mFingerprintScan.setChecked(config.isMethodAllowedSimple(SecurityService.FACTOR_FINGERPRINT));
        mDelayWearables.setText(Integer.toString(config.activationDelayProximitySeconds()));
        mDelayLocations.setText(Integer.toString(config.activationDelayGeofencingSeconds()));
        mAuthFailure.setText(Integer.toString(config.thresholdAuthFailure()));
        mAutoUnlink.setText(Integer.toString(config.thresholdAutoUnlink()));
        mAutoUnlinkWarning.setText(Integer.toString(config.thresholdAutoUnlinkWarning()));
        mAllowChangesWhenUnlinked.setChecked(config.areSecurityChangesAllowedWhenUnlinked());

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
            mEndpoint.setText(endpoint);
            mEndpoint.setVisibility(View.VISIBLE);
        } catch (Resources.NotFoundException e) {
            // Do nothing
        }
    }

    private void onReinitSdk() {
        // Reinitialize the SDK with whichever key we had last
        final String key = mSdkKey.getText().toString();

        if (key.trim().isEmpty()) {
            showError("Key cannot be null or empty.");
            return;
        }

        final boolean sslPinningEnabled = mSslPinning.isChecked();
        final boolean allowPinCode = mPinCode.isChecked();
        final boolean allowCircleCode = mCircleCode.isChecked();
        final boolean allowWearables = mWearables.isChecked();
        final boolean allowLocations = mLocations.isChecked();
        final boolean allowFingerprintScan = mFingerprintScan.isChecked();
        final String delayWearablesStr = mDelayWearables.getText().toString();

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

        final String delayLocationsStr = mDelayLocations.getText().toString();

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

        final String authFailureStr = mAuthFailure.getText().toString();

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

        final String autoUnlinkStr = mAutoUnlink.getText().toString();

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

        final String autoUnlinkWarningStr = mAutoUnlinkWarning.getText().toString();

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

        final boolean allowChangesWhenUnlinked = mAllowChangesWhenUnlinked.isChecked();

        // Let Authenticator SDK validate threshold arguments

        final AuthenticatorConfig config;

        try {
            config = new AuthenticatorConfig.Builder(this, key)
                    .allowAuthMethod(SecurityService.FACTOR_PIN, allowPinCode)
                    .allowAuthMethod(SecurityService.FACTOR_CIRCLE, allowCircleCode)
                    .allowAuthMethod(SecurityService.FACTOR_PROXIMITY, allowWearables)
                    .allowAuthMethod(SecurityService.FACTOR_GEOFENCING, allowLocations)
                    .allowAuthMethod(SecurityService.FACTOR_FINGERPRINT, allowFingerprintScan)
                    .activationDelayProximity(delayWearables)
                    .activationDelayGeofencing(delayLocations)
                    .thresholdAuthFailure(authFailure)
                    .thresholdAutoUnlink(autoUnlink, autoUnlinkWarning)
                    .allowSecurityChangesWhenUnlinked(allowChangesWhenUnlinked)
                    .keyPairSize(AuthenticatorConfig.Builder.KEYSIZE_MINIMUM)
                    .sslPinning(sslPinningEnabled)
                    .theme(R.style.DemoAppTheme)
                    .build();
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
            return;
        }

        if (getAuthenticatorManager().isDeviceLinked()) {
            // Force-unlink to clear any data before re-initializing
            getAuthenticatorManager().unlinkCurrentDevice(new SimpleOperationCallback() {
                @Override
                public void onResult(boolean successful, BaseError error, Object extra) {
                    getAuthenticatorManager().initialize(config);
                    finish();
                }
            });
        } else {
            getAuthenticatorManager().initialize(config);
            finish();
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
