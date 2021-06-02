package com.launchkey.android.authenticator.demo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.databinding.DemoActivityListBinding
import com.launchkey.android.authenticator.demo.ui.activity.GenericFragmentDemoActivity
import com.launchkey.android.authenticator.demo.ui.adapter.DemoFeatureAdapter
import com.launchkey.android.authenticator.demo.ui.fragment.*
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager
import com.launchkey.android.authenticator.sdk.core.authentication_management.Device
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.DeviceLinkedEventCallback
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.UnlinkDeviceEventCallback
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIManager
import com.launchkey.android.authenticator.sdk.ui.fragment.DevicesFragment
import com.launchkey.android.authenticator.sdk.ui.fragment.SessionsFragment
import java.util.*

class ListDemoActivity : BaseDemoActivity<DemoActivityListBinding>(R.layout.demo_activity_list), OnItemClickListener {

    private val launcher = registerForActivityResult(StartActivityForResult()) { result -> sdkKey = if (result == null) null else result.data!!.getStringExtra(EXTRA_SDK_KEY) }
    private var mAdapter: DemoFeatureAdapter? = null
    private var sdkKey: String? = null
    private val mDeviceLinkedCallback: DeviceLinkedEventCallback = object : DeviceLinkedEventCallback() {
        override fun onSuccess(device: Device) {
            updateUi()
        }

        override fun onFailure(e: Exception) {
            updateUi()
        }
    }
    private val mDeviceUnlinkedCallback: UnlinkDeviceEventCallback = object : UnlinkDeviceEventCallback() {
        override fun onSuccess(device: Device) {
            updateUi()
        }

        override fun onFailure(e: Exception) {
            updateUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DemoActivityListBinding.bind(findViewById(R.id.demo_activity_list_root))
        sdkKey = intent.getStringExtra(EXTRA_SDK_KEY)
        if (sdkKey == null) {
            sdkKey = getString(R.string.authenticator_sdk_key)
        }
        setSupportActionBar(binding!!.listToolbar)
        mAdapter = DemoFeatureAdapter(this, FEATURES_RES)
        binding!!.listListview.adapter = mAdapter
        binding!!.listListview.onItemClickListener = this

        // Try processing Intent that could have extras from an FCM
        // notification if the app was in the background and running
        // on Android 8.0 (Oreo) and up. That payload is now handed
        // to this main entry Activity via Intent as extras by FCM.
        processIntent(intent)
    }

    private fun processIntent(i: Intent?) {
        if (i == null) {
            return
        }
        val extras = i.extras
        if (extras == null || extras.isEmpty) {
            return
        }
        if (!extras.getString("jwe", "").isEmpty()) {
            authenticatorManager.handlePushPayload(extras)
            i.putExtra(EXTRA_SHOW_REQUEST, true)
        }
        if (i.getBooleanExtra(EXTRA_SHOW_REQUEST, false)) {
            showRequest()
        }
    }

    private fun showRequest() {
        val authRequestActivity = Intent(this, AuthRequestActivity::class.java)
        startActivity(authRequestActivity)
    }

    private fun updateUi() {
        val assumingNotVisible = supportActionBar == null
        if (assumingNotVisible) {
            return
        }
        val nowLinked = authenticatorManager.isDeviceLinked
        val message: String
        message = if (nowLinked) {
            val device = AuthenticatorManager.instance.currentDevice
            String.format(Locale.getDefault(), "\"%s\"", device.name)
        } else {
            getString(ERROR_DEVICE_UNLINKED)
        }
        supportActionBar!!.title = getString(R.string.demo_activity_list_title_format, message)
    }

    override fun onResume() {
        super.onResume()
        updateUi()
        authenticatorManager.registerForEvents(mDeviceLinkedCallback, mDeviceUnlinkedCallback)
    }

    override fun onPause() {
        super.onPause()
        authenticatorManager.unregisterForEvents(mDeviceLinkedCallback, mDeviceUnlinkedCallback)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        val featureStringId = mAdapter!!.getItem(position)
        val linked = authenticatorManager.isDeviceLinked
        var fragmentClassName: Class<out Fragment?>? = null
        var goBackOnUnlink = false
        var forResult = false
        when (featureStringId) {
            R.string.demo_activity_list_feature_link_default_manual -> if (linked) {
                showError(ERROR_DEVICE_LINKED)
            } else {
                authenticatorUIManager.startLinkingActivity(this, AuthenticatorUIManager.LINKING_METHOD_MANUAL, sdkKey!!)
            }
            R.string.demo_activity_list_feature_link_default_scanner -> if (linked) {
                showError(ERROR_DEVICE_LINKED)
            } else {
                authenticatorUIManager.startLinkingActivity(this, AuthenticatorUIManager.LINKING_METHOD_SCAN, sdkKey!!)
            }
            R.string.demo_activity_list_feature_link_custom -> if (linked) {
                showError(ERROR_DEVICE_LINKED)
            } else {
                fragmentClassName = CustomLinkingFragment::class.java
            }
            R.string.demo_activity_list_feature_security -> authenticatorUIManager.startSecurityActivity(this)
            R.string.demo_activity_list_feature_security_custom -> {
                val customSecurityActivity = Intent(this, CustomSecurityActivity::class.java)
                startActivity(customSecurityActivity)
            }
            R.string.demo_activity_list_feature_securityinfo -> fragmentClassName = SecurityInfoFragment::class.java
            R.string.demo_activity_list_feature_requests_xml -> if (!linked) {
                showError(ERROR_DEVICE_UNLINKED)
            } else {
                val authRequestActivity = Intent(this, AuthRequestActivity::class.java)
                startActivity(authRequestActivity)
            }
            R.string.demo_activity_list_feature_logout_custom -> if (!linked) {
                showError(ERROR_DEVICE_UNLINKED)
            } else {
                fragmentClassName = CustomLogoutFragment::class.java
                goBackOnUnlink = true
            }
            R.string.demo_activity_list_feature_unlink_custom -> if (!linked) {
                showError(ERROR_DEVICE_UNLINKED)
            } else {
                fragmentClassName = CustomUnlinkFragment::class.java
                goBackOnUnlink = true
            }
            R.string.demo_activity_list_feature_sessions_default -> if (!linked) {
                showError(ERROR_DEVICE_UNLINKED)
            } else {
                fragmentClassName = SessionsFragment::class.java
                goBackOnUnlink = true
            }
            R.string.demo_activity_list_feature_sessions_custom -> if (!linked) {
                showError(ERROR_DEVICE_UNLINKED)
            } else {
                fragmentClassName = CustomSessionsFragment::class.java
                goBackOnUnlink = true
            }
            R.string.demo_activity_list_feature_devices_default -> if (!linked) {
                showError(ERROR_DEVICE_UNLINKED)
            } else {
                fragmentClassName = DevicesFragment::class.java
                goBackOnUnlink = true
            }
            R.string.demo_activity_list_feature_devices_custom -> if (!linked) {
                showError(ERROR_DEVICE_UNLINKED)
            } else {
                fragmentClassName = CustomDevicesFragment::class.java
                goBackOnUnlink = true
            }
            R.string.demo_activity_list_feature_config -> {
                fragmentClassName = AppConfigsFragment::class.java
                forResult = true
            }
        }
        if (fragmentClassName != null) {
            val fragmentActivity = Intent(this, GenericFragmentDemoActivity::class.java)
            fragmentActivity.putExtra(EXTRA_SDK_KEY, sdkKey)
            fragmentActivity.putExtra(GenericFragmentDemoActivity.EXTRA_FRAGMENT_CLASS, fragmentClassName.canonicalName)
            fragmentActivity.putExtra(GenericFragmentDemoActivity.EXTRA_TITLE, getString(featureStringId))
            fragmentActivity.putExtra(GenericFragmentDemoActivity.EXTRA_GO_BACK_ON_UNLINK, goBackOnUnlink)
            startGenericFragmentActivity(fragmentClassName, featureStringId, goBackOnUnlink, forResult)
        }
    }

    private fun startGenericFragmentActivity(fragmentClassName: Class<out Fragment?>,
                                             @StringRes featureStringId: Int,
                                             goBackOnUnlink: Boolean,
                                             forResult: Boolean) {
        //The full class name of a Fragment will be passed to the activity
        // so it's automatically instantiated by name and placed in a container.
        val i = Intent(this, GenericFragmentDemoActivity::class.java)
        i.putExtra(EXTRA_SDK_KEY, sdkKey)
        i.putExtra(GenericFragmentDemoActivity.EXTRA_FRAGMENT_CLASS, fragmentClassName.canonicalName)
        i.putExtra(GenericFragmentDemoActivity.EXTRA_TITLE, getString(featureStringId))
        i.putExtra(GenericFragmentDemoActivity.EXTRA_GO_BACK_ON_UNLINK, goBackOnUnlink)
        if (forResult) {
            launcher.launch(i)
        } else {
            startActivity(i)
        }
    }

    private fun showError(messageRes: Int) {
        showError(getString(messageRes))
    }

    private fun showError(message: String) {
        showMessage(getString(R.string.demo_generic_error, message))
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding!!.listListview, message, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        const val EXTRA_SDK_KEY = "extraSdkKey"
        const val EXTRA_SHOW_REQUEST = "extraShowRequest"

        @StringRes
        private val ERROR_DEVICE_UNLINKED = R.string.demo_generic_device_is_unlinked

        @StringRes
        private val ERROR_DEVICE_LINKED = R.string.demo_error_device_already_linked
        private val FEATURES_RES = intArrayOf(
                R.string.demo_activity_list_feature_link_default_manual,
                R.string.demo_activity_list_feature_link_default_scanner,
                R.string.demo_activity_list_feature_link_custom,
                R.string.demo_activity_list_feature_security,
                R.string.demo_activity_list_feature_security_custom,
                R.string.demo_activity_list_feature_securityinfo,
                R.string.demo_activity_list_feature_requests_xml,
                R.string.demo_activity_list_feature_logout_custom,
                R.string.demo_activity_list_feature_unlink_custom,
                R.string.demo_activity_list_feature_sessions_default,
                R.string.demo_activity_list_feature_sessions_custom,
                R.string.demo_activity_list_feature_devices_default,
                R.string.demo_activity_list_feature_devices_custom,
                R.string.demo_activity_list_feature_config
        )
    }
}