package com.launchkey.android.authenticator.demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ListView
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.ui.adapter.DemoSessionsAdapter
import com.launchkey.android.authenticator.demo.util.Utils.simpleSnackbar
import com.launchkey.android.authenticator.demo.util.Utils.simpleSnackbarForBaseError
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager
import com.launchkey.android.authenticator.sdk.core.authentication_management.Session
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.EndAllSessionsEventCallback
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.EndSessionEventCallback
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.GetSessionsEventCallback
import com.launchkey.android.authenticator.sdk.core.util.CompositeDisposable
import java.util.*

class CustomSessionsFragment : BaseDemoFragment<ViewBinding>(), OnItemClickListener {
    private val compositeDisposable = CompositeDisposable()
    private val sessionManager = AuthenticatorManager.instance
    private val mSessions: MutableList<Session> = ArrayList()
    private var mList: ListView? = null
    private val endSessionEventCallback: EndSessionEventCallback = object : EndSessionEventCallback() {
        override fun onSuccess(session: Session) {
            refresh()
            simpleSnackbar(mList, "Session \"" + session.name + "\" ended")
        }

        override fun onFailure(e: Exception) {
            simpleSnackbarForBaseError(mList, e)
        }
    }
    private var adapter: DemoSessionsAdapter? = null
    private val getSessionsEventCallback: GetSessionsEventCallback = object : GetSessionsEventCallback() {
        override fun onSuccess(sessions: List<Session>) {
            mSessions.clear()
            mSessions.addAll(sessions)
            adapter!!.notifyDataSetChanged()
        }

        override fun onFailure(e: Exception) {
            simpleSnackbarForBaseError(mList, e)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.demo_fragment_sessions, container, false)
        return onLayoutPreprocessing(root)
    }

    private fun onLayoutPreprocessing(root: View): View {
        adapter = DemoSessionsAdapter(activity!!, mSessions, this)
        mList = root.findViewById(R.id.demo_fragment_sessions_list)
        mList!!.setAdapter(adapter)
        val clearAll = root.findViewById<Button>(R.id.demo_fragment_sessions_button)
        clearAll.setOnClickListener {
            sessionManager.endAllSessions(object : EndAllSessionsEventCallback() {
                override fun onSuccess(result: Void?) {
                    refresh()
                    simpleSnackbar(mList, "All sessions ended", Snackbar.LENGTH_SHORT)
                }

                override fun onFailure(e: Exception) {
                    simpleSnackbarForBaseError(mList, e)
                }
            })
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            refresh()
        }
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    private fun refresh() {
        compositeDisposable.add(sessionManager.getSessions(getSessionsEventCallback))
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        val session = adapter!!.getItem(position)
        compositeDisposable.add(sessionManager.endSession(session, endSessionEventCallback))
    }
}