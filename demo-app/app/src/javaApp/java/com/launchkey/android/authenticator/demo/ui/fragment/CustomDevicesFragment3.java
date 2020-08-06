package com.launchkey.android.authenticator.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.ui.adapter.DemoDevicesAdapter;
import com.launchkey.android.authenticator.demo.util.Utils;
import com.launchkey.android.authenticator.sdk.AuthenticatorManager;
import com.launchkey.android.authenticator.sdk.SimpleOperationCallback;
import com.launchkey.android.authenticator.sdk.device.Device;
import com.launchkey.android.authenticator.sdk.device.DeviceManager;
import com.launchkey.android.authenticator.sdk.device.event.GetDevicesEventCallback;
import com.launchkey.android.authenticator.sdk.error.BaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomDevicesFragment3 extends BaseDemoFragment
        implements AdapterView.OnItemClickListener {

    private final DeviceManager deviceManager = DeviceManager.getInstance();
    private List<Device> devices = new ArrayList<>();
    private ListView listView;
    private DemoDevicesAdapter adapter;
    private final GetDevicesEventCallback getDevicesEventCallback = new GetDevicesEventCallback() {

        @Override
        public void onEventResult(boolean successful, BaseError error, List<Device> devices) {

            if (successful) {
                CustomDevicesFragment3.this.devices.clear();
                CustomDevicesFragment3.this.devices.addAll(devices);
                adapter.notifyDataSetChanged();
            } else {
                Utils.simpleSnackbarForBaseError(listView, error);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.demo_fragment_devices, container, false);
        return postInflationSetup(root);
    }

    private View postInflationSetup(View root) {
        adapter = new DemoDevicesAdapter(getActivity(), devices, this);

        listView = (ListView) root.findViewById(R.id.demo_fragment_devices_list);
        listView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            deviceManager.getDevices();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        deviceManager.registerForEvents(getDevicesEventCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        deviceManager.unregisterForEvents(getDevicesEventCallback);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (AuthenticatorManager.getInstance().isDeviceLinked()) {
            Device deviceToUnlink = adapter.getItem(position);
            deviceManager.unlinkDevice(deviceToUnlink, new SimpleOperationCallback<Device>() {

                @Override
                public void onResult(boolean successful, BaseError error, Device device) {

                    if (successful) {
                        String message = String.format(Locale.getDefault(), "Device \"%s\" unlinked", device.getName());
                        Snackbar.make(listView, message, Snackbar.LENGTH_INDEFINITE)
                                .setAction("Return", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Utils.finish(CustomDevicesFragment3.this);
                                    }
                                })
                                .show();
                        if (AuthenticatorManager.getInstance().isDeviceLinked()) {
                            deviceManager.getDevices();
                        }
                    } else {
                        Utils.simpleSnackbarForBaseError(listView, error);
                    }
                }
            });
        }
    }
}
