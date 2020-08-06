package com.launchkey.android.authenticator.demo.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.ui.BaseDemoView
import com.launchkey.android.authenticator.sdk.DeviceUnlinkedEventCallback
import com.launchkey.android.authenticator.sdk.error.BaseError

class GenericFragmentDemoActivity : BaseDemoActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_fragment)

        val toolbar = findViewById<Toolbar>(R.id.demo_activity_fragment_toolbar)
        setSupportActionBar(toolbar)

        val i = intent
        val extras = i?.extras

        val title = extras?.getString(EXTRA_TITLE)
        supportActionBar?.title = title ?: "Demo"

        if (savedInstanceState != null) {
            return
        }

        val fragmentClass = extras?.getString(EXTRA_FRAGMENT_CLASS)

        if (fragmentClass == null) {
            finish()
            return
        }

        //Instantiate the Fragment by name that was passed via extra (Bundle) and if not null,
        // then place it in the container.

        val f: Fragment?

        try {
            f = Fragment.instantiate(this, fragmentClass)
        } catch (e: Exception) {
            finish()
            return
        }

        if (f != null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.demo_activity_fragment_container, f)
                    .commit()
        }

        authenticatorManager.registerForEvents(object : DeviceUnlinkedEventCallback() {
            override fun onEventResult(b: Boolean, baseError: BaseError?, o: Any?) {
                finish()
            }
        })
    }

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_FRAGMENT_CLASS = "fragment_class"
    }
}
