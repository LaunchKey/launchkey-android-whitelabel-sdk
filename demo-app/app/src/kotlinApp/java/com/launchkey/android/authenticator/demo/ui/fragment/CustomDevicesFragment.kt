package com.launchkey.android.authenticator.demo.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import com.google.android.material.snackbar.Snackbar
import com.launchkey.android.authenticator.demo.R
import com.launchkey.android.authenticator.demo.databinding.DemoFragmentDevicesBinding
import com.launchkey.android.authenticator.demo.ui.adapter.DemoDevicesAdapter
import com.launchkey.android.authenticator.demo.util.Utils.finish
import com.launchkey.android.authenticator.demo.util.Utils.simpleSnackbarForBaseError
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager
import com.launchkey.android.authenticator.sdk.core.authentication_management.Device
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.GetDevicesEventCallback
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.UnlinkDeviceEventCallback
import com.launchkey.android.authenticator.sdk.core.util.CompositeDisposable
import java.util.*

class CustomDevicesFragment : BaseDemoFragment<DemoFragmentDevicesBinding>(R.layout.demo_fragment_devices), OnItemClickListener {
    private val compositeDisposable = CompositeDisposable()
    private val deviceManager = AuthenticatorManager.instance
    private val devices: MutableList<Device> = ArrayList()
    private var adapter: DemoDevicesAdapter? = null
    private val getDevicesEventCallback: GetDevicesEventCallback = object : GetDevicesEventCallback() {
        override fun onSuccess(devices: List<Device>) {
            this@CustomDevicesFragment.devices.clear()
            this@CustomDevicesFragment.devices.addAll(devices)
            adapter!!.notifyDataSetChanged()
        }

        override fun onFailure(e: Exception) {
            simpleSnackbarForBaseError(binding!!.demoFragmentDevicesList, e)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DemoFragmentDevicesBinding.bind(view)
        adapter = DemoDevicesAdapter(activity!!, devices, this)
        binding!!.demoFragmentDevicesList.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            compositeDisposable.add(deviceManager.getDevices(getDevicesEventCallback))
        }
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        if (AuthenticatorManager.instance.isDeviceLinked) {
            val deviceToUnlink = adapter!!.getItem(position)
            compositeDisposable.add(deviceManager.unlinkDevice(deviceToUnlink, object : UnlinkDeviceEventCallback() {
                override fun onSuccess(device: Device) {
                    val message = String.format(Locale.getDefault(), "Device \"%s\" unlinked", device.name)
                    Snackbar.make(binding!!.demoFragmentDevicesList, message, Snackbar.LENGTH_INDEFINITE)
                            .setAction("Return") { finish(this@CustomDevicesFragment) }
                            .show()
                    if (!device.isCurrent) {
                        deviceManager.getDevices(getDevicesEventCallback)
                    }
                }

                override fun onFailure(e: Exception) {
                    simpleSnackbarForBaseError(binding!!.demoFragmentDevicesList, e)
                }
            }))
        }
    }
}