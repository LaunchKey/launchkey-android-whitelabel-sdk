package com.launchkey.android.authenticator.demo.ui.activity

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.databinding.DemoActivityAuthrequestBinding
import com.launchkey.android.authenticator.demo.util.Utils.getMessageForBaseError
import com.launchkey.android.authenticator.sdk.core.exception.DeviceNotLinkedException
import com.launchkey.android.authenticator.sdk.ui.AuthRequestFragment

class AuthRequestActivity : BaseDemoActivity<DemoActivityAuthrequestBinding>(R.layout.demo_activity_authrequest), AuthRequestFragment.Listener {
    private var authRequestActivityViewModel: AuthRequestActivityViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DemoActivityAuthrequestBinding.bind(findViewById(R.id.demo_activity_authrequest_root))
        authRequestActivityViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(AuthRequestActivityViewModel::class.java)
        authRequestActivityViewModel!!.getState().observe(this, Observer { state ->
            if (state is AuthRequestActivityViewModel.State.Success) {
                updateNoRequestsView(false)
                clearNotifications()
            } else if (state is AuthRequestActivityViewModel.State.Loading) {
                updateNoRequestsView(true)
            } else if (state is AuthRequestActivityViewModel.State.Failed) {
                handleError(state.exception)
                updateNoRequestsView(false)
                clearNotifications()
            }
        })
        val toolbar = binding!!.demoActivityAuthrequestToolbar
        toolbar.title = "Request"
        setSupportActionBar(toolbar)
        updateNoRequestsView(true)
        onUiChange(authRequestActivityViewModel!!.currentAuthRequest != null)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.refresh, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_refresh) {
            if (authenticatorManager.isDeviceLinked) {
                authRequestActivityViewModel!!.checkForAuthRequest()
            } else {
                finish()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleError(e: Exception) {
        if (e !is DeviceNotLinkedException) {
            //this will potentially cover ExpiredAuthRequestError most of the time
            Toast.makeText(this@AuthRequestActivity, getMessageForBaseError(e),
                    Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        if (authRequestActivityViewModel!!.currentAuthRequest != null) {
            return
        }
        super.onBackPressed()
    }

    private fun updateNoRequestsView(isChecking: Boolean) {
        val messageRes = if (isChecking) R.string.demo_activity_authrequest_refreshing_message else R.string.demo_activity_authrequest_norequests_message
        binding!!.demoActivityAuthrequestNorequest.setText(messageRes)
    }

    private fun clearNotifications() {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager?.cancelAll()
    }

    override fun onUiChange(requestShown: Boolean) {
        if (!requestShown) {
            authRequestActivityViewModel!!.currentAuthRequest = null
            updateNoRequestsView(false)
        }
        binding!!.demoActivityAuthrequestNorequest.visibility = if (requestShown) View.GONE else View.VISIBLE
    }
}