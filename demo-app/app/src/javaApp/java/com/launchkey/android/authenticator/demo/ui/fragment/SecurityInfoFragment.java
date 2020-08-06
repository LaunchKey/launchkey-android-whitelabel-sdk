package com.launchkey.android.authenticator.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.util.Utils;
import com.launchkey.android.authenticator.sdk.error.BaseError;
import com.launchkey.android.authenticator.sdk.security.SecurityFactor;
import com.launchkey.android.authenticator.sdk.security.SecurityService;

import java.util.List;

public class SecurityInfoFragment extends BaseDemoFragment implements SecurityService.SecurityStatusListener {

    private TextView textView;
    private SecurityService securityService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.demo_fragment_security_info, container, false);
        return postInflationSetup(root);
    }

    private View postInflationSetup(View root) {
        textView = root.findViewById(R.id.demo_fragment_securityinfo_text);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        securityService = SecurityService.getInstance(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        securityService.getStatus(this);
    }

    @Override
    public void onSecurityStatusUpdate(boolean success, List<SecurityFactor> list, BaseError baseError) {
        if (success) {
            showSecurityInformation(list);
        } else {
            Utils.simpleSnackbar(textView, getString(R.string.demo_generic_error, baseError.getMessage()));
        }
    }

    private void showSecurityInformation(List<SecurityFactor> list) {

        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (SecurityFactor f : list) {

            sb.append("\n");

            if (f != null) {
                sb.append(getString(R.string.demo_activity_list_feature_security_info_line,
                        getFactorName(f), getFactorCategory(f),
                        f.isActive() ? getString(R.string.demo_generic_true) : getString(R.string.demo_generic_false)));
            } else {
                sb.append(getString(R.string.demo_activity_list_feature_security_info_null_factor));
            }
        }

        String message = sb.toString();

        if (message.trim().isEmpty()) {
            message = getString(R.string.demo_activity_list_feature_security_info_no_message);
        }

        textView.setText(message);
    }

    private String getFactorName(SecurityFactor f) {
        switch (f.getFactor()) {
            case SecurityService.FACTOR_PIN:
                return getString(R.string.demo_activity_list_feature_security_info_pin_code);
            case SecurityService.FACTOR_CIRCLE:
                return getString(R.string.demo_activity_list_feature_security_info_circle_code);
            case SecurityService.FACTOR_PROXIMITY:
                return getString(R.string.demo_activity_list_feature_security_info_bluetooth_devices);
            case SecurityService.FACTOR_GEOFENCING:
                return getString(R.string.demo_activity_list_feature_security_info_geofencing);
            case SecurityService.FACTOR_FINGERPRINT:
                return getString(R.string.demo_activity_list_feature_security_info_fingerprint);
            default:
                return getString(R.string.demo_activity_list_feature_security_info_none);
        }
    }

    private String getFactorCategory(SecurityFactor f) {
        switch (f.getCategory()) {
            case SecurityService.CATEGORY_KNOWLEDGE:
                return getString(R.string.demo_activity_list_feature_security_info_knowledge);
            case SecurityService.CATEGORY_INHERENCE:
                return getString(R.string.demo_activity_list_feature_security_info_inherence);
            case SecurityService.CATEGORY_POSSESSION:
                return getString(R.string.demo_activity_list_feature_security_info_possession);
            default:
                return getString(R.string.demo_activity_list_feature_security_info_none);
        }
    }
}
