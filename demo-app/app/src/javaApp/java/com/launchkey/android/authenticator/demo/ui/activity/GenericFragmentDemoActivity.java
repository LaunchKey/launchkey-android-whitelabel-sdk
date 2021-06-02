package com.launchkey.android.authenticator.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.databinding.DemoActivityFragmentBinding;
import com.launchkey.android.authenticator.demo.ui.fragment.CustomLinkingFragment;
import com.launchkey.android.authenticator.sdk.core.authentication_management.Device;
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.UnlinkDeviceEventCallback;
import com.launchkey.android.authenticator.sdk.core.exception.DeviceUnlinkedButFailedToNotifyServerException;

public class GenericFragmentDemoActivity extends BaseDemoActivity<DemoActivityFragmentBinding> {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_FRAGMENT_CLASS = "fragment_class";
    public static final String EXTRA_GO_BACK_ON_UNLINK = "go_back_on_unlink";

    public GenericFragmentDemoActivity() {
        super(R.layout.demo_activity_fragment);
    }

    private boolean goBackOnUnlink;
    private final @NonNull UnlinkDeviceEventCallback unlinkDeviceEventCallback = new UnlinkDeviceEventCallback() {
        @Override
        public void onSuccess(final @NonNull Device device) {
            if (device.isCurrent() && goBackOnUnlink) {
                finish();
            }
        }

        @Override
        public void onFailure(final @NonNull Exception e) {
            if (e instanceof DeviceUnlinkedButFailedToNotifyServerException && goBackOnUnlink) {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DemoActivityFragmentBinding.bind(findViewById(R.id.demo_activity_fragment_root));

        setSupportActionBar(binding.demoActivityFragmentToolbar);

        Intent i = getIntent();
        Bundle extras = i == null ? null : i.getExtras();
        if (getSupportActionBar() != null) {
            String title = extras == null ? null : extras.getString(EXTRA_TITLE);
            getSupportActionBar().setTitle(title == null ? "Demo" : title);
        }

        if (savedInstanceState != null) {
            return;
        }

        String fragmentClass = extras == null ? null : extras.getString(EXTRA_FRAGMENT_CLASS);

        if (fragmentClass == null) {
            finish();
            return;
        }

        goBackOnUnlink = extras.getBoolean(EXTRA_GO_BACK_ON_UNLINK);

        //Instantiate the Fragment by name that was passed via extra (Bundle) and if not null,
        // then place it in the container.

        Fragment f;

        try {
            f = getSupportFragmentManager().getFragmentFactory().instantiate(getClassLoader(), fragmentClass);
            f.setArguments(extras);
        } catch (Exception e) {
            finish();
            return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.demo_activity_fragment_container, f)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAuthenticatorManager().registerForEvents(unlinkDeviceEventCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        getAuthenticatorManager().unregisterForEvents(unlinkDeviceEventCallback);
    }
}
