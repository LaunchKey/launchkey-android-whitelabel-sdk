package com.launchkey.android.authenticator.demo.ui.fragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.sdk.core.auth_method_management.AuthMethod;
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorConfig;
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager;
import com.launchkey.android.authenticator.sdk.core.authentication_management.Device;
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.UnlinkDeviceEventCallback;
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIConfig;
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIManager;
import com.launchkey.android.authenticator.sdk.ui.internal.viewmodel.SingleLiveEvent;
import com.launchkey.android.authenticator.sdk.ui.theme.AuthenticatorTheme;

public class AppConfigsViewModel extends ViewModel {
    @NonNull
    private final AuthenticatorManager authenticatorManager;

    @NonNull
    private final AuthenticatorUIManager authenticatorUIManager;

    @NonNull
    private final SavedStateHandle savedStateHandle;

    @NonNull
    private String configsSdkKey;
    @NonNull
    public String getConfigsSdkKey() { return configsSdkKey; }
    public void setConfigsSdkKey(@NonNull final String configsSdkKey) {
        this.savedStateHandle.set("configsSdkKey", configsSdkKey);
        this.configsSdkKey = configsSdkKey;
    }
    private boolean configsSslPinning;
    public boolean getConfigsSslPinning() { return configsSslPinning; }
    public void setConfigsSslPinning(final boolean configsSslPinning) {
        this.savedStateHandle.set("configsSslPinning", configsSslPinning);
        this.configsSslPinning = configsSslPinning;
    }
    private boolean configsPc;
    public boolean getConfigsPc() { return configsPc; }
    public void setConfigsPc(final boolean configsPc) {
        this.savedStateHandle.set("configsPc", configsPc);
        this.configsPc = configsPc;
    }
    private boolean configsCc;
    public boolean getConfigsCc() { return configsCc; }
    public void setConfigsCc(final boolean configsCc) {
        this.savedStateHandle.set("configsCc", configsCc);
        this.configsCc = configsCc;
    }
    private boolean configsW;
    public boolean getConfigsW() { return configsW; }
    public void setConfigsW(final boolean configsW) {
        this.savedStateHandle.set("configsW", configsW);
        this.configsW = configsW;
    }
    private boolean configsL;
    public boolean getConfigsL() { return configsL; }
    public void setConfigsL(final boolean configsL) {
        this.savedStateHandle.set("configsL", configsL);
        this.configsL = configsL;
    }
    private boolean configsFs;
    public boolean getConfigsFs() { return configsFs; }
    public void setConfigsFs(final boolean configsFs) {
        this.savedStateHandle.set("configsFs", configsFs);
        this.configsFs = configsFs;
    }
    private int configsDelayWearables;
    public int getConfigsDelayWearables() { return configsDelayWearables; }
    public void setConfigsDelayWearables(final int configsDelayWearables) {
        this.savedStateHandle.set("configsDelayWearables", configsDelayWearables);
        this.configsDelayWearables = configsDelayWearables;
    }
    private int configsDelayLocations;
    public int getConfigsDelayLocations() { return configsDelayLocations; }
    public void setConfigsDelayLocations(final int configsDelayLocations) {
        this.savedStateHandle.set("configsDelayLocations", configsDelayLocations);
        this.configsDelayLocations = configsDelayLocations;
    }
    private int configsAuthfailure;
    public int getConfigsAuthfailure() { return configsAuthfailure; }
    public void setConfigsAuthfailure(final int configsAuthfailure) {
        this.savedStateHandle.set("configsAuthfailure", configsAuthfailure);
        this.configsAuthfailure = configsAuthfailure;
    }
    private int configsAutounlink;
    public int getConfigsAutounlink() { return configsAutounlink; }
    public void setConfigsAutounlink(final int configsAutounlink) {
        this.savedStateHandle.set("configsAutounlink", configsAutounlink);
        this.configsAutounlink = configsAutounlink;
    }
    private int configsAutounlinkwarning;
    public int getConfigsAutounlinkwarning() { return configsAutounlinkwarning; }
    public void setConfigsAutounlinkwarning(final int configsAutounlinkwarning) {
        this.savedStateHandle.set("configsAutounlinkwarning", configsAutounlinkwarning);
        this.configsAutounlinkwarning = configsAutounlinkwarning;
    }
    private boolean configsAllowchangesunlinked;
    public boolean getConfigsAllowchangesunlinked() { return configsAllowchangesunlinked; }
    public void setConfigsAllowchangesunlinked(final boolean configsAllowchangesunlinked) {
        this.savedStateHandle.set("configsAllowchangesunlinked", configsAllowchangesunlinked);
        this.configsAllowchangesunlinked = configsAllowchangesunlinked;
    }
    private AuthenticatorTheme authenticatorTheme;
    public void setAuthenticatorTheme(final AuthenticatorTheme authenticatorTheme) {
        this.authenticatorTheme = authenticatorTheme;
    }
    
