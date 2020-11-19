package com.launchkey.android.authenticator.demo.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.databinding.DemoFragmentSecurityInfoBinding;
import com.launchkey.android.authenticator.demo.ui.fragment.security.SecurityFactor;
import com.launchkey.android.authenticator.demo.ui.fragment.security.SecurityService;

import java.util.List;

public class SecurityInfoFragment extends BaseDemoFragment<DemoFragmentSecurityInfoBinding> implements SecurityService.SecurityStatusListener {
    @NonNull
    private final SecurityService securityService = SecurityService.getInstance();

    public SecurityInfoFragment() {
        super(R.layout.demo_fragment_security_info);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DemoFragmentSecurityInfoBinding.bind(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        securityService.getStatus(this);
    }

    @Override
    public void onSecurityStatusUpdate(List<SecurityFactor> list) {
        showSecurityInformation(list);
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

        binding.demoFragmentSecurityinfoText.setText(message);
    }

    private String getFactorName(SecurityFactor f) {
        switch (f.getAuthMethod()) {
            case PIN_CODE:
                return getString(R.string.demo_activity_list_feature_security_info_pin_code);
            case CIRCLE_CODE:
                return getString(R.string.demo_activity_list_feature_security_info_circle_code);
            case WEARABLES:
                return getString(R.string.demo_activity_list_feature_security_info_bluetooth_devices);
            case LOCATIONS:
                return getString(R.string.demo_activity_list_feature_security_info_geofencing);
            case BIOMETRIC:
                return getString(R.string.demo_activity_list_feature_security_info_fingerprint);
            default:
                return getString(R.string.demo_activity_list_feature_security_info_none);
        }
    }

    private String getFactorCategory(SecurityFactor f) {
        switch (f.getType()) {
            case KNOWLEDGE:
                return getString(R.string.demo_activity_list_feature_security_info_knowledge);
            case INHERENCE:
                return getString(R.string.demo_activity_list_feature_security_info_inherence);
            case POSSESSION:
                return getString(R.string.demo_activity_list_feature_security_info_possession);
            default:
                return getString(R.string.demo_activity_list_feature_security_info_none);
        }
    }
}
