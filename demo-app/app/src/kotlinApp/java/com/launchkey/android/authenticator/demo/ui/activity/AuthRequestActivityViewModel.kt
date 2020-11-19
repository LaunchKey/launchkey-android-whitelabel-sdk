package com.launchkey.android.authenticator.demo.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.launchkey.android.authenticator.sdk.core.auth_request_management.AuthRequest
import com.launchkey.android.authenticator.sdk.core.auth_request_management.AuthRequestManager
import com.launchkey.android.authenticator.sdk.core.auth_request_management.AuthRequestResponse
import com.launchkey.android.authenticator.sdk.core.auth_request_management.event_callback.AuthRequestResponseEvent
import com.launchkey.android.authenticator.sdk.core.auth_request_management.event_callback.GetAuthRequestEventCallback
import com.launchkey.android.authenticator.sdk.core.util.CompositeDisposable
import com.launchkey.android.authenticator.sdk.core.util.Disposable
import java.util.concurrent.Executor

class AuthRequestActivityViewModel(private val authRequestManager: AuthRequestManager,
                                   private val executor: Executor,
                                   savedStateHandle: SavedStateHandle?) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    var currentAuthRequest: AuthRequest? = null
    private val state = MutableLiveData<State>()
    private val getAuthRequestEventCallback: GetAuthRequestEventCallback = object : GetAuthRequestEventCallback() {
        override fun onSuccess(authRequest: AuthRequest?) {
            if (authRequest != null && currentAuthRequest != null && authRequest.id == currentAuthRequest!!.id) {
                state.value = State.Success(currentAuthRequest)
                return
            }
            currentAuthRequest = authRequest
            state.value = State.Success(currentAuthRequest)
        }

        override fun onFailure(e: Exception) {
            state.value = State.Failed(e)
        }
    }
    private val authRequestResponseEvent: AuthRequestResponseEvent = object : AuthRequestResponseEvent() {
        override fun onSuccess(result: AuthRequestResponse) {
            currentAuthRequest = null
            state.value = State.Success(null)
        }

        override fun onFailure(e: Exception) {
            currentAuthRequest = null
            state.value = State.Failed(e)
        }
    }

    fun getState(): LiveData<State> {
        return state
    }

    override fun onCleared() {
        compositeDisposable.clear()
        authRequestManager.unregisterForEvents(getAuthRequestEventCallback, authRequestResponseEvent)
    }

    fun checkForAuthRequest() {
        executor.execute {
            val d = authRequestManager.checkForAuthRequest(null)
            state.postValue(State.Loading(d))
            compositeDisposable.add(d)
        }
    }

    abstract class State {
        class Success internal constructor(val authRequest: AuthRequest?) : State()

        class Loading internal constructor(val disposable: Disposable) : State()

        class Failed internal constructor(val exception: Exception) : State()
    }

    init {
        authRequestManager.registerForEvents(getAuthRequestEventCallback, authRequestResponseEvent)
    }
}