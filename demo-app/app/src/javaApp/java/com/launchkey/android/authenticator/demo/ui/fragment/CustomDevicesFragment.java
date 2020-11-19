package com.launchkey.android.authenticator.demo.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.databinding.DemoFragmentDevicesBinding;
import com.launchkey.android.authenticator.demo.ui.adapter.DemoDevicesAdapter;
import com.launchkey.android.authenticator.demo.util.Utils;
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager;
import com.launchkey.android.authenticator.sdk.core.authentication_management.Device;
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.GetDevicesEventCallback;
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.UnlinkDeviceEventCallback;
import com.launchkey.android.authenticator.sdk.core.util.CompositeDisposable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomDevicesFragment extends BaseDemoFragment<DemoFragmentDevicesBinding>
        implements AdapterView.OnItemClickListener {

    private final @NonNull CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final AuthenticatorManager deviceManager = AuthenticatorManager.instance;
    private List<Device> devices = new ArrayList<>();
    private DemoDevicesAdapter adapter;
    private final GetDevicesEventCallback getDevicesEventCallback = new GetDevicesEventCallback() {
        @Override
        public void onSuccess(@NonNull List<Device> devices) {
            CustomDevicesFragment.this.devices.clear();
            CustomDevicesFragment.this.devices.addAll(devices);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(@NonNull Exception e) {
            Utils.simpleSnackbarForBaseError(binding.demoFragmentDevicesList, e);
        }
    };

    public CustomDevicesFragment() {
        super(R.layout.demo_fragment_devices);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DemoFragmentDevicesBinding.bind(view);
        adapter = new DemoDevicesAdapter(getActivity(), devices, this);
        binding.demoFragmentDevicesList.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            compositeDisposable.add(deviceManager.getDevices(getDevicesEventCallback));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (AuthenticatorManager.instance.isDeviceLinked()) {
            Device deviceToUnlink = adapter.getItem(position);
            compositeDisposable.add(deviceManager.unlinkDevice(deviceToUnlink, new UnlinkDeviceEventCallback() {
                @Override
                public void onSuccess(final @NonNull Device device) {
                    String message = String.format(Locale.getDefault(), "Device \"%s\" unlinked", device.getName());
                    Snackbar.make(binding.demoFragmentDevicesList, message, Snackbar.LENGTH_INDEFINITE)
                            .setAction("Return", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Utils.finish(CustomDevicesFragment.this);
                                }
                            })
                            .show();
                    if (!device.isCurrent()) {
                        deviceManager.getDevices(getDevicesEventCallback);
                    }
                }

                @Override
                public void onFailure(final @NonNull Exception e) {
                    Utils.simpleSnackbarForBaseError(binding.demoFragmentDevicesList, e);
                }
            }));
        }
    }
}
