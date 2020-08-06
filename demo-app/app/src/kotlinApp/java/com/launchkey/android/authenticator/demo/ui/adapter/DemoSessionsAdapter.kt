package com.launchkey.android.authenticator.demo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.sdk.session.Session
import java.util.*

class DemoSessionsAdapter(private val mContext: Context, sessions: List<Session>, private val mItemClickListener: AdapterView.OnItemClickListener?) : BaseAdapter() {
    private var mSessions: List<Session> = ArrayList<Session>()
    private val mInternalClickListener: View.OnClickListener

    init {
        mSessions = sessions

        mInternalClickListener = View.OnClickListener { v ->
            if (v != null && v.tag != null) {
                val position = v.tag as Int

                mItemClickListener?.onItemClick(null, v, position, position.toLong())
            }
        }
    }

    override fun getCount(): Int {
        return mSessions.size
    }

    override fun getItem(position: Int): Session {
        return mSessions[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {

        //Relying on the authorizations layout for items

        var v: View? = convertView

        if (v == null) {
            v = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.demo_authorizations_item, parent, false)
        }

        val s = getItem(position)

        //fetch an application's icon via its url with Application.getAppIcon()

        val name = v!!.findViewById<View>(R.id.demo_authorizations_item_name) as TextView
        name.text = s.name

        //TODO: Update usage of ID as placeholder for potential context being sent
        val context = v.findViewById<View>(R.id.demo_authorizations_item_context) as TextView
        context.text = s.id

        val millisAgo = s.getCreatedAgoMillis(mContext)

        val action = v.findViewById<View>(R.id.demo_authorizations_item_text_action) as TextView
        action.text = String.format(Locale.getDefault(), "%d seconds ago", millisAgo / 1000)

        /*
        TextView status = (TextView) v.findViewById(R.id.demo_authorizations_item_text_status);
        status.setText(a.getStatus().toUpperCase());

        TextView transactional = (TextView) v.findViewById(R.id.demo_authorizations_item_text_transactional);
        transactional.setText(a.isTransactional() ? "TRANSACTIONAL" : "");
        */

        val button = v.findViewById<View>(R.id.demo_authorizations_item_button) as Button
        button.text = "LOG OUT"
        button.setOnClickListener(mInternalClickListener)
        button.tag = position

        return v
    }
}
