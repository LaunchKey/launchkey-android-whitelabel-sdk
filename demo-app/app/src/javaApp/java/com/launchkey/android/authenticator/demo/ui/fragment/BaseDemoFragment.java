package com.launchkey.android.authenticator.demo.ui.fragment;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.launchkey.android.authenticator.demo.ui.BaseDemoView;
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager;
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIManager;

public class BaseDemoFragment<T extends ViewBinding> extends Fragment implements BaseDemoView {
    protected T binding;
    private final AuthenticatorManager authenticatorManager = AuthenticatorManager.instance;
    public BaseDemoFragment() {
        super();
    }

    public BaseDemoFragment(@LayoutRes final int layoutId) {
        super(layoutId);
    }

    @Override
    public AuthenticatorManager getAuthenticatorManager() {
        return authenticatorManager;
    }

    private final AuthenticatorUIManager authenticatorUIManager = AuthenticatorUIManager.getInstance();

    @Override
    public AuthenticatorUIManager getAuthenticatorUIManager() {
        return authenticatorUIManager;
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
