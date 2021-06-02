package com.launchkey.android.authenticator.demo.ui.fragment

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.launchkey.android.authenticator.sdk.core.auth_method_management.AuthMethod
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorConfig
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager
import com.launchkey.android.authenticator.sdk.core.authentication_management.Device
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.UnlinkDeviceEventCallback
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIConfig
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIManager
import com.launchkey.android.authenticator.sdk.ui.internal.viewmodel.SingleLiveEvent
import com.launchkey.android.authenticator.sdk.ui.theme.AuthenticatorTheme

class AppConfigsViewModel(private val authenticatorManager: AuthenticatorManager,
                          private val authenticatorUIManager: AuthenticatorUIManager,
                          sdkKey: String,
                          private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private var configsSdkKey: String
    fun getConfigsSdkKey(): String {
        return configsSdkKey
    }

    fun setConfigsSdkKey(configsSdkKey: String) {
        savedStateHandle.set("configsSdkKey", configsSdkKey)
        this.configsSdkKey = configsSdkKey
    }

    private var configsSslPinning = false
    fun getConfigsSslPinning(): Boolean {
        return configsSslPinning
    }

    fun setConfigsSslPinning(configsSslPinning: Boolean) {
        savedStateHandle.set("configsSslPinning", configsSslPinning)
        this.configsSslPinning = configsSslPinning
    }

    private var configsPc = false
    fun getConfigsPc(): Boolean {
        return configsPc
    }

    fun setConfigsPc(configsPc: Boolean) {
        savedStateHandle.set("configsPc", configsPc)
        this.configsPc = configsPc
    }

    private var configsCc = false
    fun getConfigsCc(): Boolean {
        return configsCc
    }

    fun setConfigsCc(configsCc: Boolean) {
        savedStateHandle.set("configsCc", configsCc)
        this.configsCc = configsCc
    }

    private var configsW = false
    fun getConfigsW(): Boolean {
        return configsW
    }

    fun setConfigsW(configsW: Boolean) {
        savedStateHandle.set("configsW", configsW)
        this.configsW = configsW
    }

    private var configsL = false
    fun getConfigsL(): Boolean {
        return configsL
    }

    fun setConfigsL(configsL: Boolean) {
        savedStateHandle.set("configsL", configsL)
        this.configsL = configsL
    }

    private var configsFs = false
    fun getConfigsFs(): Boolean {
        return configsFs
    }

    fun setConfigsFs(configsFs: Boolean) {
        savedStateHandle.set("configsFs", configsFs)
        this.configsFs = configsFs
    }

    private var configsDelayWearables = 0
    fun getConfigsDelayWearables(): Int {
        return configsDelayWearables
    }

    fun setConfigsDelayWearables(configsDelayWearables: Int) {
        savedStateHandle.set("configsDelayWearables", configsDelayWearables)
        this.configsDelayWearables = configsDelayWearables
    }

    private var configsDelayLocations = 0
    fun getConfigsDelayLocations(): Int {
        return configsDelayLocations
    }

    fun setConfigsDelayLocations(configsDelayLocations: Int) {
        savedStateHandle.set("configsDelayLocations", configsDelayLocations)
        this.configsDelayLocations = configsDelayLocations
    }

    private var configsAuthfailure = 0
    fun getConfigsAuthfailure(): Int {
        return configsAuthfailure
    }

    fun setConfigsAuthfailure(configsAuthfailure: Int) {
        savedStateHandle.set("configsAuthfailure", configsAuthfailure)
        this.configsAuthfailure = configsAuthfailure
    }

    private var configsAutounlink = 0
    fun getConfigsAutounlink(): Int {
        return configsAutounlink
    }

    fun setConfigsAutounlink(configsAutounlink: Int) {
        savedStateHandle.set("configsAutounlink", configsAutounlink)
        this.configsAutounlink = configsAutounlink
    }

    private var configsAutounlinkwarning = 0
    fun getConfigsAutounlinkwarning(): Int {
        return configsAutounlinkwarning
    }

    fun setConfigsAutounlinkwarning(configsAutounlinkwarning: Int) {
        savedStateHandle.set("configsAutounlinkwarning", configsAutounlinkwarning)
        this.configsAutounlinkwarning = configsAutounlinkwarning
    }

    private var configsAllowchangesunlinked = false
    fun getConfigsAllowchangesunlinked(): Boolean {
        return configsAllowchangesunlinked
    }

    fun setConfigsAllowchangesunlinked(configsAllowchangesunlinked: Boolean) {
        savedStateHandle.set("configsAllowchangesunlinked", configsAllowchangesunlinked)
        this.configsAllowchangesunlinked = configsAllowchangesunlinked
    }

    private var authenticatorTheme: AuthenticatorTheme? = null
    fun setAuthenticatorTheme(authenticatorTheme: AuthenticatorTheme?) {
        this.authenticatorTheme = authenticatorTheme
    }

    fun finalizeChangesToConfigAndRestart(context: Context) {
        _state.value = State.ChangesLoading()
        if (configsSdkKey.trim { it <= ' ' }.isEmpty()) {
            _state.value = State.ChangesFailed("Key cannot be null or empty.")
            return
        }

        // Let Authenticator SDK validate threshold arguments
        val config: AuthenticatorConfig
        val uiConfig: AuthenticatorUIConfig
        try {
            val builder = AuthenticatorConfig.Builder()
            disallowAuthMethod(builder, AuthMethod.PIN_CODE, configsPc)
            disallowAuthMethod(builder, AuthMethod.CIRCLE_CODE, configsCc)
            disallowAuthMethod(builder, AuthMethod.WEARABLES, configsW)
            disallowAuthMethod(builder, AuthMethod.LOCATIONS, configsL)
            disallowAuthMethod(builder, AuthMethod.BIOMETRIC, configsFs)
            config = builder
                    .activationDelayWearable(configsDelayWearables)
                    .activationDelayLocation(configsDelayLocations)
                    .thresholdAuthFailure(configsAuthfailure)
                    .thresholdAutoUnlink(configsAutounlink)
                    .thresholdAutoUnlinkWarning(configsAutounlinkwarning)
                    .keyPairSize(AuthenticatorConfig.Builder.KEYSIZE_MINIMUM)
                    .sslPinning(configsSslPinning)
                    .build()
            uiConfig = AuthenticatorUIConfig.Builder()
                    .allowSecurityChangesWhenUnlinked(configsAllowchangesunlinked)
                    .theme(authenticatorTheme)
                    .build(context.applicationContext)
        } catch (e: IllegalArgumentException) {
            _state.value = State.ChangesFailed(e.message!!)
            return
        }
        if (authenticatorManager.isDeviceLinked) {
            // Force-unlink to clear any data before re-initializing
            authenticatorManager.unlinkDevice(null, object : UnlinkDeviceEventCallback() {
                override fun onSuccess(device: Device) {
                    authenticatorManager.initialize(config)
                    authenticatorUIManager.initialize(uiConfig)
                    _state.value = State.ChangesDone(configsSdkKey)
                }

                override fun onFailure(e: Exception) {
                    authenticatorManager.initialize(config)
                    authenticatorUIManager.initialize(uiConfig)
                    _state.value = State.ChangesDone(configsSdkKey)
                }
            })
        } else {
            authenticatorManager.initialize(config)
            authenticatorUIManager.initialize(uiConfig)
            _state.setValue(State.ChangesDone(configsSdkKey))
        }
    }

    private val _state: MutableLiveData<State> = SingleLiveEvent()
    val state: LiveData<State> = _state

    abstract class State {
        class ChangesFailed internal constructor(val failure: String) : State()
        class ChangesLoading : State()
        class ChangesDone internal constructor(val sdkKey: String) : State()
    }

    companion object {
        private fun disallowAuthMethod(configBuilder: AuthenticatorConfig.Builder, authMethod: AuthMethod, allow: Boolean) {
            if (!allow) {
                configBuilder.disallowAuthMethod(authMethod)
            }
        }
    }

    init {
        val currentConfig = authenticatorManager.config
        configsSdkKey = if (savedStateHandle.get<Any?>("configsSdkKey") != null) savedStateHandle.get("configsSdkKey")!! else sdkKey
        configsSslPinning = if (savedStateHandle.get<Any?>("configsSslPinning") != null) savedStateHandle.get("configsSslPinning")!! else currentConfig.isSslPinningRequired
        configsPc = if (savedStateHandle.get<Any?>("configsPc") != null) savedStateHandle.get("configsPc")!! else currentConfig.isMethodAllowed(AuthMethod.PIN_CODE)
        configsCc = if (savedStateHandle.get<Any?>("configsCc") != null) savedStateHandle.get("configsCc")!! else currentConfig.isMethodAllowed(AuthMethod.CIRCLE_CODE)
        configsW = if (savedStateHandle.get<Any?>("configsW") != null) savedStateHandle.get("configsW")!! else currentConfig.isMethodAllowed(AuthMethod.WEARABLES)
        configsL = if (savedStateHandle.get<Any?>("configsL") != null) savedStateHandle.get("configsL")!! else currentConfig.isMethodAllowed(AuthMethod.LOCATIONS)
        configsFs = if (savedStateHandle.get<Any?>("configsFs") != null) savedStateHandle.get("configsFs")!! else currentConfig.isMethodAllowed(AuthMethod.BIOMETRIC)
        configsDelayWearables = if (savedStateHandle.get<Any?>("configsDelayWearables") != null) savedStateHandle.get("configsDelayWearables")!! else currentConfig.activationDelayWearablesSeconds()
        configsDelayLocations = if (savedStateHandle.get<Any?>("configsDelayLocations") != null) savedStateHandle.get("configsDelayLocations")!! else currentConfig.activationDelayLocationsSeconds()
        configsAuthfailure = if (savedStateHandle.get<Any?>("configsAuthfailure") != null) savedStateHandle.get("configsAuthfailure")!! else currentConfig.thresholdAuthFailure()
        configsAutounlink = if (savedStateHandle.get<Any?>("configsAutounlink") != null) savedStateHandle.get("configsAutounlink")!! else currentConfig.thresholdAutoUnlink()
        configsAutounlinkwarning = if (savedStateHandle.get<Any?>("configsAutounlinkwarning") != null) savedStateHandle.get("configsAutounlinkwarning")!! else currentConfig.thresholdAutoUnlinkWarning()
        val currentUIConfig = authenticatorUIManager.config
        configsAllowchangesunlinked = if (savedStateHandle.get<Any?>("configsAllowchangesunlinked") != null) savedStateHandle.get("configsAllowchangesunlinked")!! else currentUIConfig.areSecurityChangesAllowedWhenUnlinked()
    }
}