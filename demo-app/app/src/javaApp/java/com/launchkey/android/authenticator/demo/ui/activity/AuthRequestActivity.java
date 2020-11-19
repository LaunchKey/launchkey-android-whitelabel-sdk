package com.launchkey.android.authenticator.demo.ui.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.databinding.DemoActivityAuthrequestBinding;
import com.launchkey.android.authenticator.demo.util.Utils;
import com.launchkey.android.authenticator.sdk.core.exception.DeviceNotLinkedException;
import com.launchkey.android.authenticator.sdk.ui.AuthRequestFragment;

public class AuthRequestActivity extends BaseDemoActivity<DemoActivityAuthrequestBinding> implements AuthRequestFragment.Listener {
    public AuthRequestActivity() {
        super(R.layout.demo_activity_authrequest);
    }

    private AuthRequestActivityViewModel authRequestActivityViewModel;

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DemoActivityAuthrequestBinding.bind(findViewById(R.id.demo_activity_authrequest_root));

        authRequestActivityViewModel = new ViewModelProvider(this, getDefaultViewModelProviderFactory()).get(AuthRequestActivityViewModel.class);
        authRequestActivityViewModel.getState().observe(this, new Observer<AuthRequestActivityViewModel.State>() {
            @Override
            public void onChanged(AuthRequestActivityViewModel.State state) {
                if (state instanceof AuthRequestActivityViewModel.State.Success) {
                    updateNoRequestsView(false);
                    clearNotifications();
                } else if (state instanceof AuthRequestActivityViewModel.State.Loading) {
                    updateNoRequestsView(true);
                } else if (state instanceof AuthRequestActivityViewModel.State.Failed) {
                    handleError(((AuthRequestActivityViewModel.State.Failed) state).getException());
                    updateNoRequestsView(false);
                    clearNotifications();
                }
            }
        });

        Toolbar toolbar = binding.demoActivityAuthrequestToolbar;
        toolbar.setTitle("Request");
        setSupportActionBar(toolbar);
        updateNoRequestsView(true);
        onUiChange(authRequestActivityViewModel.currentAuthRequest != null);
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
                authRequestActivityViewModel.checkForAuthRequest();
            } else {
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleError(final @NonNull Exception e) {
        if (!(e instanceof DeviceNotLinkedException)) {
            //this will potentially cover ExpiredAuthRequestError most of the time
            Toast.makeText(AuthRequestActivity.this, Utils.getMessageForBaseError(e),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (authRequestActivityViewModel.currentAuthRequest != null) {
            return;
        }

        super.onBackPressed();
    }

    private void updateNoRequestsView(boolean isChecking) {
        int messageRes = isChecking ? R.string.demo_activity_authrequest_refreshing_message
                : R.string.demo_activity_authrequest_norequests_message;

        binding.demoActivityAuthrequestNorequest.setText(messageRes);
    }

    private void clearNotifications() {
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
    }

    @Override
    public void onUiChange(boolean requestShown) {
        if (!requestShown) {
            authRequestActivityViewModel.currentAuthRequest = null;
            updateNoRequestsView(false);
        }

        binding.demoActivityAuthrequestNorequest.setVisibility(requestShown ? View.GONE : View.VISIBLE);
    }
}
