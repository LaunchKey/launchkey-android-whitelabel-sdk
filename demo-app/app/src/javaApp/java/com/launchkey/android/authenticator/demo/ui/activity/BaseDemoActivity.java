package com.launchkey.android.authenticator.demo.ui.activity;

import android.support.v7.app.AppCompatActivity;

import com.launchkey.android.authenticator.demo.ui.BaseDemoView;
import com.launchkey.android.authenticator.sdk.AuthenticatorManager;

public class BaseDemoActivity extends AppCompatActivity implements BaseDemoView {

    private final AuthenticatorManager mAuthenticatorManager = AuthenticatorManager.getInstance();

    @Override
    public AuthenticatorManager getAuthenticatorManager() {
        return mAuthenticatorManager;
    }
}
