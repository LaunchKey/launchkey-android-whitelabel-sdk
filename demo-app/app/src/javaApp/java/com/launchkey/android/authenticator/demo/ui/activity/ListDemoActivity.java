package com.launchkey.android.authenticator.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.databinding.DemoActivityListBinding;
import com.launchkey.android.authenticator.demo.ui.adapter.DemoFeatureAdapter;
import com.launchkey.android.authenticator.demo.ui.fragment.AppConfigsFragment;
import com.launchkey.android.authenticator.demo.ui.fragment.CustomDevicesFragment;
import com.launchkey.android.authenticator.demo.ui.fragment.CustomLinkingFragment;
import com.launchkey.android.authenticator.demo.ui.fragment.CustomLogoutFragment;
import com.launchkey.android.authenticator.demo.ui.fragment.CustomSessionsFragment;
import com.launchkey.android.authenticator.demo.ui.fragment.CustomUnlinkFragment;
import com.launchkey.android.authenticator.demo.ui.fragment.SecurityInfoFragment;
import com.launchkey.android.authenticator.sdk.core.authentication_management.Device;
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.DeviceLinkedEventCallback;
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.UnlinkDeviceEventCallback;
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIManager;
import com.launchkey.android.authenticator.sdk.ui.fragment.DevicesFragment;
import com.launchkey.android.authenticator.sdk.ui.fragment.SessionsFragment;

import java.util.Locale;

public class ListDemoActivity extends BaseDemoActivity<DemoActivityListBinding> implements AdapterView.OnItemClickListener {

    public static final String EXTRA_SDK_KEY = "extraSdkKey";
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
            R.string.demo_activity_list_feature_logout_custom,
            R.string.demo_activity_list_feature_unlink_custom,
            R.string.demo_activity_list_feature_sessions_default,
            R.string.demo_activity_list_feature_sessions_custom,
            R.string.demo_activity_list_feature_devices_default,
            R.string.demo_activity_list_feature_devices_custom,
            R.string.demo_activity_list_feature_config
    };

    private DemoFeatureAdapter mAdapter;
    private String sdkKey = null;
    private final @NonNull DeviceLinkedEventCallback mDeviceLinkedCallback = new DeviceLinkedEventCallback() {
        @Override
        public void onSuccess(@NonNull final Device device) {
            updateUi();
        }

        @Override
        public void onFailure(final @NonNull Exception e) {
            updateUi();
        }
    };

    private final @NonNull UnlinkDeviceEventCallback mDeviceUnlinkedCallback = new UnlinkDeviceEventCallback() {
        @Override
        public void onSuccess(final @NonNull Device device) {
            updateUi();
        }

        @Override
        public void onFailure(final @NonNull Exception e) {
            updateUi();
        }
    };

    public ListDemoActivity() {
        super(R.layout.demo_activity_list);
    }

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(@Nullable final ActivityResult result) {
            sdkKey = result == null ? null : result.getData().getStringExtra(ListDemoActivity.EXTRA_SDK_KEY);
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DemoActivityListBinding.bind(findViewById(R.id.demo_activity_list_root));

        sdkKey = getIntent().getStringExtra(ListDemoActivity.EXTRA_SDK_KEY);
        if (sdkKey == null) {
            sdkKey = getString(R.string.authenticator_sdk_key);
        }

        setSupportActionBar(binding.listToolbar);

        mAdapter = new DemoFeatureAdapter(this, FEATURES_RES);

        binding.listListview.setAdapter(mAdapter);
        binding.listListview.setOnItemClickListener(this);

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
            getAuthenticatorManager().handlePushPayload(extras);
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
            Device device = getAuthenticatorManager().instance.getCurrentDevice();
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

        Class<? extends Fragment> fragmentClassName = null;
        boolean goBackOnUnlink = false;
        boolean forResult = false;
        switch (featureStringId) {
            case R.string.demo_activity_list_feature_link_default_manual:
                if (linked) {
                    showError(ERROR_DEVICE_LINKED);
                } else {
                    getAuthenticatorUIManager().startLinkingActivity(this, AuthenticatorUIManager.LINKING_METHOD_MANUAL, sdkKey);
                }

                break;
            case R.string.demo_activity_list_feature_link_default_scanner:
                if (linked) {
                    showError(ERROR_DEVICE_LINKED);
                } else {
                    getAuthenticatorUIManager().startLinkingActivity(this, AuthenticatorUIManager.LINKING_METHOD_SCAN, sdkKey);
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
                getAuthenticatorUIManager().startSecurityActivity(this);
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
            case R.string.demo_activity_list_feature_logout_custom:
                if (!linked) {
                    showError(ERROR_DEVICE_UNLINKED);
                } else {
                    fragmentClassName = CustomLogoutFragment.class;
                    goBackOnUnlink = true;
                }

                break;
            case R.string.demo_activity_list_feature_unlink_custom:
                if (!linked) {
                    showError(ERROR_DEVICE_UNLINKED);
                } else {
                    fragmentClassName = CustomUnlinkFragment.class;
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
            case R.string.demo_activity_list_feature_devices_custom:
                if (!linked) {
                    showError(ERROR_DEVICE_UNLINKED);
                } else {
                    fragmentClassName = CustomDevicesFragment.class;
                    goBackOnUnlink = true;
                }

                break;
            case R.string.demo_activity_list_feature_config:
                fragmentClassName = AppConfigsFragment.class;
                forResult = true;
                break;
        }

        if (fragmentClassName != null) {
            Intent fragmentActivity = new Intent(this, GenericFragmentDemoActivity.class);
            fragmentActivity.putExtra(ListDemoActivity.EXTRA_SDK_KEY, sdkKey);
            fragmentActivity.putExtra(GenericFragmentDemoActivity.EXTRA_FRAGMENT_CLASS, fragmentClassName.getCanonicalName());
            fragmentActivity.putExtra(GenericFragmentDemoActivity.EXTRA_TITLE, getString(featureStringId));
            fragmentActivity.putExtra(GenericFragmentDemoActivity.EXTRA_GO_BACK_ON_UNLINK, goBackOnUnlink);

            startGenericFragmentActivity(fragmentClassName, featureStringId, goBackOnUnlink, forResult);
        }
    }

    private void startGenericFragmentActivity(final Class<? extends Fragment> fragmentClassName,
                                              @StringRes final int featureStringId,
                                              final boolean goBackOnUnlink,
                                              final boolean forResult) {
        //The full class name of a Fragment will be passed to the activity
        // so it's automatically instantiated by name and placed in a container.
        Intent i = new Intent(this, GenericFragmentDemoActivity.class);
        i.putExtra(ListDemoActivity.EXTRA_SDK_KEY, sdkKey);
        i.putExtra(GenericFragmentDemoActivity.EXTRA_FRAGMENT_CLASS, fragmentClassName.getCanonicalName());
        i.putExtra(GenericFragmentDemoActivity.EXTRA_TITLE, getString(featureStringId));
        i.putExtra(GenericFragmentDemoActivity.EXTRA_GO_BACK_ON_UNLINK, goBackOnUnlink);
        if (forResult) {
            launcher.launch(i);
        } else {
            startActivity(i);
        }
    }

    private void showError(int messageRes) {
        showError(getString(messageRes));
    }

    private void showError(String message) {
        showMessage(getString(R.string.demo_generic_error, message));
    }

    private void showMessage(String message) {
        Snackbar.make(binding.listListview, message, Snackbar.LENGTH_LONG).show();
    }
}