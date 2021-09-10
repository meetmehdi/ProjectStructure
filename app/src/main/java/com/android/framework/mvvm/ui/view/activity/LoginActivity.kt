package com.android.framework.mvvm.ui.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.framework.mvvm.R
import com.android.framework.mvvm.data.model.LoginModel
import com.android.framework.mvvm.databinding.ActivityLoginBinding
import com.android.framework.mvvm.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

    private val viewModel:LoginViewModel by viewModels()
    private lateinit var loginBinding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        loginBinding.viewModel = viewModel
        loginBinding.loginModel = LoginModel()
        loginBinding.lifecycleOwner = this

    }
}