    public AppConfigsViewModel(@NonNull final AuthenticatorManager authenticatorManager,
                               @NonNull final AuthenticatorUIManager authenticatorUIManager,
                               @NonNull final String sdkKey,
                               @NonNull final SavedStateHandle savedStateHandle) {
        this.authenticatorManager = authenticatorManager;
        this.authenticatorUIManager = authenticatorUIManager;
        this.savedStateHandle = savedStateHandle;

        final AuthenticatorConfig currentConfig = authenticatorManager.getConfig();
        if (this.savedStateHandle.get("configsSdkKey") != null) configsSdkKey = this.savedStateHandle.get("configsSdkKey");
        else configsSdkKey = sdkKey;
        if (this.savedStateHandle.get("configsSslPinning") != null) configsSslPinning = this.savedStateHandle.get("configsSslPinning");
        else configsSslPinning = currentConfig.isSslPinningRequired();
        if (this.savedStateHandle.get("configsPc") != null) configsPc = this.savedStateHandle.get("configsPc");
        else configsPc = currentConfig.isMethodAllowed(AuthMethod.PIN_CODE);
        if (this.savedStateHandle.get("configsCc") != null) configsCc = this.savedStateHandle.get("configsCc");
        else configsCc = currentConfig.isMethodAllowed(AuthMethod.CIRCLE_CODE);;
        if (this.savedStateHandle.get("configsW") != null) configsW = this.savedStateHandle.get("configsW");
        else configsW = currentConfig.isMethodAllowed(AuthMethod.WEARABLES);;
        if (this.savedStateHandle.get("configsL") != null) configsL = this.savedStateHandle.get("configsL");
        else configsL = currentConfig.isMethodAllowed(AuthMethod.LOCATIONS);;
        if (this.savedStateHandle.get("configsFs") != null) configsFs = this.savedStateHandle.get("configsFs");
        else configsFs = currentConfig.isMethodAllowed(AuthMethod.BIOMETRIC);;
        if (this.savedStateHandle.get("configsDelayWearables") != null) configsDelayWearables = this.savedStateHandle.get("configsDelayWearables");
        else configsDelayWearables = currentConfig.activationDelayWearablesSeconds();
        if (this.savedStateHandle.get("configsDelayLocations") != null) configsDelayLocations = this.savedStateHandle.get("configsDelayLocations");
        else configsDelayLocations = currentConfig.activationDelayLocationsSeconds();
        if (this.savedStateHandle.get("configsAuthfailure") != null) configsAuthfailure = this.savedStateHandle.get("configsAuthfailure");
        else configsAuthfailure = currentConfig.thresholdAuthFailure();
        if (this.savedStateHandle.get("configsAutounlink") != null) configsAutounlink = this.savedStateHandle.get("configsAutounlink");
        else configsAutounlink = currentConfig.thresholdAutoUnlink();
        if (this.savedStateHandle.get("configsAutounlinkwarning") != null) configsAutounlinkwarning = this.savedStateHandle.get("configsAutounlinkwarning");
        else configsAutounlinkwarning = currentConfig.thresholdAutoUnlinkWarning();

        final AuthenticatorUIConfig currentUIConfig = authenticatorUIManager.getConfig();
        if (this.savedStateHandle.get("configsAllowchangesunlinked") != null) configsAllowchangesunlinked = this.savedStateHandle.get("configsAllowchangesunlinked");
        else configsAllowchangesunlinked = currentUIConfig.areSecurityChangesAllowedWhenUnlinked();
    }

