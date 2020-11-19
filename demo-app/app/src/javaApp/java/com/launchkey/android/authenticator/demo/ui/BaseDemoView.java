package com.launchkey.android.authenticator.demo.ui;

import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager;
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIManager;

public interface BaseDemoView {
    AuthenticatorManager getAuthenticatorManager();
    AuthenticatorUIManager getAuthenticatorUIManager();
}
