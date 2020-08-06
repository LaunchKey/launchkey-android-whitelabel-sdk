package com.launchkey.android.authenticator.demo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.app.DemoApplication
import com.launchkey.android.authenticator.demo.ui.adapter.DemoFeatureAdapter
import com.launchkey.android.authenticator.demo.ui.fragment.*
import com.launchkey.android.authenticator.sdk.AuthenticatorManager
import com.launchkey.android.authenticator.sdk.DeviceLinkedEventCallback
import com.launchkey.android.authenticator.sdk.DeviceUnlinkedEventCallback
import com.launchkey.android.authenticator.sdk.device.Device
import com.launchkey.android.authenticator.sdk.device.DeviceManager
import com.launchkey.android.authenticator.sdk.error.BaseError
import com.launchkey.android.authenticator.sdk.ui.fragment.DevicesFragment
import com.launchkey.android.authenticator.sdk.ui.fragment.SessionsFragment
import java.util.*

class ListDemoActivity : BaseDemoActivity(), OnItemClickListener {
    private lateinit var mList: ListView
    private var mAdapter: DemoFeatureAdapter? = null
    private val mDeviceLinkedCallback: DeviceLinkedEventCallback = object : DeviceLinkedEventCallback() {
        override fun onEventResult(successful: Boolean, error: BaseError, device: Device) {
            updateUi()
        }
    }
    private val mDeviceUnlinkedCallback: DeviceUnlinkedEventCallback = object : DeviceUnlinkedEventCallback() {
        override fun onEventResult(successful: Boolean, error: BaseError?, o: Any?) {
            updateUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_list)
        // Makes sure we only initialize the sample app once (instead of also instantiating it in
// DemoApplication)
        val sdkKey = intent.getStringExtra("sdkKey")
        val action = intent.action
        if (action != null && action == "android.intent.action.MAIN") {
            (application as DemoApplication).initialize(sdkKey)
        }
        val toolbar = findViewById<Toolbar>(R.id.list_toolbar)
        toolbar?.let { setSupportActionBar(it) }
        mAdapter = DemoFeatureAdapter(this, FEATURES_RES)
        mList = findViewById(R.id.list_listview)
        mList.setAdapter(mAdapter)
        mList.setOnItemClickListener(this)
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
            AuthenticatorManager.getInstance().onPushNotification(extras)
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
            val device = DeviceManager.getInstance().currentDevice
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
        val featureStringId: Int = mAdapter!!.getItem(position)!!
        val linked = authenticatorManager.isDeviceLinked
        var fragmentClassName: Class<*>? = null
        when (featureStringId) {
            R.string.demo_activity_list_feature_link_default_manual -> if (linked) {
                showError(ERROR_DEVICE_LINKED)
            } else {
                authenticatorManager.startLinkingActivity(this, AuthenticatorManager.LINKING_METHOD_MANUAL)
            }
            R.string.demo_activity_list_feature_link_default_scanner -> if (linked) {
                showError(ERROR_DEVICE_LINKED)
            } else {
                authenticatorManager.startLinkingActivity(this, AuthenticatorManager.LINKING_METHOD_SCAN)
            }
            R.string.demo_activity_list_feature_link_custom -> if (linked) {
                showError(ERROR_DEVICE_LINKED)
            } else {
                fragmentClassName = CustomLinkingFragment::class.java
            }
            R.string.demo_activity_list_feature_security -> authenticatorManager.startSecurityActivity(this)
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
            R.string.demo_activity_list_feature_logout_custom2 -> if (!linked) {
                showError(ERROR_DEVICE_UNLINKED)
            } else {
                fragmentClassName = CustomLogoutFragment2::class.java
            }
            R.string.demo_activity_list_feature_unlink_custom2 -> if (!linked) {
                showError(ERROR_DEVICE_UNLINKED)
            } else {
                fragmentClassName = CustomUnlinkFragment2::class.java
            }
            R.string.demo_activity_list_feature_sessions_default -> if (!linked) {
                showError(ERROR_DEVICE_UNLINKED)
            } else {
                fragmentClassName = SessionsFragment::class.java
            }
            R.string.demo_activity_list_feature_sessions_custom -> if (!linked) {
                showError(ERROR_DEVICE_UNLINKED)
            } else {
                fragmentClassName = CustomSessionsFragment::class.java
            }
            R.string.demo_activity_list_feature_devices_default -> if (!linked) {
                showError(ERROR_DEVICE_UNLINKED)
            } else {
                fragmentClassName = DevicesFragment::class.java
            }
            R.string.demo_activity_list_feature_devices_custom3 -> if (!linked) {
                showError(ERROR_DEVICE_UNLINKED)
            } else {
                fragmentClassName = CustomDevicesFragment3::class.java
            }
            R.string.demo_activity_list_feature_config -> {
                val appConfigsActivity = Intent(this, AppConfigsActivity::class.java)
                startActivity(appConfigsActivity)
            }
        }
        if (fragmentClassName != null) { //The full class name of a Fragment will be passed to the activity
// so it's automatically instantiated by name and placed in a container.
            val fragmentActivity = Intent(this, GenericFragmentDemoActivity::class.java)
            fragmentActivity.putExtra(GenericFragmentDemoActivity.EXTRA_FRAGMENT_CLASS, fragmentClassName.canonicalName)
            fragmentActivity.putExtra(GenericFragmentDemoActivity.EXTRA_TITLE, getString(featureStringId))
            startActivity(fragmentActivity)
        }
    }

    private fun showError(messageRes: Int) {
        showError(getString(messageRes))
    }

    private fun showError(message: String) {
        showMessage(getString(R.string.demo_generic_error, message))
    }

    private fun showMessage(message: String) {
        Snackbar.make(mList!!, message, Snackbar.LENGTH_LONG).show()
    }

    companion object {
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
                R.string.demo_activity_list_feature_logout_custom2,
                R.string.demo_activity_list_feature_unlink_custom2,
                R.string.demo_activity_list_feature_sessions_default,
                R.string.demo_activity_list_feature_sessions_custom,
                R.string.demo_activity_list_feature_devices_default,
                R.string.demo_activity_list_feature_devices_custom3,
                R.string.demo_activity_list_feature_config
        )
    }
}