    public void finalizeChangesToConfigAndRestart(@NonNull final Context context) {
        _state.setValue(new State.ChangesLoading());
        if (configsSdkKey.trim().isEmpty()) {
            _state.setValue(new State.ChangesFailed("Key cannot be null or empty."));
            return;
        }

        // Let Authenticator SDK validate threshold arguments
        final AuthenticatorConfig config;
        final AuthenticatorUIConfig uiConfig;
        try {
            AuthenticatorConfig.Builder builder = new AuthenticatorConfig.Builder();
            disallowAuthMethod(builder, AuthMethod.PIN_CODE, configsPc);
            disallowAuthMethod(builder, AuthMethod.CIRCLE_CODE, configsCc);
            disallowAuthMethod(builder, AuthMethod.WEARABLES, configsW);
            disallowAuthMethod(builder, AuthMethod.LOCATIONS, configsL);
            disallowAuthMethod(builder, AuthMethod.BIOMETRIC, configsFs);
            config = builder
                    .activationDelayWearable(configsDelayWearables)
                    .activationDelayLocation(configsDelayLocations)
                    .thresholdAuthFailure(configsAuthfailure)
                    .thresholdAutoUnlink(configsAutounlink)
                    .thresholdAutoUnlinkWarning(configsAutounlinkwarning)
                    .keyPairSize(AuthenticatorConfig.Builder.KEYSIZE_MINIMUM)
                    .sslPinning(configsSslPinning)
                    .build();

            uiConfig = new AuthenticatorUIConfig.Builder()
                    .allowSecurityChangesWhenUnlinked(configsAllowchangesunlinked)
                    .theme(authenticatorTheme)
                    .build(context.getApplicationContext());
        } catch (IllegalArgumentException e) {
            _state.setValue(new State.ChangesFailed(e.getMessage()));
            return;
        }

        if (authenticatorManager.isDeviceLinked()) {
            // Force-unlink to clear any data before re-initializing
            authenticatorManager.unlinkDevice(null, new UnlinkDeviceEventCallback() {
                @Override
                public void onSuccess(final @NonNull Device device) {
                    authenticatorManager.initialize(config);
                    authenticatorUIManager.initialize(uiConfig);
                    _state.setValue(new State.ChangesDone(configsSdkKey));
                }

                @Override
                public void onFailure(final @NonNull Exception e) {
                    authenticatorManager.initialize(config);
                    authenticatorUIManager.initialize(uiConfig);
                    _state.setValue(new State.ChangesDone(configsSdkKey));
                }
            });
        } else {
            authenticatorManager.initialize(config);
            authenticatorUIManager.initialize(uiConfig);
            _state.setValue(new State.ChangesDone(configsSdkKey));
        }
    }

    private static void disallowAuthMethod(final @NonNull AuthenticatorConfig.Builder configBuilder, final @NonNull AuthMethod authMethod, final boolean allow) {
        if (!allow) {
            configBuilder.disallowAuthMethod(authMethod);
        }
    }

    @NonNull
    private final MutableLiveData<State> _state = new SingleLiveEvent<>();
    public final LiveData<State> state = _state;

    public abstract static class State {
        public static final class ChangesFailed extends State {
            @NonNull
            public final String failure;
            ChangesFailed(@NonNull final String failure) { this.failure = failure; }
        }
        public static final class ChangesLoading extends State {}
        public static final class ChangesDone extends State {
            @NonNull
            public final String sdkKey;
            ChangesDone(@NonNull final String sdkKey) {
                this.sdkKey = sdkKey;
            }
        }
    }
}
