package com.launchkey.android.authenticator.demo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.launchkey.android.authenticator.demo.R


/**
 * Created by armando on 7/8/16.
 */
class DemoFeatureAdapter(private val mContext: Context, i: IntArray) : BaseAdapter() {
    private var mItems = IntArray(0)

    init {
        mItems = i
    }

    override fun getCount(): Int {
        return mItems.size
    }

    override fun getItem(position: Int): Int? {
        return mItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v: TextView? = convertView as? TextView

        if (v == null) {
            v = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.demo_activity_list_item, parent, false) as TextView
        }

        v.text = mContext.getString(getItem(position)!!)

        return v
    }
}
