package com.launchkey.android.authenticator.demo.ui.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.ui.adapter.DemoDevicesAdapter
import com.launchkey.android.authenticator.demo.util.Utils
import com.launchkey.android.authenticator.sdk.device.Device
import com.launchkey.android.authenticator.sdk.device.DeviceManager
import com.launchkey.android.authenticator.sdk.device.event.GetDevicesEventCallback
import com.launchkey.android.authenticator.sdk.error.BaseError
import java.util.*

class CustomDevicesFragment3 : BaseDemoFragment(), AdapterView.OnItemClickListener {

    private val mDevices = ArrayList<Device>()
    private var mList: ListView? = null
    private var mAdapter: DemoDevicesAdapter? = null
    private var mDeviceManager: DeviceManager? = null
    private var mGetDevicesCallback: GetDevicesEventCallback? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.demo_fragment_devices, container, false)
        return postInflationSetup(root)
    }

    private fun postInflationSetup(root: View): View {
        mAdapter = DemoDevicesAdapter(activity!!, mDevices, this)

        mList = root.findViewById<View>(R.id.demo_fragment_devices_list) as ListView
        mList!!.adapter = mAdapter

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mDeviceManager = DeviceManager.getInstance(activity)
        mGetDevicesCallback = object : GetDevicesEventCallback() {

            override fun onEventResult(successful: Boolean, error: BaseError?, devices: List<Device>?) {

                if (successful) {
                    mDevices.clear()
                    mDevices.addAll(devices!!)
                    mAdapter!!.notifyDataSetChanged()
                } else {
                    Utils.simpleSnackbarForBaseError(mList, error)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mDeviceManager!!.registerForEvents(mGetDevicesCallback)
        mDeviceManager!!.getDevices()
    }

    override fun onPause() {
        super.onPause()
        mDeviceManager!!.unregisterForEvents(mGetDevicesCallback)
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

        val deviceToUnlink = mAdapter!!.getItem(position)
        mDeviceManager!!.unlinkDevice(deviceToUnlink) { successful, error, device ->
            if (successful) {
                val message = String.format(Locale.getDefault(), "Device \"%s\" unlinked", device.name)
                Snackbar.make(mList!!, message, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Return") { Utils.finish(this@CustomDevicesFragment3) }
                        .show()
                mDeviceManager!!.getDevices()
            } else {
                Utils.simpleSnackbarForBaseError(mList, error)
            }
        }
    }
}
