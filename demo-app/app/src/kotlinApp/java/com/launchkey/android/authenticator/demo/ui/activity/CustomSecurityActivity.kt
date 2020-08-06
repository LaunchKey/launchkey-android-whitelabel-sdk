package com.launchkey.android.authenticator.demo.ui.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar

import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.sdk.ui.SecurityFragment

class CustomSecurityActivity : BaseDemoActivity() {

    private var mSecFragment: SecurityFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_security)

        val toolbar = findViewById<Toolbar>(R.id.sec_toolbar)
        setSupportActionBar(toolbar)

        mSecFragment = supportFragmentManager
                .findFragmentById(R.id.sec_security) as SecurityFragment?
    }
}
