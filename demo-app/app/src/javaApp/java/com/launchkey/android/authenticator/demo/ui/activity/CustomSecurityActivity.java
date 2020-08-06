package com.launchkey.android.authenticator.demo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.launchkey.android.authenticator.demo.R;

public class CustomSecurityActivity extends BaseDemoActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_security);

        final Toolbar toolbar = findViewById(R.id.sec_toolbar);
        setSupportActionBar(toolbar);
    }
}
