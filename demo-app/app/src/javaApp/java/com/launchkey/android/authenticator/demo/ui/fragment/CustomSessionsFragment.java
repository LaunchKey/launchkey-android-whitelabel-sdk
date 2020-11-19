package com.launchkey.android.authenticator.demo.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;
import com.launchkey.android.authenticator.demo.R;
import com.launchkey.android.authenticator.demo.ui.adapter.DemoSessionsAdapter;
import com.launchkey.android.authenticator.demo.util.Utils;
import com.launchkey.android.authenticator.sdk.core.authentication_management.AuthenticatorManager;
import com.launchkey.android.authenticator.sdk.core.authentication_management.Session;
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.EndAllSessionsEventCallback;
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.EndSessionEventCallback;
import com.launchkey.android.authenticator.sdk.core.authentication_management.event_callback.GetSessionsEventCallback;
import com.launchkey.android.authenticator.sdk.core.util.CompositeDisposable;

import java.util.ArrayList;
import java.util.List;

public class CustomSessionsFragment extends BaseDemoFragment implements AdapterView.OnItemClickListener {

    private final @NonNull CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final @NonNull AuthenticatorManager sessionManager = AuthenticatorManager.instance;
    private List<Session> mSessions = new ArrayList<>();
    private ListView mList;
    private final EndSessionEventCallback endSessionEventCallback = new EndSessionEventCallback() {
        @Override
        public void onSuccess(final Session session) {
            refresh();
            Utils.simpleSnackbar(mList, "Session \"" + session.getName() + "\" ended");
        }

        @Override
        public void onFailure(final @NonNull Exception e) {
            Utils.simpleSnackbarForBaseError(mList, e);
        }
    };
    private DemoSessionsAdapter adapter;
    private final GetSessionsEventCallback getSessionsEventCallback = new GetSessionsEventCallback() {
        @Override
        public void onSuccess(@NonNull List<Session> sessions) {
            mSessions.clear();
            mSessions.addAll(sessions);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(@NonNull Exception e) {
            Utils.simpleSnackbarForBaseError(mList, e);
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
                sessionManager.endAllSessions(new EndAllSessionsEventCallback() {
                    @Override
                    public void onSuccess(final Void result) {
                        refresh();
                        Utils.simpleSnackbar(mList, "All sessions ended", Snackbar.LENGTH_SHORT);
                    }

                    @Override
                    public void onFailure(final @NonNull Exception e) {
                        Utils.simpleSnackbarForBaseError(mList, e);
                    }
                });
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
    public void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }

    private void refresh() {
        compositeDisposable.add(sessionManager.getSessions(getSessionsEventCallback));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Session session = adapter.getItem(position);
        compositeDisposable.add(sessionManager.endSession(session, endSessionEventCallback));
    }
}
