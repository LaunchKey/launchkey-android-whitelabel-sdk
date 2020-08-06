package com.launchkey.android.authenticator.demo.ui.activity

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.util.Utils
import com.launchkey.android.authenticator.sdk.DeviceUnlinkedEventCallback
import com.launchkey.android.authenticator.sdk.auth.AuthRequest
import com.launchkey.android.authenticator.sdk.auth.AuthRequestManager
import com.launchkey.android.authenticator.sdk.auth.event.AuthRequestResponseEventCallback
import com.launchkey.android.authenticator.sdk.auth.event.GetAuthRequestEventCallback
import com.launchkey.android.authenticator.sdk.error.BaseError
import com.launchkey.android.authenticator.sdk.error.DeviceNotLinkedError

class AuthRequestActivity : BaseDemoActivity() {

    private var mOnUnlink: DeviceUnlinkedEventCallback? = null
    private var mAuthRequestManager: AuthRequestManager? = null
    private var mOnRequest: GetAuthRequestEventCallback? = null
    private var mOnResponse: AuthRequestResponseEventCallback? = null

    private var mNoRequestsView: TextView? = null
    private var mToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_authrequest)

        mToolbar = findViewById<View>(R.id.demo_activity_authrequest_toolbar) as Toolbar
        setSupportActionBar(mToolbar)
        supportActionBar!!.title = "Request"

        mNoRequestsView = findViewById<View>(R.id.demo_activity_authrequest_norequest) as TextView

        mOnUnlink = object : DeviceUnlinkedEventCallback() {
            override fun onEventResult(ok: Boolean, e: BaseError?, o: Any?) {

                finish()
            }
        }

        mAuthRequestManager = AuthRequestManager.getInstance(this)

        mOnRequest = object : GetAuthRequestEventCallback() {

            override fun onEventResult(successful: Boolean, error: BaseError?, authRequest: AuthRequest?) {

                updateNoRequestsView(false)

                if (!successful) {
                    //let the callback handle the error
                    mOnResponse!!.onEventResult(false, error, null)
                }
            }
        }

        mOnResponse = object : AuthRequestResponseEventCallback() {
            override fun onEventResult(successful: Boolean, error: BaseError?, authorized: Boolean?) {

                if (successful) {
                    mOnRequest!!.onEventResult(true, null, null)
                } else {
                    if (error is DeviceNotLinkedError) {
                        showUnlinkedDialog()
                    } else {
                        //this will potentially cover ExpiredAuthRequestError most of the time
                        Toast.makeText(this@AuthRequestActivity, Utils.getMessageForBaseError(error),
                                Toast.LENGTH_SHORT).show()
                    }
                }

                finishIfNecessary()
            }
        }

        updateNoRequestsView(false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.refresh, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_refresh -> {
                onRefresh()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        mAuthRequestManager!!.registerForEvents(mOnRequest, mOnResponse)
        authenticatorManager.registerForEvents(mOnUnlink)
    }

    override fun onPause() {
        super.onPause()
        mAuthRequestManager!!.unregisterForEvents(mOnRequest, mOnResponse)
        authenticatorManager.unregisterForEvents(mOnUnlink)
    }

    override fun onBackPressed() {

        if (hasPending()) {
            return
        }

        super.onBackPressed()
    }

    private fun onRefresh() {
        updateNoRequestsView(true)
        mAuthRequestManager!!.check()
    }

    private fun updateNoRequestsView(isChecking: Boolean) {

        val messageRes = if (isChecking)
            R.string.demo_activity_authrequest_refreshing_message
        else
            R.string.demo_activity_authrequest_norequests_message
        mNoRequestsView!!.setText(messageRes)

        mNoRequestsView!!.visibility = if (hasPending()) View.GONE else View.VISIBLE
    }

    private fun finishIfNecessary() {

        if (!hasPending()) {

            finish()
        }
    }

    private fun hasPending(): Boolean {
        return mAuthRequestManager!!.pendingAuthRequest != null
    }

    private fun showUnlinkedDialog() {

        val positiveClick = DialogInterface.OnClickListener { dialog, which -> finish() }

        val alertDialog = AlertDialog.Builder(this)
                .setTitle(R.string.demo_activity_authrequest_dialog_unlinked_title)
                .setMessage(R.string.demo_activity_authrequest_dialog_unlinked_message)
                .setPositiveButton(R.string.demo_generic_ok, positiveClick)
                .create()

        alertDialog.show()

        val messageView = alertDialog.findViewById<View>(android.R.id.message) as TextView?
        if (messageView != null) {
            messageView.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        }
    }
}
