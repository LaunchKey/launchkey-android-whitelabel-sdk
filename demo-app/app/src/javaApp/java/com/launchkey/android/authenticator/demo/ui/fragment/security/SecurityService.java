/*
 *  Copyright (c) 2016. LaunchKey, Inc. All rights reserved.
 */

package com.launchkey.android.authenticator.demo.ui.fragment.security;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.launchkey.android.authenticator.sdk.core.auth_method_management.AuthMethod;
import com.launchkey.android.authenticator.sdk.core.auth_method_management.AuthMethodManagerFactory;
import com.launchkey.android.authenticator.sdk.core.auth_method_management.AuthMethodType;
import com.launchkey.android.authenticator.sdk.core.auth_method_management.LocationsManager;
import com.launchkey.android.authenticator.sdk.core.auth_method_management.WearablesManager;
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class that allows White Label
 * implementers to get current
 * information on the security
 * aspect of the White Label
 * SDK.
 */
public class SecurityService {
    private static volatile SecurityService mInstance = new SecurityService();
    private final @NonNull Map<SecurityStatusListener, Integer> statusListenerIntegerMap = new ConcurrentHashMap<>();

    /**
     * @return the single instance.
     */
    public static SecurityService getInstance() {
        return mInstance;
    }

    private SecurityService() { }

    private void getUpdatedStatus(final @NonNull SecurityStatusListener listener) {
        boolean pinEnabled = AuthenticatorManager.instance.getConfig().isMethodAllowed(AuthMethod.PIN_CODE) && AuthMethodManagerFactory.getPINCodeManager().isPINCodeSet();
        boolean circleEnabled = AuthenticatorManager.instance.getConfig().isMethodAllowed(AuthMethod.CIRCLE_CODE) && AuthMethodManagerFactory.getCircleCodeManager().isCircleCodeSet();
        boolean fingerprintEnabled = AuthenticatorManager.instance.getConfig().isMethodAllowed(AuthMethod.BIOMETRIC) && AuthMethodManagerFactory.getBiometricManager().isBiometricSet();

        final List<SecurityFactor> list = new ArrayList<>();

        if (pinEnabled) {
            list.add(new SecurityFactorImpl(AuthMethod.PIN_CODE, AuthMethodType.KNOWLEDGE));
        }

        if (circleEnabled) {
            list.add(new SecurityFactorImpl(AuthMethod.CIRCLE_CODE, AuthMethodType.KNOWLEDGE));
        }

        if (fingerprintEnabled) {
            list.add(new SecurityFactorImpl(AuthMethod.BIOMETRIC, AuthMethodType.INHERENCE));
        }

        statusListenerIntegerMap.put(listener, 0);
        if (AuthenticatorManager.instance.getConfig().isMethodAllowed(AuthMethod.LOCATIONS)) {
            AuthMethodManagerFactory.getLocationsManager().getStoredLocations(new LocationsManager.GetStoredLocationsCallback() {
                @Override
                public void onGetSuccess(final @NonNull List<LocationsManager.StoredLocation> locations) {
                    boolean locationsEnabled = !locations.isEmpty();
                    if (locationsEnabled) {
                        boolean locationsActive = false;
                        for (final LocationsManager.StoredLocation location : locations) {
                            if (location.isActive()) {
                                locationsActive = true;
                                break;
                            }
                        }

                        list.add(new SecurityFactorImpl(AuthMethod.LOCATIONS, AuthMethodType.INHERENCE, locationsActive));
                    }
                    final int i = statusListenerIntegerMap.get(listener);
                    statusListenerIntegerMap.put(listener, i + 1);
                    notifyListenerIfDone(listener, list);
                }

                @Override
                public void onGetFailure(final @NonNull Exception e) {
                    final int i = statusListenerIntegerMap.get(listener);
                    statusListenerIntegerMap.put(listener, i + 1);
                    notifyListenerIfDone(listener, list);
                }
            });
        } else {
            notifyListenerIfDone(listener, list);
        }

        if (AuthenticatorManager.instance.getConfig().isMethodAllowed(AuthMethod.LOCATIONS)) {
            AuthMethodManagerFactory.getWearablesManager().getStoredWearables(new WearablesManager.GetStoredWearablesCallback() {
                @Override
                public void onGetSuccess(final @NonNull List<WearablesManager.Wearable> wearablesFactor) {
                    boolean wearablesEnabled = !wearablesFactor.isEmpty();
                    if (wearablesEnabled) {
                        boolean wearablesActive = false;
                        for (final WearablesManager.Wearable wearableFactor : wearablesFactor) {
                            if (wearableFactor.isActive()) {
                                wearablesActive = true;
                                break;
                            }
                        }

                        list.add(new SecurityFactorImpl(AuthMethod.WEARABLES, AuthMethodType.POSSESSION, wearablesActive));
                    }
                    final int i = statusListenerIntegerMap.get(listener);
                    statusListenerIntegerMap.put(listener, i + 1);
                    notifyListenerIfDone(listener, list);
                }

                @Override
                public void onGetFailure(final @NonNull Exception exception) {
                    final int i = statusListenerIntegerMap.get(listener);
                    statusListenerIntegerMap.put(listener, i + 1);
                    notifyListenerIfDone(listener, list);
                }
            });
        } else {
            notifyListenerIfDone(listener, list);
        }
    }

    public void getStatus(final @Nullable SecurityStatusListener listener) {
        if (listener == null || statusListenerIntegerMap.containsKey(listener)) {
            return;
        }

        getStatusAsyncInternal(listener);
    }

    private void getStatusAsyncInternal(final SecurityStatusListener l) {
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                getUpdatedStatus(l);
            }
        };

        final Thread t = new Thread(r);
        t.start();
    }

    private void notifyListenerIfDone(final @NonNull SecurityStatusListener securityStatusListener, final @NonNull List<SecurityFactor> securityFactors) {
        if (statusListenerIntegerMap.get(securityStatusListener) == 2) {
            statusListenerIntegerMap.remove(securityStatusListener);
            final Handler uiHandler = new Handler(Looper.getMainLooper());
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    securityStatusListener.onSecurityStatusUpdate(securityFactors);
                }
            });
        }
    }

    public interface SecurityStatusListener {
        void onSecurityStatusUpdate(@NonNull final List<SecurityFactor> list);
    }
}
