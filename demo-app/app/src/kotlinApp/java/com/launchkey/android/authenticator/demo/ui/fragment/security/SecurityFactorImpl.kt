/*
 *  Copyright (c) 2016. LaunchKey, Inc. All rights reserved.
 */
package com.launchkey.android.authenticator.demo.ui.fragment.security

import com.launchkey.android.authenticator.sdk.core.auth_method_management.AuthMethod
import com.launchkey.android.authenticator.sdk.core.auth_method_management.AuthMethodType

internal class SecurityFactorImpl(override val authMethod: AuthMethod?, override val type: AuthMethodType?, override val isActive: Boolean) : SecurityFactor {

    constructor(factor: AuthMethod?, category: AuthMethodType?) : this(factor, category, true) {}

}