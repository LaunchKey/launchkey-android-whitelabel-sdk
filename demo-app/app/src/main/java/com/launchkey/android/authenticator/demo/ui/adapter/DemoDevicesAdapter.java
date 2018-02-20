package com.launchkey.android.authenticator.demo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.launchkey.android.authenticator.sdk.device.Device;
import com.launchkey.android.authenticator.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by armando on 7/11/16.
 */
public class DemoDevicesAdapter extends BaseAdapter {

    private Context mContext;
    private List<Device> mDevices = new ArrayList<>();
    private AdapterView.OnItemClickListener mItemClickListener;
    private View.OnClickListener mInternalClickListener;

    public DemoDevicesAdapter(Context c, List<Device> devices, AdapterView.OnItemClickListener l) {
        mContext = c;
        mDevices = devices;
        mItemClickListener = l;

        mInternalClickListener = new View.OnClickListener() {
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

        TextView currentDevice = (TextView) v.findViewById(R.id.demo_devices_item_currentdevice);
        currentDevice.setVisibility(position == 0 ? View.VISIBLE : View.GONE);

        TextView name = (TextView) v.findViewById(R.id.demo_devices_item_name);
        name.setText(d.getName());

        TextView status = (TextView) v.findViewById(R.id.demo_devices_item_status);
        status.setText(d.getType());

        Button button = (Button) v.findViewById(R.id.demo_devices_item_button);
        button.setOnClickListener(mInternalClickListener);
        button.setTag(position);

        return v;
    }
}
