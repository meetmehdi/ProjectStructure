package com.android.framework.mvvm.ui.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.framework.mvvm.R
import com.android.framework.mvvm.data.repository.db.DbHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}