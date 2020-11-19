package com.launchkey.android.authenticator.demo.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.databinding.DemoActivityConfigsBinding
import com.launchkey.android.authenticator.sdk.core.auth_method_management.AuthMethod
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorConfig
import com.launchkey.android.authenticator.sdk.core.authentication_management.Device
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.UnlinkDeviceEventCallback
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIConfig

class AppConfigsActivity : BaseDemoActivity<DemoActivityConfigsBinding>(R.layout.demo_activity_configs) {
    private var originalSdkKey: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DemoActivityConfigsBinding.bind(findViewById(R.id.configs_root))
        binding!!.configsButton.setOnClickListener { onReinitSdk() }
        binding!!.configsBuildHash.text = getString(
                R.string.demo_commit_hash_title,
                getString(R.string.demo_commit_hash))
        updateUi()
    }

    @SuppressLint("SetTextI18n")
    private fun updateUi() {
        val i = intent
        if (i != null) {
            originalSdkKey = i.getStringExtra(ListDemoActivity.EXTRA_SDK_KEY)
            binding!!.configsSdkKey.setText(if (originalSdkKey == null) "" else originalSdkKey)
        }

        // Prep all hints at runtime
        val delaySecondsMin = 0
        val delaySecondsMax = 60 * 60 * 24
        val delayHints = getString(
                R.string.demo_activity_list_feature_config_hints_format, delaySecondsMin, delaySecondsMax)
        binding!!.configsDelayWearables.hint = delayHints
        binding!!.configsDelayLocations.hint = delayHints
        val authFailureHint = getString(
                R.string.demo_activity_list_feature_config_hints_format,
                AuthenticatorConfig.Builder.THRESHOLD_AUTHFAILURE_MINIMUM,
                AuthenticatorConfig.Builder.THRESHOLD_AUTHFAILURE_MAXIMUM)
        binding!!.configsAuthfailure.hint = authFailureHint
        val autoUnlinkHint = getString(
                R.string.demo_activity_list_feature_config_hints_format,
                AuthenticatorConfig.Builder.THRESHOLD_AUTOUNLINK_MINIMUM,
                AuthenticatorConfig.Builder.THRESHOLD_AUTOUNLINK_MAXIMUM)
        binding!!.configsAutounlink.hint = autoUnlinkHint

        // For the warning, max is derived from Auto Unlink max - 1
        val autoUnlinkWarningHint = getString(
                R.string.demo_activity_list_feature_config_hints_format,
                AuthenticatorConfig.Builder.THRESHOLD_AUTOUNLINK_WARNING_NONE,
                AuthenticatorConfig.Builder.THRESHOLD_AUTOUNLINK_MAXIMUM - 1)
        binding!!.configsAutounlinkwarning.hint = autoUnlinkWarningHint
        val config = authenticatorManager.config

        // Update UI to match SDK config passed upon initialization
        binding!!.configsPc.isChecked = config.isMethodAllowed(AuthMethod.PIN_CODE)
        binding!!.configsCc.isChecked = config.isMethodAllowed(AuthMethod.CIRCLE_CODE)
        binding!!.configsW.isChecked = config.isMethodAllowed(AuthMethod.WEARABLES)
        binding!!.configsL.isChecked = config.isMethodAllowed(AuthMethod.LOCATIONS)
        binding!!.configsFs.isChecked = config.isMethodAllowed(AuthMethod.BIOMETRIC)
        binding!!.configsDelayWearables.setText(Integer.toString(config.activationDelayWearablesSeconds()))
        binding!!.configsDelayLocations.setText(Integer.toString(config.activationDelayLocationsSeconds()))
        binding!!.configsAuthfailure.setText(Integer.toString(config.thresholdAuthFailure()))
        binding!!.configsAutounlink.setText(Integer.toString(config.thresholdAutoUnlink()))
        binding!!.configsAutounlinkwarning.setText(Integer.toString(config.thresholdAutoUnlinkWarning()))
        binding!!.configsAllowchangesunlinked.isChecked = authenticatorUIManager.config.areSecurityChangesAllowedWhenUnlinked()
        try {
            // If overriding domain or subdomain is null, default values will be used when specs are built
            val res = resources
            val subdomainOverrideResId = res.getIdentifier("lk_auth_sdk_oendsub", "string", packageName)
            val subdomainOverride: String?
            subdomainOverride = if (subdomainOverrideResId > 0) {
                res.getString(subdomainOverrideResId)
            } else {
                null
            }
            val subdomain: String
            subdomain = subdomainOverride ?: "mapi"
            val domainOverrideResId = res.getIdentifier("lk_auth_sdk_oenddom", "string", packageName)
            val domainOverride: String?
            domainOverride = if (domainOverrideResId > 0) {
                res.getString(domainOverrideResId)
            } else {
                null
            }
            val domain: String
            domain = domainOverride ?: "launchkey.com"
            val endpoint = getString(
                    R.string.demo_activity_list_feature_config_endpoint_format, subdomain, domain)
            binding!!.configsEndpoint.text = endpoint
            binding!!.configsEndpoint.visibility = View.VISIBLE
        } catch (e: Resources.NotFoundException) {
            // Do nothing
        }
    }

    private fun onReinitSdk() {
        // Reinitialize the SDK with whichever key we had last
        val key = binding!!.configsSdkKey.text.toString()
        if (key.trim { it <= ' ' }.isEmpty()) {
            showError("Key cannot be null or empty.")
            return
        }
        val sslPinningEnabled = binding!!.configsSslPinning.isChecked
        val allowPinCode = binding!!.configsPc.isChecked
        val allowCircleCode = binding!!.configsCc.isChecked
        val allowWearables = binding!!.configsW.isChecked
        val allowLocations = binding!!.configsL.isChecked
        val allowFingerprintScan = binding!!.configsFs.isChecked
        val delayWearablesStr = binding!!.configsDelayWearables.text.toString()
        if (delayWearablesStr.trim { it <= ' ' }.isEmpty()) {
            showError("Activation delay for Wearables cannot be empty")
            return
        }
        val delayWearables: Int
        delayWearables = try {
            delayWearablesStr.toInt()
        } catch (e: NumberFormatException) {
            showError("Activation delay for Wearables must be a number")
            return
        }
        val delayLocationsStr = binding!!.configsDelayLocations.text.toString()
        if (delayLocationsStr.trim { it <= ' ' }.isEmpty()) {
            showError("Activation delay for Locations cannot be empty")
            return
        }
        val delayLocations: Int
        delayLocations = try {
            delayLocationsStr.toInt()
        } catch (e: NumberFormatException) {
            showError("Activation delay for Locations must be a number")
            return
        }
        val authFailureStr = binding!!.configsAuthfailure.text.toString()
        if (authFailureStr.trim { it <= ' ' }.isEmpty()) {
            showError("Auth Failure threshold cannot be empty")
            return
        }
        val authFailure: Int
        authFailure = try {
            authFailureStr.toInt()
        } catch (e: NumberFormatException) {
            showError("Auth Failure threshold must be a number")
            return
        }
        val autoUnlinkStr = binding!!.configsAutounlink.text.toString()
        if (autoUnlinkStr.trim { it <= ' ' }.isEmpty()) {
            showError("Auto Unlink threshold cannot be empty")
            return
        }
        val autoUnlink: Int
        autoUnlink = try {
            autoUnlinkStr.toInt()
        } catch (e: NumberFormatException) {
            showError("Auto Unlink threshold must be a number")
            return
        }
        val autoUnlinkWarningStr = binding!!.configsAutounlinkwarning.text.toString()
        if (autoUnlinkWarningStr.trim { it <= ' ' }.isEmpty()) {
            showError("Auto Unlink warning threshold cannot be empty")
            return
        }
        val autoUnlinkWarning: Int
        autoUnlinkWarning = try {
            autoUnlinkWarningStr.toInt()
        } catch (e: NumberFormatException) {
            showError("Auto Unlink warning threshold must be a number")
            return
        }
        val allowChangesWhenUnlinked = binding!!.configsAllowchangesunlinked.isChecked

        // Let Authenticator SDK validate threshold arguments
        val config: AuthenticatorConfig
        val uiConfig: AuthenticatorUIConfig
        try {
            val builder = AuthenticatorConfig.Builder()
            disallowAuthMethod(builder, AuthMethod.PIN_CODE, allowPinCode)
            disallowAuthMethod(builder, AuthMethod.CIRCLE_CODE, allowCircleCode)
            disallowAuthMethod(builder, AuthMethod.WEARABLES, allowWearables)
            disallowAuthMethod(builder, AuthMethod.LOCATIONS, allowLocations)
            disallowAuthMethod(builder, AuthMethod.BIOMETRIC, allowFingerprintScan)
            config = builder
                    .activationDelayWearable(delayWearables)
                    .activationDelayLocation(delayLocations)
                    .thresholdAuthFailure(authFailure)
                    .thresholdAutoUnlink(autoUnlink)
                    .thresholdAutoUnlinkWarning(autoUnlinkWarning)
                    .keyPairSize(AuthenticatorConfig.Builder.KEYSIZE_MINIMUM)
                    .sslPinning(sslPinningEnabled)
                    .build()
            uiConfig = AuthenticatorUIConfig.Builder()
                    .allowSecurityChangesWhenUnlinked(allowChangesWhenUnlinked)
                    .theme(R.style.DemoAppTheme)
                    .build(this)
        } catch (e: IllegalArgumentException) {
            showError(e.message)
            return
        }
        if (authenticatorManager.isDeviceLinked) {
            // Force-unlink to clear any data before re-initializing
            authenticatorManager.unlinkDevice(null, object : UnlinkDeviceEventCallback() {
                override fun onSuccess(device: Device) {
                    authenticatorManager.initialize(config)
                    authenticatorUIManager.initialize(uiConfig)
                    val i = Intent()
                    i.putExtra(ListDemoActivity.EXTRA_SDK_KEY, key)
                    setResult(0, i)
                    finish()
                }

                override fun onFailure(e: Exception) {
                    authenticatorManager.initialize(config)
                    authenticatorUIManager.initialize(uiConfig)
                    val i = Intent()
                    i.putExtra(ListDemoActivity.EXTRA_SDK_KEY, key)
                    setResult(0, i)
                    finish()
                }
            })
        } else {
            authenticatorManager.initialize(config)
            authenticatorUIManager.initialize(uiConfig)
            val i = Intent()
            i.putExtra(ListDemoActivity.EXTRA_SDK_KEY, key)
            setResult(0, i)
            finish()
        }
    }

    override fun onBackPressed() {
        val i = Intent()
        i.putExtra(ListDemoActivity.EXTRA_SDK_KEY, originalSdkKey)
        setResult(0, i)
        super.onBackPressed()
    }

    private fun showError(message: String?) {
        AlertDialog.Builder(this)
                .setTitle("Could not reinitialize SDK")
                .setMessage(message)
                .setNeutralButton(R.string.demo_generic_ok, null)
                .create()
                .show()
    }

    companion object {
        private fun disallowAuthMethod(configBuilder: AuthenticatorConfig.Builder, authMethod: AuthMethod, allow: Boolean) {
            if (!allow) {
                configBuilder.disallowAuthMethod(authMethod)
            }
        }
    }
}