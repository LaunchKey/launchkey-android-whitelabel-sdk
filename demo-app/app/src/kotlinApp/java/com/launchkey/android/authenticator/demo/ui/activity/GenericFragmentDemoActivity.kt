package com.launchkey.android.authenticator.demo.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.databinding.DemoActivityFragmentBinding
import com.launchkey.android.authenticator.sdk.core.authentication_management.Device
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.UnlinkDeviceEventCallback
import com.launchkey.android.authenticator.sdk.core.exception.DeviceUnlinkedButFailedToNotifyServerException

class GenericFragmentDemoActivity : BaseDemoActivity<DemoActivityFragmentBinding>(R.layout.demo_activity_fragment) {
    private var goBackOnUnlink = false
    private val unlinkDeviceEventCallback: UnlinkDeviceEventCallback = object : UnlinkDeviceEventCallback() {
        override fun onSuccess(device: Device) {
            if (device.isCurrent && goBackOnUnlink) {
                finish()
            }
        }

        override fun onFailure(e: Exception) {
            if (e is DeviceUnlinkedButFailedToNotifyServerException && goBackOnUnlink) {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DemoActivityFragmentBinding.bind(findViewById(R.id.demo_activity_fragment_root))
        setSupportActionBar(binding!!.demoActivityFragmentToolbar)
        val i = intent
        val extras = i?.extras
        if (supportActionBar != null) {
            val title = extras?.getString(EXTRA_TITLE)
            supportActionBar!!.title = title ?: "Demo"
        }
        if (savedInstanceState != null) {
            return
        }
        val fragmentClass = extras?.getString(EXTRA_FRAGMENT_CLASS)
        if (fragmentClass == null) {
            finish()
            return
        }
        goBackOnUnlink = extras.getBoolean(EXTRA_GO_BACK_ON_UNLINK)

        //Instantiate the Fragment by name that was passed via extra (Bundle) and if not null,
        // then place it in the container.
        val f: Fragment
        f = try {
            Fragment.instantiate(this, fragmentClass)
        } catch (e: Exception) {
            finish()
            return
        }
        supportFragmentManager
                .beginTransaction()
                .add(R.id.demo_activity_fragment_container, f)
                .commit()
    }

    public override fun onResume() {
        super.onResume()
        authenticatorManager.registerForEvents(unlinkDeviceEventCallback)
    }

    public override fun onPause() {
        super.onPause()
        authenticatorManager.unregisterForEvents(unlinkDeviceEventCallback)
    }

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_FRAGMENT_CLASS = "fragment_class"
        const val EXTRA_GO_BACK_ON_UNLINK = "go_back_on_unlink"
    }
}