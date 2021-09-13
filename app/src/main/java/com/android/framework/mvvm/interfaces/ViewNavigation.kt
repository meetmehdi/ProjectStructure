package com.android.framework.mvvm.interfaces

import android.content.Intent

interface ViewNavigation {
    fun startActivityForResult(intent: Intent?, requestCode: Int)
    fun startActivity(intent: Intent?)
}