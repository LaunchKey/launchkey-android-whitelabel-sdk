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
import com.launchkey.android.authenticator.sdk.device.Device
import java.util.*

class DemoDevicesAdapter(private val mContext: Context, devices: List<Device>, private val mItemClickListener: AdapterView.OnItemClickListener?) : BaseAdapter() {
    private var mDevices: List<Device> = ArrayList()
    private val mInternalClickListener: View.OnClickListener

    init {
        mDevices = devices

        mInternalClickListener = View.OnClickListener { v ->
            if (v != null && v.tag != null) {
                val position = v.tag as Int

                mItemClickListener?.onItemClick(null, v, position, position.toLong())
            }
        }
    }

    override fun getCount(): Int {
        return mDevices.size
    }

    override fun getItem(position: Int): Device {
        return mDevices[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var v: View? = convertView

        if (v == null) {
            v = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.demo_devices_item, parent, false)
        }

        val d = getItem(position)

        val currentDevice = v!!.findViewById<View>(R.id.demo_devices_item_currentdevice) as TextView
        currentDevice.visibility = if (position == 0) View.VISIBLE else View.GONE

        val name = v.findViewById<View>(R.id.demo_devices_item_name) as TextView
        name.text = d.name

        val status = v.findViewById<View>(R.id.demo_devices_item_status) as TextView
        status.text = d.type

        val button = v.findViewById<View>(R.id.demo_devices_item_button) as Button
        button.setOnClickListener(mInternalClickListener)
        button.tag = position

        return v
    }
}
