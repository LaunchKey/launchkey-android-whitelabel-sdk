package com.launchkey.android.authenticator.demo.ui.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.ui.adapter.DemoSessionsAdapter
import com.launchkey.android.authenticator.demo.util.Utils
import com.launchkey.android.authenticator.sdk.error.BaseError
import com.launchkey.android.authenticator.sdk.session.Session
import com.launchkey.android.authenticator.sdk.session.SessionManager
import com.launchkey.android.authenticator.sdk.session.event.EndAllSessionsEventCallback
import com.launchkey.android.authenticator.sdk.session.event.EndSessionEventCallback
import com.launchkey.android.authenticator.sdk.session.event.GetSessionsEventCallback
import java.util.*

class CustomSessionsFragment : BaseDemoFragment(), AdapterView.OnItemClickListener {

    private val mSessions = ArrayList<Session>()
    private lateinit var mList: ListView
    private lateinit var mAdapter: DemoSessionsAdapter
    private lateinit var mSessionManager: SessionManager
    private lateinit var mGetSessionsCallback: GetSessionsEventCallback
    private lateinit var mEndSessionCallback: EndSessionEventCallback
    private lateinit var mEndAllSessionsCallback: EndAllSessionsEventCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.demo_fragment_sessions, container, false)
        return onLayoutPreprocessing(root)
    }

    private fun onLayoutPreprocessing(root: View): View {

        mAdapter = DemoSessionsAdapter(activity!!, mSessions, this)

        mList = root.findViewById<View>(R.id.demo_fragment_sessions_list) as ListView
        mList.adapter = mAdapter

        val clearAll = root.findViewById<View>(R.id.demo_fragment_sessions_button) as Button
        clearAll.setOnClickListener { mSessionManager.endAllSessions(null) }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mSessionManager = SessionManager.getInstance(activity)
        mGetSessionsCallback = object : GetSessionsEventCallback() {
            override fun onEventResult(successful: Boolean, error: BaseError?, sessions: List<Session>?) {
                if (successful) {
                    mSessions.clear()
                    mSessions.addAll(sessions!!)
                    mAdapter.notifyDataSetChanged()
                } else {
                    Utils.simpleSnackbarForBaseError(mList, error)
                }
            }
        }

        mEndSessionCallback = object : EndSessionEventCallback() {
            override fun onEventResult(successful: Boolean, error: BaseError?, session: Session?) {
                if (successful) {
                    refresh()
                    Utils.simpleSnackbar(mList, "Session \"" + session!!.name + "\" ended")
                } else {
                    Utils.simpleSnackbarForBaseError(mList, error)
                }
            }
        }

        mEndAllSessionsCallback = object : EndAllSessionsEventCallback() {
            override fun onEventResult(successful: Boolean, error: BaseError?, o: Any?) {
                if (successful) {
                    refresh()
                    Utils.simpleSnackbar(mList, "All sessions ended", Snackbar.LENGTH_SHORT)
                } else {
                    Utils.simpleSnackbarForBaseError(mList, error)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mSessionManager.registerForEvents(mGetSessionsCallback, mEndSessionCallback, mEndAllSessionsCallback)
        refresh()
    }

    override fun onPause() {
        super.onPause()
        mSessionManager.unregisterForEvents(mGetSessionsCallback, mEndSessionCallback, mEndAllSessionsCallback)
    }

    private fun refresh() {
        mSessionManager.getSessions()
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val session = mAdapter.getItem(position)
        mSessionManager.endSession(session, null)
    }
}
