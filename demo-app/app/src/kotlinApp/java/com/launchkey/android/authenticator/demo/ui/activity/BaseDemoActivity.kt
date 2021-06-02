package com.launchkey.android.authenticator.demo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.launchkey.android.authenticator.demo.ui.BaseDemoView
import com.launchkey.android.authenticator.sdk.core.auth_request_management.AuthRequestManager
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIManager
import java.util.concurrent.Executors

open class BaseDemoActivity<T : ViewBinding> : AppCompatActivity, BaseDemoView {
    protected var binding: T? = null
    private val viewModelProviderFactory: ViewModelProvider.Factory = object : AbstractSavedStateViewModelFactory(this, null) {
        override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
            return if (modelClass == AuthRequestActivityViewModel::class.java) {
                AuthRequestActivityViewModel(AuthRequestManager.instance, Executors.newSingleThreadExecutor(), handle) as T
            } else {
                super@BaseDemoActivity.getDefaultViewModelProviderFactory().create(modelClass)
            }
        }
    }

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override val authenticatorManager = AuthenticatorManager.instance

    constructor() : super() {}
    constructor(layoutId: Int) : super(layoutId) {}

    override val authenticatorUIManager: AuthenticatorUIManager = AuthenticatorUIManager.instance

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}