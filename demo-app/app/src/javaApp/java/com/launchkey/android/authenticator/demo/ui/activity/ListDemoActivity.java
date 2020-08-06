package com.launchkey.android.authenticator.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.app.DemoApplication;
import com.launchkey.android.authenticator.demo.ui.adapter.DemoFeatureAdapter;
import com.launchkey.android.authenticator.demo.ui.fragment.CustomDevicesFragment3;
import com.launchkey.android.authenticator.demo.ui.fragment.CustomLinkingFragment;
import com.launchkey.android.authenticator.demo.ui.fragment.CustomLogoutFragment2;
import com.launchkey.android.authenticator.demo.ui.fragment.CustomSessionsFragment;
import com.launchkey.android.authenticator.demo.ui.fragment.CustomUnlinkFragment2;
import com.launchkey.android.authenticator.demo.ui.fragment.SecurityInfoFragment;
import com.launchkey.android.authenticator.sdk.AuthenticatorManager;
import com.launchkey.android.authenticator.sdk.DeviceLinkedEventCallback;
import com.launchkey.android.authenticator.sdk.DeviceUnlinkedEventCallback;
import com.launchkey.android.authenticator.sdk.device.Device;
import com.launchkey.android.authenticator.sdk.device.DeviceManager;
import com.launchkey.android.authenticator.sdk.error.BaseError;
import com.launchkey.android.authenticator.sdk.ui.fragment.DevicesFragment;
import com.launchkey.android.authenticator.sdk.ui.fragment.SessionsFragment;

import java.util.Locale;

public class ListDemoActivity extends BaseDemoActivity implements AdapterView.OnItemClickListener {

    public static final String EXTRA_SHOW_REQUEST = "extraShowRequest";

    @StringRes private static final int ERROR_DEVICE_UNLINKED = R.string.demo_generic_device_is_unlinked;
    @StringRes private static final int ERROR_DEVICE_LINKED = R.string.demo_error_device_already_linked;

    private static final int[] FEATURES_RES = new int[]{
            R.string.demo_activity_list_feature_link_default_manual,
            R.string.demo_activity_list_feature_link_default_scanner,
            R.string.demo_activity_list_feature_link_custom,
            R.string.demo_activity_list_feature_security,
            R.string.demo_activity_list_feature_security_custom,
            R.string.demo_activity_list_feature_securityinfo,
            R.string.demo_activity_list_feature_requests_xml,
            R.string.demo_activity_list_feature_logout_custom2,
            R.string.demo_activity_list_feature_unlink_custom2,
            R.string.demo_activity_list_feature_sessions_default,
            R.string.demo_activity_list_feature_sessions_custom,
            R.string.demo_activity_list_feature_devices_default,
            R.string.demo_activity_list_feature_devices_custom3,
            R.string.demo_activity_list_feature_config
    };

    private ListView mList;
    private DemoFeatureAdapter mAdapter;

    private final @NonNull DeviceLinkedEventCallback mDeviceLinkedCallback = new DeviceLinkedEventCallback() {
        @Override
        public void onEventResult(boolean successful, BaseError error, Device device) {
            updateUi();
        }
    };

