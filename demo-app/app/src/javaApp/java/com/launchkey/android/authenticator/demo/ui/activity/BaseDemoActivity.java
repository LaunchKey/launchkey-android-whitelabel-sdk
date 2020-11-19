package com.launchkey.android.authenticator.demo.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.launchkey.android.authenticator.demo.ui.BaseDemoView;
import com.launchkey.android.authenticator.sdk.core.auth_request_management.AuthRequestManager;
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager;
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIManager;

import java.util.concurrent.Executors;

public class BaseDemoActivity<T extends ViewBinding> extends AppCompatActivity implements BaseDemoView {
    protected T binding;

    private final ViewModelProvider.Factory viewModelProviderFactory = new AbstractSavedStateViewModelFactory(this, null) {
        @NonNull
        @Override
        protected <T extends ViewModel> T create(@NonNull String key, @NonNull Class<T> modelClass, @NonNull SavedStateHandle handle) {
            if (modelClass == AuthRequestActivityViewModel.class) {
                return (T) new AuthRequestActivityViewModel(AuthRequestManager.instance, Executors.newSingleThreadExecutor(), handle);
            } else {
                return BaseDemoActivity.super.getDefaultViewModelProviderFactory().create(modelClass);
            }
        }
    };

    @NonNull
    @Override
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        return viewModelProviderFactory;
    }

    private final AuthenticatorManager authenticatorManager = AuthenticatorManager.instance;
    public BaseDemoActivity() {
        super();
    }

    public BaseDemoActivity(final int layoutId) {
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
    protected void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
