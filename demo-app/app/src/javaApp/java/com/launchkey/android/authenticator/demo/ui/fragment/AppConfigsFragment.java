package com.launchkey.android.authenticator.demo.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.databinding.DemoFragmentConfigsBinding;
import com.launchkey.android.authenticator.demo.ui.activity.ListDemoActivity;
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorConfig;

public class AppConfigsFragment extends BaseDemoFragment<DemoFragmentConfigsBinding> {
    @NonNull
    private AppConfigsViewModel appConfigsViewModel;
    @NonNull
    private AppConfigsThemeViewModel appConfigsThemeViewModel;

    public AppConfigsFragment() {
        super(R.layout.demo_fragment_configs);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = DemoFragmentConfigsBinding.bind(view);

        binding.configsButton.setOnClickListener(v -> onReinitSdk());

        binding.configsBuildHash.setText(
                getString(
                        R.string.demo_commit_hash_title,
                        getString(R.string.demo_commit_hash)));

        binding.themeButton.setOnClickListener(v -> {
            binding.configsRoot.setVisibility(View.GONE);
            binding.configsThemeFragment.setVisibility(View.VISIBLE);
        });

        final Bundle bundle = getArguments();
        final String originalSdkKey;
        if (bundle != null) {
            originalSdkKey = bundle.getString(ListDemoActivity.EXTRA_SDK_KEY);
            binding.configsSdkKey.setText(originalSdkKey == null ? "" : originalSdkKey);
        } else {
            originalSdkKey = "";
        }

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (binding.configsThemeFragment.getVisibility() == View.VISIBLE) {
                    binding.configsThemeFragment.setVisibility(View.GONE);
                    binding.configsRoot.setVisibility(View.VISIBLE);
                    return;
                }
                assert originalSdkKey != null;
                finishAndReturn(originalSdkKey);
            }
        });

        final ViewModelProvider viewModelProvider = new ViewModelProvider(this, new AbstractSavedStateViewModelFactory(this, null) {
            @NonNull
            @Override
            protected <T extends ViewModel> T create(@NonNull String key, @NonNull Class<T> modelClass, @NonNull SavedStateHandle handle) {
                if (modelClass.equals(AppConfigsViewModel.class)) {
                    return (T) new AppConfigsViewModel(getAuthenticatorManager(), getAuthenticatorUIManager(), originalSdkKey, handle);
                } else if (modelClass.equals(AppConfigsThemeViewModel.class)){
                    return (T) new AppConfigsThemeViewModel(getAuthenticatorUIManager(), requireActivity(), handle);
                }
                return null;
            }
        });
        appConfigsViewModel = viewModelProvider.get(AppConfigsViewModel.class);
        appConfigsThemeViewModel = viewModelProvider.get(AppConfigsThemeViewModel.class);

        appConfigsViewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state instanceof AppConfigsViewModel.State.ChangesDone) {
                finishAndReturn(((AppConfigsViewModel.State.ChangesDone)state).sdkKey);
            } else if (state instanceof AppConfigsViewModel.State.ChangesLoading) {
                // TODO: Add a loading screen
            } else if (state instanceof AppConfigsViewModel.State.ChangesFailed) {
                showError(((AppConfigsViewModel.State.ChangesFailed)state).failure);
            }
        });

        updateUi();
    }

    private void finishAndReturn(@NonNull final String sdkKey) {
        final Intent i = new Intent();
        i.putExtra(ListDemoActivity.EXTRA_SDK_KEY, sdkKey);
        requireActivity().setResult(Activity.RESULT_OK, i);
        requireActivity().finish();
    }

    private void updateUi() {
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
        binding.configsSdkKey.setText(appConfigsViewModel.getConfigsSdkKey());
        binding.configsSslPinning.setChecked(appConfigsViewModel.getConfigsSslPinning());
        binding.configsPc.setChecked(appConfigsViewModel.getConfigsPc());
        binding.configsCc.setChecked(appConfigsViewModel.getConfigsCc());
        binding.configsW.setChecked(appConfigsViewModel.getConfigsW());
        binding.configsL.setChecked(appConfigsViewModel.getConfigsL());
        binding.configsFs.setChecked(appConfigsViewModel.getConfigsFs());
        binding.configsDelayWearables.setText(String.valueOf(appConfigsViewModel.getConfigsDelayWearables()));
        binding.configsDelayLocations.setText(String.valueOf(appConfigsViewModel.getConfigsDelayLocations()));
        binding.configsAuthfailure.setText(String.valueOf(appConfigsViewModel.getConfigsAuthfailure()));
        binding.configsAutounlink.setText(String.valueOf(appConfigsViewModel.getConfigsAutounlink()));
        binding.configsAutounlinkwarning.setText(String.valueOf(appConfigsViewModel.getConfigsAutounlinkwarning()));
        binding.configsAllowchangesunlinked.setChecked(appConfigsViewModel.getConfigsAllowchangesunlinked());

        try {
            // If overriding domain or subdomain is null, default values will be used when specs are built
            final Resources res = getResources();
            final int subdomainOverrideResId =
                    res.getIdentifier("lk_auth_sdk_oendsub", "string", requireActivity().getPackageName());

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
                    res.getIdentifier("lk_auth_sdk_oenddom", "string", requireActivity().getPackageName());

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
        final String delayWearablesStr = binding.configsDelayWearables.getText().toString();
        if (delayWearablesStr.trim().isEmpty()) {
            showError("Activation delay for Wearables cannot be empty");
            return;
        }

        final String delayLocationsStr = binding.configsDelayLocations.getText().toString();
        if (delayLocationsStr.trim().isEmpty()) {
            showError("Activation delay for Locations cannot be empty");
            return;
        }

        final String authFailureStr = binding.configsAuthfailure.getText().toString();

        if (authFailureStr.trim().isEmpty()) {
            showError("Auth Failure threshold cannot be empty");
            return;
        }

        final String autoUnlinkStr = binding.configsAutounlink.getText().toString();

        if (autoUnlinkStr.trim().isEmpty()) {
            showError("Auto Unlink threshold cannot be empty");
            return;
        }

        final String autoUnlinkWarningStr = binding.configsAutounlinkwarning.getText().toString();

        if (autoUnlinkWarningStr.trim().isEmpty()) {
            showError("Auto Unlink warning threshold cannot be empty");
            return;
        }

        appConfigsViewModel.setConfigsSdkKey(binding.configsSdkKey.getText().toString());
        appConfigsViewModel.setConfigsSslPinning(binding.configsSslPinning.isChecked());
        appConfigsViewModel.setConfigsPc(binding.configsPc.isChecked());
        appConfigsViewModel.setConfigsCc(binding.configsCc.isChecked());
        appConfigsViewModel.setConfigsW(binding.configsW.isChecked());
        appConfigsViewModel.setConfigsL(binding.configsL.isChecked());
        appConfigsViewModel.setConfigsFs(binding.configsFs.isChecked());
        appConfigsViewModel.setConfigsDelayWearables(Integer.parseInt(binding.configsDelayWearables.getText().toString()));
        appConfigsViewModel.setConfigsDelayLocations(Integer.parseInt(binding.configsDelayLocations.getText().toString()));
        appConfigsViewModel.setConfigsAuthfailure(Integer.parseInt(binding.configsAuthfailure.getText().toString()));
        appConfigsViewModel.setConfigsAutounlink(Integer.parseInt(binding.configsAutounlink.getText().toString()));
        appConfigsViewModel.setConfigsAutounlinkwarning(Integer.parseInt(binding.configsAutounlinkwarning.getText().toString()));
        appConfigsViewModel.setConfigsAllowchangesunlinked(binding.configsAllowchangesunlinked.isChecked());
        appConfigsViewModel.setAuthenticatorTheme(appConfigsThemeViewModel.getAuthenticatorTheme(requireActivity()));

        appConfigsViewModel.finalizeChangesToConfigAndRestart(requireActivity());
    }

    private void showError(String message) {
        new AlertDialog.Builder(requireActivity())
                .setTitle("Could not reinitialize SDK")
                .setMessage(message)
                .setNeutralButton(R.string.demo_generic_ok, null)
                .create()
                .show();
    }
}
