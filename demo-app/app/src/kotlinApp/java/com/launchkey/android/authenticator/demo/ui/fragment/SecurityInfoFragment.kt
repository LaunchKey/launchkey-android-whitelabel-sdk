package com.launchkey.android.authenticator.demo.ui.fragment

import android.os.Bundle
import android.view.View
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.databinding.DemoFragmentSecurityInfoBinding
import com.launchkey.android.authenticator.demo.ui.fragment.security.SecurityFactor
import com.launchkey.android.authenticator.demo.ui.fragment.security.SecurityService
import com.launchkey.android.authenticator.demo.ui.fragment.security.SecurityService.SecurityStatusListener
import com.launchkey.android.authenticator.sdk.core.auth_method_management.AuthMethod
import com.launchkey.android.authenticator.sdk.core.auth_method_management.AuthMethodType

class SecurityInfoFragment : BaseDemoFragment<DemoFragmentSecurityInfoBinding>(R.layout.demo_fragment_security_info), SecurityStatusListener {
    private val securityService: SecurityService = SecurityService.instance
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DemoFragmentSecurityInfoBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        securityService.getStatus(this)
    }

    override fun onSecurityStatusUpdate(list: List<SecurityFactor>) {
        showSecurityInformation(list)
    }

    private fun showSecurityInformation(list: List<SecurityFactor>) {
        if (activity == null || activity!!.isFinishing) {
            return
        }
        val sb = StringBuilder()
        for (f in list) {
            sb.append("\n")
            if (f != null) {
                sb.append(getString(R.string.demo_activity_list_feature_security_info_line,
                        getFactorName(f), getFactorCategory(f),
                        if (f.isActive) getString(R.string.demo_generic_true) else getString(R.string.demo_generic_false)))
            } else {
                sb.append(getString(R.string.demo_activity_list_feature_security_info_null_factor))
            }
        }
        var message = sb.toString()
        if (message.trim { it <= ' ' }.isEmpty()) {
            message = getString(R.string.demo_activity_list_feature_security_info_no_message)
        }
        binding!!.demoFragmentSecurityinfoText.text = message
    }

    private fun getFactorName(f: SecurityFactor): String {
        return when (f.authMethod) {
            AuthMethod.PIN_CODE -> getString(R.string.demo_activity_list_feature_security_info_pin_code)
            AuthMethod.CIRCLE_CODE -> getString(R.string.demo_activity_list_feature_security_info_circle_code)
            AuthMethod.WEARABLES -> getString(R.string.demo_activity_list_feature_security_info_bluetooth_devices)
            AuthMethod.LOCATIONS -> getString(R.string.demo_activity_list_feature_security_info_geofencing)
            AuthMethod.BIOMETRIC -> getString(R.string.demo_activity_list_feature_security_info_fingerprint)
            else -> getString(R.string.demo_activity_list_feature_security_info_none)
        }
    }

    private fun getFactorCategory(f: SecurityFactor): String {
        return when (f.type) {
            AuthMethodType.KNOWLEDGE -> getString(R.string.demo_activity_list_feature_security_info_knowledge)
            AuthMethodType.INHERENCE -> getString(R.string.demo_activity_list_feature_security_info_inherence)
            AuthMethodType.POSSESSION -> getString(R.string.demo_activity_list_feature_security_info_possession)
            else -> getString(R.string.demo_activity_list_feature_security_info_none)
        }
    }
}