    private final @NonNull DeviceUnlinkedEventCallback mDeviceUnlinkedCallback = new DeviceUnlinkedEventCallback() {
        @Override
        public void onEventResult(boolean successful, BaseError error, Object o) {
            updateUi();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_list);

        // Makes sure we only initialize the sample app once (instead of also instantiating it in
        // DemoApplication)
        String sdkKey = getIntent().getStringExtra("sdkKey");
        String action = getIntent().getAction();
        if (action != null && action.equals("android.intent.action.MAIN")) {
            ((DemoApplication) getApplication()).initialize(sdkKey);
        }

        final Toolbar toolbar = findViewById(R.id.list_toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mAdapter = new DemoFeatureAdapter(this, FEATURES_RES);

        mList = findViewById(R.id.list_listview);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(this);

        // Try processing Intent that could have extras from an FCM
        // notification if the app was in the background and running
        // on Android 8.0 (Oreo) and up. That payload is now handed
        // to this main entry Activity via Intent as extras by FCM.
        processIntent(getIntent());
    }

    private void processIntent(Intent i) {
        if (i == null) {
            return;
        }

        final Bundle extras = i.getExtras();
        if (extras == null || extras.isEmpty()) {
            return;
        }

        if (!extras.getString("jwe", "").isEmpty()) {
            AuthenticatorManager.getInstance().onPushNotification(extras);
            i.putExtra(EXTRA_SHOW_REQUEST, true);
        }
        if (i.getBooleanExtra(EXTRA_SHOW_REQUEST, false)) {
            showRequest();
        }
    }

    private void showRequest() {
        Intent authRequestActivity = new Intent(this, AuthRequestActivity.class);
        startActivity(authRequestActivity);
    }

    private void updateUi() {

        boolean assumingNotVisible = getSupportActionBar() == null;

        if (assumingNotVisible) {
            return;
        }

        boolean nowLinked = getAuthenticatorManager().isDeviceLinked();

        final String message;
        if (nowLinked) {
            Device device = DeviceManager.getInstance().getCurrentDevice();
            message = String.format(Locale.getDefault(), "\"%s\"", device.getName());
        } else {
            message = getString(ERROR_DEVICE_UNLINKED);
        }
        getSupportActionBar().setTitle(getString(R.string.demo_activity_list_title_format, message));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
        getAuthenticatorManager().registerForEvents(mDeviceLinkedCallback, mDeviceUnlinkedCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getAuthenticatorManager().unregisterForEvents(mDeviceLinkedCallback, mDeviceUnlinkedCallback);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int featureStringId = mAdapter.getItem(position);

        boolean linked = getAuthenticatorManager().isDeviceLinked();

        Class fragmentClassName = null;
        boolean goBackOnUnlink = false;

        switch (featureStringId) {
            case R.string.demo_activity_list_feature_link_default_manual:
                if (linked) {
                    showError(ERROR_DEVICE_LINKED);
                } else {
                    getAuthenticatorManager().startLinkingActivity(this, AuthenticatorManager.LINKING_METHOD_MANUAL);
                }

                break;
            case R.string.demo_activity_list_feature_link_default_scanner:
                if (linked) {
                    showError(ERROR_DEVICE_LINKED);
                } else {
                    getAuthenticatorManager().startLinkingActivity(this, AuthenticatorManager.LINKING_METHOD_SCAN);
                }

                break;
            case R.string.demo_activity_list_feature_link_custom:
                if (linked) {
                    showError(ERROR_DEVICE_LINKED);
                } else {
                    fragmentClassName = CustomLinkingFragment.class;
                }

                break;
            case R.string.demo_activity_list_feature_security:
                getAuthenticatorManager().startSecurityActivity(this);
                break;
            case R.string.demo_activity_list_feature_security_custom:
                Intent customSecurityActivity = new Intent(this, CustomSecurityActivity.class);
                startActivity(customSecurityActivity);
                break;
            case R.string.demo_activity_list_feature_securityinfo:
                fragmentClassName = SecurityInfoFragment.class;
                break;
            case R.string.demo_activity_list_feature_requests_xml:
                if (!linked) {
                    showError(ERROR_DEVICE_UNLINKED);
                } else {
                    Intent authRequestActivity = new Intent(this, AuthRequestActivity.class);
                    startActivity(authRequestActivity);
                }

                break;
            case R.string.demo_activity_list_feature_logout_custom2:
                if (!linked) {
                    showError(ERROR_DEVICE_UNLINKED);
                } else {
                    fragmentClassName = CustomLogoutFragment2.class;
                    goBackOnUnlink = true;
                }

                break;
            case R.string.demo_activity_list_feature_unlink_custom2:
                if (!linked) {
                    showError(ERROR_DEVICE_UNLINKED);
                } else {
                    fragmentClassName = CustomUnlinkFragment2.class;
                    goBackOnUnlink = true;
                }

                break;
            case R.string.demo_activity_list_feature_sessions_default:
                if (!linked) {
                    showError(ERROR_DEVICE_UNLINKED);
                } else {
                    fragmentClassName = SessionsFragment.class;
                    goBackOnUnlink = true;
                }

                break;
            case R.string.demo_activity_list_feature_sessions_custom:
                if (!linked) {
                    showError(ERROR_DEVICE_UNLINKED);
                } else {
                    fragmentClassName = CustomSessionsFragment.class;
                    goBackOnUnlink = true;
                }

                break;
            case R.string.demo_activity_list_feature_devices_default:
                if (!linked) {
                    showError(ERROR_DEVICE_UNLINKED);
                } else {
                    fragmentClassName = DevicesFragment.class;
                    goBackOnUnlink = true;
                }

                break;
            case R.string.demo_activity_list_feature_devices_custom3:
                if (!linked) {
                    showError(ERROR_DEVICE_UNLINKED);
                } else {
                    fragmentClassName = CustomDevicesFragment3.class;
                    goBackOnUnlink = true;
                }

                break;
            case R.string.demo_activity_list_feature_config:
                Intent appConfigsActivity = new Intent(this, AppConfigsActivity.class);
                startActivity(appConfigsActivity);
                break;
        }

        if (fragmentClassName != null) {

            //The full class name of a Fragment will be passed to the activity
            // so it's automatically instantiated by name and placed in a container.
            Intent fragmentActivity = new Intent(this, GenericFragmentDemoActivity.class);
            fragmentActivity.putExtra(GenericFragmentDemoActivity.EXTRA_FRAGMENT_CLASS, fragmentClassName.getCanonicalName());
            fragmentActivity.putExtra(GenericFragmentDemoActivity.EXTRA_TITLE, getString(featureStringId));
            fragmentActivity.putExtra(GenericFragmentDemoActivity.EXTRA_GO_BACK_ON_UNLINK, goBackOnUnlink);

            startActivity(fragmentActivity);
        }
    }

    private void showError(int messageRes) {
        showError(getString(messageRes));
    }

    private void showError(String message) {
        showMessage(getString(R.string.demo_generic_error, message));
    }

    private void showMessage(String message) {
        Snackbar.make(mList, message, Snackbar.LENGTH_LONG).show();
    }
}