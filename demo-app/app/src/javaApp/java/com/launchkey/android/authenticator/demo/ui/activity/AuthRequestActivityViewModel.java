package com.launchkey.android.authenticator.demo.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.launchkey.android.authenticator.sdk.core.auth_request_management.AuthRequest;
import com.launchkey.android.authenticator.sdk.core.auth_request_management.AuthRequestManager;
import com.launchkey.android.authenticator.sdk.core.auth_request_management.AuthRequestResponse;
import com.launchkey.android.authenticator.sdk.core.auth_request_management.event_callback.AuthRequestResponseEvent;
import com.launchkey.android.authenticator.sdk.core.auth_request_management.event_callback.GetAuthRequestEventCallback;
import com.launchkey.android.authenticator.sdk.core.util.CompositeDisposable;
import com.launchkey.android.authenticator.sdk.core.util.Disposable;

import java.util.concurrent.Executor;

public class AuthRequestActivityViewModel extends ViewModel {

    private final AuthRequestManager authRequestManager;
    private final Executor executor;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public AuthRequest currentAuthRequest = null;
    private MutableLiveData<State> state = new MutableLiveData<>();

    private final GetAuthRequestEventCallback getAuthRequestEventCallback = new GetAuthRequestEventCallback() {
        @Override
        public void onSuccess(@Nullable final AuthRequest authRequest) {
            if (authRequest != null && currentAuthRequest != null && authRequest.getId().equals(currentAuthRequest.getId())) {
                state.setValue(new State.Success(currentAuthRequest));
                return;
            }

            currentAuthRequest = authRequest;
            state.setValue(new State.Success(currentAuthRequest));
        }

        @Override
        public void onFailure(@NonNull final Exception e) {
            state.setValue(new State.Failed(e));
        }
    };
    private final AuthRequestResponseEvent authRequestResponseEvent = new AuthRequestResponseEvent() {
        @Override
        public void onSuccess(@NonNull final AuthRequestResponse result) {
            currentAuthRequest = null;
            state.setValue(new State.Success(null));
        }

        @Override
        public void onFailure(@NonNull final Exception e) {
            currentAuthRequest = null;
            state.setValue(new State.Failed(e));
        }
    };

    public AuthRequestActivityViewModel(@NonNull final AuthRequestManager authRequestManager,
                                        @NonNull final Executor executor,
                                        @Nullable final SavedStateHandle savedStateHandle) {
        this.authRequestManager = authRequestManager;
        this.executor = executor;

        this.authRequestManager.registerForEvents(getAuthRequestEventCallback, authRequestResponseEvent);
    }

    public LiveData<State> getState() {
        return state;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        authRequestManager.unregisterForEvents(getAuthRequestEventCallback, authRequestResponseEvent);
    }

    public void checkForAuthRequest() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final Disposable d = authRequestManager.checkForAuthRequest(null);
                state.postValue(new State.Loading(d));
                compositeDisposable.add(d);
            }
        });
    }

    public static abstract class State {
        public static class Success extends State {
            @Nullable
            private final AuthRequest authRequest;
            Success(@Nullable final AuthRequest authRequest) {
                this.authRequest = authRequest;
            }

            @Nullable
            public AuthRequest getAuthRequest() {
                return authRequest;
            }
        }

        public static class Loading extends State {
            @NonNull
            private final Disposable disposable;
            Loading(@NonNull final Disposable disposable) {
                this.disposable = disposable;
            }

            @NonNull
            public Disposable getDisposable() {
                return disposable;
            }
        }

        public static class Failed extends State {
            @NonNull
            private final Exception exception;
            Failed(@NonNull final Exception exception) {
                this.exception = exception;
            }

            @NonNull
            public Exception getException() {
                return exception;
            }
        }
    }
}
