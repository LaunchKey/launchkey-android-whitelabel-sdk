package com.launchkey.android.authenticator.demo.ui.fragment

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.launchkey.android.authenticator.demo.ui.BaseDemoView
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIManager

open class BaseDemoFragment<T : ViewBinding> : Fragment, BaseDemoView {
    protected var binding: T? = null
    override val authenticatorManager = AuthenticatorManager.instance

    constructor() : super() {}
    constructor(@LayoutRes layoutId: Int) : super(layoutId) {}

    override val authenticatorUIManager: AuthenticatorUIManager = AuthenticatorUIManager.instance

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}