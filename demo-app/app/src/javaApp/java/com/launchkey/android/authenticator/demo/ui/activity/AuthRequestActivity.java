package com.launchkey.android.authenticator.demo.ui.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.util.Utils;
import com.launchkey.android.authenticator.sdk.auth.AuthRequest;
import com.launchkey.android.authenticator.sdk.auth.event.AuthRequestResponseEventCallback;
import com.launchkey.android.authenticator.sdk.auth.event.GetAuthRequestEventCallback;
import com.launchkey.android.authenticator.sdk.auth.AuthRequestManager;
import com.launchkey.android.authenticator.sdk.error.BaseError;
import com.launchkey.android.authenticator.sdk.error.DeviceNotLinkedError;
import com.launchkey.android.authenticator.sdk.ui.AuthRequestFragment;

public class AuthRequestActivity extends BaseDemoActivity implements AuthRequestFragment.Listener {
    private final String EXTRA_REQUEST_VISIBILITY = "is_request_shown";
    private final AuthRequestManager authRequestManager = AuthRequestManager.getInstance();
    private TextView mNoRequestsView;
    private boolean mRequestShown = false;
    private final @NonNull GetAuthRequestEventCallback mOnRequest = new GetAuthRequestEventCallback() {
        @Override
        public void onEventResult(boolean successful, BaseError error, AuthRequest authRequest) {
            NotificationManager notificationManager =
                    (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.cancelAll();
            }

            updateNoRequestsView(false);

            if (!successful) {
                handleError(error);
            }
        }
    };
    private final @NonNull AuthRequestResponseEventCallback mOnResponse = new AuthRequestResponseEventCallback() {
        @Override
        public void onEventResult(boolean successful, BaseError error, Boolean authorized) {
            if (successful) {
                updateNoRequestsView(false);
            } else {
                handleError(error);
            }
        }
    };

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_authrequest);
        mNoRequestsView = findViewById(R.id.demo_activity_authrequest_norequest);
        mNoRequestsView.setVisibility(mRequestShown ? View.GONE : View.VISIBLE);

        Toolbar mToolbar = findViewById(R.id.demo_activity_authrequest_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Request");


        updateNoRequestsView(false);
    }

    @Override
    protected void onRestoreInstanceState(final @NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int isRequestShown = savedInstanceState.getInt(EXTRA_REQUEST_VISIBILITY, View.VISIBLE);
        onUiChange(isRequestShown != View.VISIBLE);
    }

    private void handleError(final @NonNull BaseError error) {
        if (!(error instanceof DeviceNotLinkedError)) {
            //this will potentially cover ExpiredAuthRequestError most of the time
            Toast.makeText(AuthRequestActivity.this, Utils.getMessageForBaseError(error),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            if (getAuthenticatorManager().isDeviceLinked()) {
                onRefresh();
            } else {
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        authRequestManager.registerForEvents(mOnRequest, mOnResponse);
    }

    @Override
    protected void onPause() {
        super.onPause();
        authRequestManager.unregisterForEvents(mOnRequest, mOnResponse);
    }

    @Override
    public void onBackPressed() {
        if (authRequestManager.hasPending()) {
            return;
        }

        super.onBackPressed();
    }

    private void onRefresh() {
        updateNoRequestsView(true);
        authRequestManager.check();
    }

    private void updateNoRequestsView(boolean isChecking) {
        int messageRes = isChecking ? R.string.demo_activity_authrequest_refreshing_message
                : R.string.demo_activity_authrequest_norequests_message;
        mNoRequestsView.setText(messageRes);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_REQUEST_VISIBILITY, mNoRequestsView.getVisibility());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onUiChange(boolean requestShown) {
        mRequestShown = requestShown;
        if (mNoRequestsView != null) {
            mNoRequestsView.setVisibility(requestShown ? View.GONE : View.VISIBLE);
        }
    }
}
