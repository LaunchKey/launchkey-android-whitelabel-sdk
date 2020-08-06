package com.launchkey.android.authenticator.demo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.sdk.device.Device;

import java.util.List;

public class DemoDevicesAdapter extends BaseAdapter {

    private final @NonNull Context mContext;
    private final @NonNull List<Device> mDevices;
    private final @Nullable
    AdapterView.OnItemClickListener mItemClickListener;
    private final @NonNull View.OnClickListener mInternalClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v != null && v.getTag() != null) {
                int position = (int) v.getTag();

                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(null, v, position, position);
                }
            }
        }
    };

    public DemoDevicesAdapter(Context c, List<Device> devices, AdapterView.OnItemClickListener l) {
        mContext = c;
        mDevices = devices;
        mItemClickListener = l;
    }

    @Override
    public int getCount() {
        return mDevices.size();
    }

    @Override
    public Device getItem(int position) {
        return mDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            v = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.demo_devices_item, parent, false);
        }

        Device d = getItem(position);

        TextView currentDevice = v.findViewById(R.id.demo_devices_item_currentdevice);
        currentDevice.setVisibility(position == 0 ? View.VISIBLE : View.GONE);

        TextView name = v.findViewById(R.id.demo_devices_item_name);
        name.setText(d.getName());

        TextView status = v.findViewById(R.id.demo_devices_item_status);
        status.setText(d.getType());

        Button button = v.findViewById(R.id.demo_devices_item_button);
        button.setOnClickListener(mInternalClickListener);
        button.setTag(position);

        return v;
    }
}
