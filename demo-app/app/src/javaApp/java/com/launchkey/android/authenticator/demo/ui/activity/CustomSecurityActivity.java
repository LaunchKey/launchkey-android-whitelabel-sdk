package com.launchkey.android.authenticator.demo.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.databinding.DemoActivitySecurityBinding;

public class CustomSecurityActivity extends BaseDemoActivity<DemoActivitySecurityBinding> {
    public CustomSecurityActivity() {
        super(R.layout.demo_activity_security);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DemoActivitySecurityBinding.bind(findViewById(R.id.custom_sec_root));
        setSupportActionBar(binding.secToolbar);
    }
}
