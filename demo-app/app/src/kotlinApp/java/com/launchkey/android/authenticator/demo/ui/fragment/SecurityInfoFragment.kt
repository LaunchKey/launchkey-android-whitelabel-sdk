package com.launchkey.android.authenticator.demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.util.Utils
import com.launchkey.android.authenticator.sdk.error.BaseError
import com.launchkey.android.authenticator.sdk.security.SecurityFactor
import com.launchkey.android.authenticator.sdk.security.SecurityService

class SecurityInfoFragment : BaseDemoFragment(), SecurityService.SecurityStatusListener {

    private var mText: TextView? = null
    private var mSecService: SecurityService? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.demo_fragment_security_info, container, false)
        return postInflationSetup(root)
    }

    private fun postInflationSetup(root: View): View {
        mText = root.findViewById<View>(R.id.demo_fragment_securityinfo_text) as TextView
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSecService = SecurityService.getInstance(activity)
    }

    override fun onResume() {
        super.onResume()
        mSecService!!.getStatus(this)
    }

    override fun onSecurityStatusUpdate(success: Boolean, list: List<SecurityFactor>, error: BaseError?) {
        if (success) {
            showSecurityInformation(list)
        } else {
            Utils.simpleSnackbar(mText!!, getString(R.string.demo_generic_error, error!!.message))
        }
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

        mText!!.text = message
    }

    private fun getFactorName(f: SecurityFactor): String {
        when (f.factor) {
            SecurityService.FACTOR_PIN -> return getString(R.string.demo_activity_list_feature_security_info_pin_code)
            SecurityService.FACTOR_CIRCLE -> return getString(R.string.demo_activity_list_feature_security_info_circle_code)
            SecurityService.FACTOR_PROXIMITY -> return getString(R.string.demo_activity_list_feature_security_info_bluetooth_devices)
            SecurityService.FACTOR_GEOFENCING -> return getString(R.string.demo_activity_list_feature_security_info_geofencing)
            SecurityService.FACTOR_FINGERPRINT -> return getString(R.string.demo_activity_list_feature_security_info_fingerprint)
            else -> return getString(R.string.demo_activity_list_feature_security_info_none)
        }
    }

    private fun getFactorCategory(f: SecurityFactor): String {
        when (f.category) {
            SecurityService.CATEGORY_KNOWLEDGE -> return getString(R.string.demo_activity_list_feature_security_info_knowledge)
            SecurityService.CATEGORY_INHERENCE -> return getString(R.string.demo_activity_list_feature_security_info_inherence)
            SecurityService.CATEGORY_POSSESSION -> return getString(R.string.demo_activity_list_feature_security_info_possession)
            else -> return getString(R.string.demo_activity_list_feature_security_info_none)
        }
    }
}
