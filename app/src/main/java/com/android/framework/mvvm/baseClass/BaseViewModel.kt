package com.android.framework.mvvm.baseClass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        throw RuntimeException(exception.message.toString())
    }

    fun launchMain(work: suspend (() -> Unit)) =
        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            work()
        }

    fun launchIO(work: suspend (() -> Unit)) =
        viewModelScope.launch((Dispatchers.IO + exceptionHandler)) {
            work()
        }
}