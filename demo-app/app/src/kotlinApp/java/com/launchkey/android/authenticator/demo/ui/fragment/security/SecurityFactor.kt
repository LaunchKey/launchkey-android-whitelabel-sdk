/*
 *  Copyright (c) 2016. LaunchKey, Inc. All rights reserved.
 */
package com.launchkey.android.authenticator.demo.ui.fragment.security

import com.launchkey.android.authenticator.sdk.core.auth_method_management.AuthMethod
import com.launchkey.android.authenticator.sdk.core.auth_method_management.AuthMethodType

/**
 * Interface that represents the current state of a
 * set security factor returned by
 * [SecurityService.getStatus].
 *
 * @see .getAuthMethod
 */
interface SecurityFactor {
    /**
     * @return The name of the factor.
     * Check [SecurityService] for
     * the FACTOR_* public constant
     * flags.
     */
    val authMethod: AuthMethod?

    /**
     * @return The category of the factor.
     * Check [SecurityService] for
     * the CATEGORY_* public constant
     * flags.
     */
    val type: AuthMethodType?

    /**
     * @return true if this factor is already active and will
     * taken into account during an Auth Request if necessary.
     * False when the factor is set but it has not become
     * active yet; this will apply to passive factors that
     * might have
     */
    val isActive: Boolean
}