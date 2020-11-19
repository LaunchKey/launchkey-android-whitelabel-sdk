package com.launchkey.android.authenticator.demo.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.sdk.core.authentication_management.Session;

import java.util.List;
import java.util.Locale;

public class DemoSessionsAdapter extends BaseAdapter {

    private final @NonNull Context context;
    private final @NonNull List<Session> mSessions;
    private final @Nullable AdapterView.OnItemClickListener mItemClickListener;
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

    public DemoSessionsAdapter(Context c, List<Session> sessions, AdapterView.OnItemClickListener l) {
        context = c;
        mSessions = sessions;
        mItemClickListener = l;
    }

    @Override
    public int getCount() {
        return mSessions.size();
    }

    @Override
    public Session getItem(int position) {
        return mSessions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {

        //Relying on the authorizations layout for items

        View v = convertView;

        if (v == null) {
            v = LayoutInflater.from(context)
                    .inflate(R.layout.demo_authorizations_item, parent, false);
        }

        Session s = getItem(position);

        //fetch an application's icon via its url with Application.getAppIcon()

        TextView name = v.findViewById(R.id.demo_authorizations_item_name);
        name.setText(s.getName());

        //TODO: Update usage of ID as placeholder for potential context being sent
        TextView context = v.findViewById(R.id.demo_authorizations_item_context);
        context.setText(s.getId());

        long millisAgo = System.currentTimeMillis() - s.getCreatedAtMillis();

        TextView action = v.findViewById(R.id.demo_authorizations_item_text_action);
        action.setText(String.format(Locale.getDefault(), "%d seconds ago", (millisAgo / 1000)));

        Button button = v.findViewById(R.id.demo_authorizations_item_button);
        button.setText("LOG OUT");
        button.setOnClickListener(mInternalClickListener);
        button.setTag(position);

        return v;
    }
}
