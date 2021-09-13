package com.android.framework.mvvm

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Provider

@HiltAndroidApp
class MyApplication : MultiDexApplication(){

}