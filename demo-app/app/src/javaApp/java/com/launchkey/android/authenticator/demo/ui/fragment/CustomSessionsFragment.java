package com.launchkey.android.authenticator.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.ui.adapter.DemoSessionsAdapter;
import com.launchkey.android.authenticator.demo.util.Utils;
import com.launchkey.android.authenticator.sdk.error.BaseError;
import com.launchkey.android.authenticator.sdk.session.Session;
import com.launchkey.android.authenticator.sdk.session.SessionManager;
import com.launchkey.android.authenticator.sdk.session.event.EndAllSessionsEventCallback;
import com.launchkey.android.authenticator.sdk.session.event.EndSessionEventCallback;
import com.launchkey.android.authenticator.sdk.session.event.GetSessionsEventCallback;

import java.util.ArrayList;
import java.util.List;

public class CustomSessionsFragment extends BaseDemoFragment implements AdapterView.OnItemClickListener {

    private final SessionManager sessionManager = SessionManager.getInstance();
    private List<Session> mSessions = new ArrayList<>();
    private ListView mList;
    private final EndSessionEventCallback endSessionEventCallback = new EndSessionEventCallback() {
        @Override
        public void onEventResult(boolean successful, BaseError error, Session session) {
            if (successful) {
                refresh();
                Utils.simpleSnackbar(mList, "Session \"" + session.getName() + "\" ended");
            } else {
                Utils.simpleSnackbarForBaseError(mList, error);
            }
        }
    };
    private final EndAllSessionsEventCallback endAllSessionsEventCallback = new EndAllSessionsEventCallback() {
        @Override
        public void onEventResult(boolean successful, BaseError error, Object o) {
            if (successful) {
                refresh();
                Utils.simpleSnackbar(mList, "All sessions ended", Snackbar.LENGTH_SHORT);
            } else {
                Utils.simpleSnackbarForBaseError(mList, error);
            }
        }
    };
    private DemoSessionsAdapter adapter;
    private final GetSessionsEventCallback getSessionsEventCallback = new GetSessionsEventCallback() {
        @Override
        public void onEventResult(boolean successful, BaseError error, List<Session> sessions) {
            if (successful) {
                mSessions.clear();
                mSessions.addAll(sessions);
                adapter.notifyDataSetChanged();
            } else {
                Utils.simpleSnackbarForBaseError(mList, error);
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.demo_fragment_sessions, container, false);
        return onLayoutPreprocessing(root);
    }

    private View onLayoutPreprocessing(View root) {

        adapter = new DemoSessionsAdapter(getActivity(), mSessions, this);

        mList = root.findViewById(R.id.demo_fragment_sessions_list);
        mList.setAdapter(adapter);

        final Button clearAll = root.findViewById(R.id.demo_fragment_sessions_button);
        clearAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sessionManager.endAllSessions(null);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        sessionManager.registerForEvents(getSessionsEventCallback, endSessionEventCallback, endAllSessionsEventCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        sessionManager.unregisterForEvents(getSessionsEventCallback, endSessionEventCallback, endAllSessionsEventCallback);
    }

    private void refresh() {
        sessionManager.getSessions();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Session session = adapter.getItem(position);
        sessionManager.endSession(session, null);
    }
}
