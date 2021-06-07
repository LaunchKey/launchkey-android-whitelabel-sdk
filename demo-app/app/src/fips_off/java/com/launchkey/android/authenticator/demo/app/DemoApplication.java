package com.launchkey.android.authenticator.demo.app;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.ui.activity.AuthRequestActivity;
import com.launchkey.android.authenticator.sdk.core.auth_request_management.AuthRequestManager;
import com.launchkey.android.authenticator.sdk.core.auth_request_management.event_callback.AuthRequestPushReceivedEvent;
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorConfig;
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager;
import com.launchkey.android.authenticator.sdk.core.authentication_management.Device;
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.DeviceLinkedEventCallback;
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.UnlinkDeviceEventCallback;
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIConfig;
import com.launchkey.android.authenticator.sdk.ui.AuthenticatorUIManager;
import com.launchkey.android.authenticator.sdk.ui.theme.AuthenticatorTheme;

import java.util.Locale;

public class DemoApplication extends Application {

    public static final String TAG = DemoApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        AuthenticatorTheme customTheme = new AuthenticatorTheme.Builder(this)
                .appBar(Color.DKGRAY, Color.BLUE)
                .authRequestAppBar(View.GONE)
                .listHeaders(View.VISIBLE, Color.argb(100, 0, 0, 0), Color.CYAN)
                .listItems(Color.argb(100, 0, 0, 0), Color.CYAN)
                .background(new ColorDrawable(Color.DKGRAY))
                .backgroundOverlay(Color.LTGRAY)
                .settingsHeaders(Color.BLUE, Color.WHITE)
                .factorsSecurityIcons(View.VISIBLE, R.drawable.ic_photo_camera_white_24dp, R.drawable.ic_clear_white_24dp, R.drawable.ic_help_black_24dp, 0, 0, ContextCompat.getColor(this, R.color.demo_generic_orange))
                .listItems(Color.parseColor("#7B5F49"), ContextCompat.getColor(this, R.color.demo_generic_orange))
                .factorsBusyIcons(R.drawable.ic_check_black_48dp, R.drawable.ic_check_black_48dp, R.drawable.ic_check_black_48dp)
                .helpMenuItems(false)
                .button(ContextCompat.getDrawable(this, R.drawable.pinpad_button_bg), Color.WHITE)
                .buttonNegative(new ColorDrawable(Color.RED), Color.CYAN)
                .circleCode(Color.MAGENTA, Color.GREEN)
                .pinCode(ContextCompat.getDrawable(this, R.drawable.lk_textbutton_background), Color.YELLOW)
                .geoFence(ContextCompat.getColor(this, R.color.demo_generic_orange))
                .editText(ContextCompat.getColor(this, R.color.demo_generic_orange), ContextCompat.getColor(this, R.color.demo_generic_blue))
                .expirationTimer(ContextCompat.getColor(this, R.color.demo_generic_green), ContextCompat.getColor(this, R.color.demo_generic_blue), ContextCompat.getColor(this, R.color.demo_generic_orange))
                .denialReasons(ContextCompat.getColor(this, R.color.demo_generic_blue), ContextCompat.getColor(this, R.color.demo_generic_orange))
                .authResponseButton(R.drawable.arb_bg_positive, android.R.color.white, R.color.arb_positive_fill)
                .authResponseButtonNegative(R.drawable.arb_bg_negative, android.R.color.white, R.color.arb_negative_fill)
                .authResponseAuthorizedColor(Color.parseColor("#a8ff00"))
                .authResponseDeniedColor(Color.parseColor("#c000ff"))
                .authResponseFailedColor(ContextCompat.getColor(this, R.color.demo_generic_orange))
                .authContentViewBackground(Color.parseColor("#00695C"))
                .build();

        AuthenticatorConfig.Builder configBuilder = new AuthenticatorConfig.Builder()
                .activationDelayLocation(60)
                .activationDelayWearable(60)
                .keyPairSize(AuthenticatorConfig.Builder.KEYSIZE_MINIMUM);
        AuthenticatorUIConfig.Builder uiConfigBuilder = new AuthenticatorUIConfig.Builder()
                .theme(R.style.DemoAppTheme); // Built theme programmatically in the next line
        //.theme(customTheme)

        final AuthenticatorManager manager = AuthenticatorManager.instance;
        manager.initialize(configBuilder.build());

        AuthenticatorUIManager uiManager = AuthenticatorUIManager.getInstance();
        uiManager.initialize(uiConfigBuilder.build(this.getApplicationContext()));

        DeviceLinkedEventCallback onDeviceLink = new DeviceLinkedEventCallback() {
            @Override
            public void onSuccess(final Device device) {
                final String deviceName = device.getName();
                Log.i(TAG, String.format(Locale.getDefault(), "Link-event=%b Device-name=%s", true, deviceName));
            }

            @Override
            public void onFailure(final @NonNull Exception e) {
                Log.i(TAG, String.format(Locale.getDefault(), "Link-event=%b Device-name=%s", false, null));
            }
        };

        UnlinkDeviceEventCallback onDeviceUnlink = new UnlinkDeviceEventCallback() {
            @Override
            public void onSuccess(final @NonNull Device device) {
                Log.i(TAG, String.format(Locale.getDefault(), "Unlink-event=%b error=%s", true, null));
            }

            @Override
            public void onFailure(final @NonNull Exception e) {
                Log.i(TAG, String.format(Locale.getDefault(), "Unlink-event=%b error=%s", false, e.getMessage()));
            }
        };

        manager.registerForEvents(onDeviceLink, onDeviceUnlink);

        AuthRequestPushReceivedEvent authRequestPushReceivedEvent = new AuthRequestPushReceivedEvent() {
            @Override
            public void onSuccess(@Nullable Void result) {
                final Intent i = new Intent(DemoApplication.this, AuthRequestActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        };

        AuthRequestManager.instance.registerForEvents(authRequestPushReceivedEvent);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.i(DemoApplication.class.getSimpleName(), "Failed to get FirebaseMessaging Token. Is it properly setup for this application?");
                    return;
                }

                String token = task.getResult();

                Log.i(DemoApplication.class.getSimpleName(), "Retrieved FirebaseMessaging Token: " + token);
                manager.setPushDeviceToken(token);
            }
        });
    }
}