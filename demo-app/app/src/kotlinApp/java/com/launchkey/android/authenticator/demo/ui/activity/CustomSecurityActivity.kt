package com.launchkey.android.authenticator.demo.ui.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.launchkey.android.authenticator.demo.R

class CustomSecurityActivity : BaseDemoActivity<ViewBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_security)
        val toolbar = findViewById<Toolbar>(R.id.sec_toolbar)
        setSupportActionBar(toolbar)
    }
}