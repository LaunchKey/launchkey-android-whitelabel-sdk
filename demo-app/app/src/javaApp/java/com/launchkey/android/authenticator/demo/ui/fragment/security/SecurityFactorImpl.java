/*
 *  Copyright (c) 2016. LaunchKey, Inc. All rights reserved.
 */

package com.launchkey.android.authenticator.demo.ui.fragment.security;

import com.launchkey.android.authenticator.sdk.core.auth_method_management.AuthMethod;
import com.launchkey.android.authenticator.sdk.core.auth_method_management.AuthMethodType;

class SecurityFactorImpl implements SecurityFactor {

    private final AuthMethod factor;
    private final AuthMethodType category;
    private final boolean active;

    SecurityFactorImpl(AuthMethod factor, AuthMethodType category) {
        this(factor, category, true);
    }

    SecurityFactorImpl(AuthMethod factor, AuthMethodType cat, boolean active) {
        this.factor = factor;
        category = cat;
        this.active = active;
    }

    @Override
    public AuthMethod getAuthMethod() {
        return factor;
    }

    @Override
    public AuthMethodType getType() {
        return category;
    }

    @Override
    public boolean isActive() {
        return active;
    }

}
