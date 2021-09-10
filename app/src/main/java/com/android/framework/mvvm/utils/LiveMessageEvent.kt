package com.android.framework.mvvm.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class LiveMessageEvent<T> : SingleLiveEvent<(T.() -> Unit)?>() {

    fun setEventReceiver(owner: LifecycleOwner, receiver: T) {
        observe(owner, { event ->
            if ( event != null ) {
                receiver.event()
            }
        })
    }

    fun sendEvent(event: (T.() -> Unit)?) {
        value = event
    }
}