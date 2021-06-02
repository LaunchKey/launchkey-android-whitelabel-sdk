package com.launchkey.android.authenticator.demo.ui.fragment

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.databinding.DemoFragmentConfigsBinding
import com.launchkey.android.authenticator.demo.ui.activity.ListDemoActivity
import com.launchkey.android.authenticator.demo.ui.fragment.AppConfigsViewModel.State.*
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorConfig

class AppConfigsFragment : BaseDemoFragment<DemoFragmentConfigsBinding>(R.layout.demo_fragment_configs) {
    private lateinit var appConfigsViewModel: AppConfigsViewModel
    private lateinit var appConfigsThemeViewModel: AppConfigsThemeViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DemoFragmentConfigsBinding.bind(view)
        binding!!.configsButton.setOnClickListener { v -> onReinitSdk() }
        binding!!.configsBuildHash.text = getString(
                R.string.demo_commit_hash_title,
                getString(R.string.demo_commit_hash))
        binding!!.themeButton.setOnClickListener { v ->
            binding!!.configsRoot.visibility = View.GONE
            binding!!.configsThemeFragment.visibility = View.VISIBLE
        }
        val bundle = arguments
        val originalSdkKey: String?
        if (bundle != null) {
            originalSdkKey = bundle.getString(ListDemoActivity.EXTRA_SDK_KEY)
            binding!!.configsSdkKey.setText(originalSdkKey ?: "")
        } else {
            originalSdkKey = ""
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding!!.configsThemeFragment.visibility === View.VISIBLE) {
                    binding!!.configsThemeFragment.visibility = View.GONE
                    binding!!.configsRoot.visibility = View.VISIBLE
                    return
                }
                assert(originalSdkKey != null)
                finishAndReturn(originalSdkKey!!)
            }
        })
        val viewModelProvider = ViewModelProvider(this, object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
                if (modelClass == AppConfigsViewModel::class.java) {
                    return AppConfigsViewModel(authenticatorManager, authenticatorUIManager, originalSdkKey!!, handle) as T
                } else if (modelClass == AppConfigsThemeViewModel::class.java) {
                    return AppConfigsThemeViewModel(authenticatorUIManager, requireActivity(), handle) as T
                }
                return super.create(modelClass)
            }
        })
        appConfigsViewModel = viewModelProvider.get(AppConfigsViewModel::class.java)
        appConfigsThemeViewModel = viewModelProvider.get(AppConfigsThemeViewModel::class.java)
        appConfigsViewModel.state.observe(viewLifecycleOwner, { state: AppConfigsViewModel.State? ->
            if (state is ChangesDone) {
                finishAndReturn(state.sdkKey)
            } else if (state is ChangesLoading) {
                // TODO: Add a loading screen
            } else if (state is ChangesFailed) {
                showError(state.failure)
            }
        })
        updateUi()
    }

    private fun finishAndReturn(sdkKey: String) {
        val i = Intent()
        i.putExtra(ListDemoActivity.EXTRA_SDK_KEY, sdkKey)
        requireActivity().setResult(Activity.RESULT_OK, i)
        requireActivity().finish()
    }

    private fun updateUi() {
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
        binding!!.configsSdkKey.setText(appConfigsViewModel.getConfigsSdkKey())
        binding!!.configsSslPinning.isChecked = appConfigsViewModel.getConfigsSslPinning()
        binding!!.configsPc.isChecked = appConfigsViewModel.getConfigsPc()
        binding!!.configsCc.isChecked = appConfigsViewModel.getConfigsCc()
        binding!!.configsW.isChecked = appConfigsViewModel.getConfigsW()
        binding!!.configsL.isChecked = appConfigsViewModel.getConfigsL()
        binding!!.configsFs.isChecked = appConfigsViewModel.getConfigsFs()
        binding!!.configsDelayWearables.setText(appConfigsViewModel.getConfigsDelayWearables().toString())
        binding!!.configsDelayLocations.setText(appConfigsViewModel.getConfigsDelayLocations().toString())
        binding!!.configsAuthfailure.setText(appConfigsViewModel.getConfigsAuthfailure().toString())
        binding!!.configsAutounlink.setText(appConfigsViewModel.getConfigsAutounlink().toString())
        binding!!.configsAutounlinkwarning.setText(appConfigsViewModel.getConfigsAutounlinkwarning().toString())
        binding!!.configsAllowchangesunlinked.isChecked = appConfigsViewModel.getConfigsAllowchangesunlinked()
        try {
            // If overriding domain or subdomain is null, default values will be used when specs are built
            val res = resources
            val subdomainOverrideResId = res.getIdentifier("lk_auth_sdk_oendsub", "string", requireActivity().packageName)
            val subdomainOverride: String?
            subdomainOverride = if (subdomainOverrideResId > 0) {
                res.getString(subdomainOverrideResId)
            } else {
                null
            }
            val subdomain: String
            subdomain = subdomainOverride ?: "mapi"
            val domainOverrideResId = res.getIdentifier("lk_auth_sdk_oenddom", "string", requireActivity().packageName)
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
        val delayWearablesStr = binding!!.configsDelayWearables.text.toString()
        if (delayWearablesStr.trim { it <= ' ' }.isEmpty()) {
            showError("Activation delay for Wearables cannot be empty")
            return
        }
        val delayLocationsStr = binding!!.configsDelayLocations.text.toString()
        if (delayLocationsStr.trim { it <= ' ' }.isEmpty()) {
            showError("Activation delay for Locations cannot be empty")
            return
        }
        val authFailureStr = binding!!.configsAuthfailure.text.toString()
        if (authFailureStr.trim { it <= ' ' }.isEmpty()) {
            showError("Auth Failure threshold cannot be empty")
            return
        }
        val autoUnlinkStr = binding!!.configsAutounlink.text.toString()
        if (autoUnlinkStr.trim { it <= ' ' }.isEmpty()) {
            showError("Auto Unlink threshold cannot be empty")
            return
        }
        val autoUnlinkWarningStr = binding!!.configsAutounlinkwarning.text.toString()
        if (autoUnlinkWarningStr.trim { it <= ' ' }.isEmpty()) {
            showError("Auto Unlink warning threshold cannot be empty")
            return
        }
        appConfigsViewModel.setConfigsSdkKey(binding!!.configsSdkKey.text.toString())
        appConfigsViewModel.setConfigsSslPinning(binding!!.configsSslPinning.isChecked)
        appConfigsViewModel.setConfigsPc(binding!!.configsPc.isChecked)
        appConfigsViewModel.setConfigsCc(binding!!.configsCc.isChecked)
        appConfigsViewModel.setConfigsW(binding!!.configsW.isChecked)
        appConfigsViewModel.setConfigsL(binding!!.configsL.isChecked)
        appConfigsViewModel.setConfigsFs(binding!!.configsFs.isChecked)
        appConfigsViewModel.setConfigsDelayWearables(binding!!.configsDelayWearables.text.toString().toInt())
        appConfigsViewModel.setConfigsDelayLocations(binding!!.configsDelayLocations.text.toString().toInt())
        appConfigsViewModel.setConfigsAuthfailure(binding!!.configsAuthfailure.text.toString().toInt())
        appConfigsViewModel.setConfigsAutounlink(binding!!.configsAutounlink.text.toString().toInt())
        appConfigsViewModel.setConfigsAutounlinkwarning(binding!!.configsAutounlinkwarning.text.toString().toInt())
        appConfigsViewModel.setConfigsAllowchangesunlinked(binding!!.configsAllowchangesunlinked.isChecked)
        appConfigsViewModel.setAuthenticatorTheme(appConfigsThemeViewModel.getAuthenticatorTheme(requireActivity()))
        appConfigsViewModel.finalizeChangesToConfigAndRestart(requireActivity())
    }

    private fun showError(message: String) {
        AlertDialog.Builder(requireActivity())
                .setTitle("Could not reinitialize SDK")
                .setMessage(message)
                .setNeutralButton(R.string.demo_generic_ok, null)
                .create()
                .show()
    }
}