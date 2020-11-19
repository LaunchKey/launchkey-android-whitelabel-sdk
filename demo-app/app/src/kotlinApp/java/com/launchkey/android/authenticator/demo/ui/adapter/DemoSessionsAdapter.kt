package com.launchkey.android.authenticator.demo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.sdk.core.authentication_management.Session
import java.util.*

class DemoSessionsAdapter(private val context: Context, private val mSessions: List<Session>, private val mItemClickListener: OnItemClickListener?) : BaseAdapter() {
    private val mInternalClickListener = View.OnClickListener { v ->
        if (v != null && v.tag != null) {
            val position = v.tag as Int
            mItemClickListener?.onItemClick(null, v, position, position.toLong())
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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        //Relying on the authorizations layout for items
        var v = convertView
        if (v == null) {
            v = LayoutInflater.from(context)
                    .inflate(R.layout.demo_authorizations_item, parent, false)
        }
        val s = getItem(position)

        //fetch an application's icon via its url with Application.getAppIcon()
        val name = v!!.findViewById<TextView>(R.id.demo_authorizations_item_name)
        name.text = s.name

        //TODO: Update usage of ID as placeholder for potential context being sent
        val context = v.findViewById<TextView>(R.id.demo_authorizations_item_context)
        context.text = s.id
        val millisAgo = System.currentTimeMillis() - s.createdAtMillis
        val action = v.findViewById<TextView>(R.id.demo_authorizations_item_text_action)
        action.text = String.format(Locale.getDefault(), "%d seconds ago", millisAgo / 1000)
        val button = v.findViewById<Button>(R.id.demo_authorizations_item_button)
        button.text = "LOG OUT"
        button.setOnClickListener(mInternalClickListener)
        button.tag = position
        return v
    }

}