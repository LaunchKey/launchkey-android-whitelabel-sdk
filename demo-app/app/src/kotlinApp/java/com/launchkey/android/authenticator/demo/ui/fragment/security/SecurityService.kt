/*
 *  Copyright (c) 2016. LaunchKey, Inc. All rights reserved.
 */
package com.launchkey.android.authenticator.demo.ui.fragment.security

import android.os.Handler
import android.os.Looper
import com.launchkey.android.authenticator.sdk.core.auth_method_management.*
import com.launchkey.android.authenticator.sdk.core.auth_method_management.LocationsManager.GetStoredLocationsCallback
import com.launchkey.android.authenticator.sdk.core.auth_method_management.WearablesManager.GetStoredWearablesCallback
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Class that allows White Label
 * implementers to get current
 * information on the security
 * aspect of the White Label
 * SDK.
 */
class SecurityService private constructor() {
    private val statusListenerIntegerMap: MutableMap<SecurityStatusListener, Int> = ConcurrentHashMap()
    private fun getUpdatedStatus(listener: SecurityStatusListener) {
        val pinEnabled = AuthenticatorManager.instance.config.isMethodAllowed(AuthMethod.PIN_CODE) && AuthMethodManagerFactory.getPINCodeManager().isPINCodeSet
        val circleEnabled = AuthenticatorManager.instance.config.isMethodAllowed(AuthMethod.CIRCLE_CODE) && AuthMethodManagerFactory.getCircleCodeManager().isCircleCodeSet
        val fingerprintEnabled = AuthenticatorManager.instance.config.isMethodAllowed(AuthMethod.BIOMETRIC) && AuthMethodManagerFactory.getBiometricManager().isBiometricSet
        val list: MutableList<SecurityFactor> = ArrayList()
        if (pinEnabled) {
            list.add(SecurityFactorImpl(AuthMethod.PIN_CODE, AuthMethodType.KNOWLEDGE))
        }
        if (circleEnabled) {
            list.add(SecurityFactorImpl(AuthMethod.CIRCLE_CODE, AuthMethodType.KNOWLEDGE))
        }
        if (fingerprintEnabled) {
            list.add(SecurityFactorImpl(AuthMethod.BIOMETRIC, AuthMethodType.INHERENCE))
        }
        statusListenerIntegerMap[listener] = 0
        if (AuthenticatorManager.instance.config.isMethodAllowed(AuthMethod.LOCATIONS)) {
            AuthMethodManagerFactory.getLocationsManager().getStoredLocations(object : GetStoredLocationsCallback {
                override fun onGetSuccess(locations: List<LocationsManager.StoredLocation>) {
                    val locationsEnabled = !locations.isEmpty()
                    if (locationsEnabled) {
                        var locationsActive = false
                        for (location in locations) {
                            if (location.isActive) {
                                locationsActive = true
                                break
                            }
                        }
                        list.add(SecurityFactorImpl(AuthMethod.LOCATIONS, AuthMethodType.INHERENCE, locationsActive))
                    }
                    val i = statusListenerIntegerMap[listener]!!
                    statusListenerIntegerMap[listener] = i + 1
                    notifyListenerIfDone(listener, list)
                }

                override fun onGetFailure(e: Exception) {
                    val i = statusListenerIntegerMap[listener]!!
                    statusListenerIntegerMap[listener] = i + 1
                    notifyListenerIfDone(listener, list)
                }
            })
        } else {
            notifyListenerIfDone(listener, list)
        }
        if (AuthenticatorManager.instance.config.isMethodAllowed(AuthMethod.LOCATIONS)) {
            AuthMethodManagerFactory.getWearablesManager().getStoredWearables(object : GetStoredWearablesCallback {
                override fun onGetSuccess(wearablesFactor: List<WearablesManager.Wearable>) {
                    val wearablesEnabled = !wearablesFactor.isEmpty()
                    if (wearablesEnabled) {
                        var wearablesActive = false
                        for (wearableFactor in wearablesFactor) {
                            if (wearableFactor.isActive) {
                                wearablesActive = true
                                break
                            }
                        }
                        list.add(SecurityFactorImpl(AuthMethod.WEARABLES, AuthMethodType.POSSESSION, wearablesActive))
                    }
                    val i = statusListenerIntegerMap[listener]!!
                    statusListenerIntegerMap[listener] = i + 1
                    notifyListenerIfDone(listener, list)
                }

                override fun onGetFailure(exception: Exception) {
                    val i = statusListenerIntegerMap[listener]!!
                    statusListenerIntegerMap[listener] = i + 1
                    notifyListenerIfDone(listener, list)
                }
            })
        } else {
            notifyListenerIfDone(listener, list)
        }
    }

    fun getStatus(listener: SecurityStatusListener?) {
        if (listener == null || statusListenerIntegerMap.containsKey(listener)) {
            return
        }
        getStatusAsyncInternal(listener)
    }

    private fun getStatusAsyncInternal(l: SecurityStatusListener) {
        val r = Runnable { getUpdatedStatus(l) }
        val t = Thread(r)
        t.start()
    }

    private fun notifyListenerIfDone(securityStatusListener: SecurityStatusListener, securityFactors: List<SecurityFactor>) {
        if (statusListenerIntegerMap[securityStatusListener] == 2) {
            statusListenerIntegerMap.remove(securityStatusListener)
            val uiHandler = Handler(Looper.getMainLooper())
            uiHandler.post { securityStatusListener.onSecurityStatusUpdate(securityFactors) }
        }
    }

    interface SecurityStatusListener {
        fun onSecurityStatusUpdate(list: List<SecurityFactor>)
    }

    companion object {
        /**
         * @return the single instance.
         */
        val instance = SecurityService()

    }
}