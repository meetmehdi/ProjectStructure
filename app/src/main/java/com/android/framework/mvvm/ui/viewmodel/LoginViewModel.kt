package com.android.framework.mvvm.ui.viewmodel

import android.content.Intent
import android.view.View
import com.android.framework.mvvm.baseClass.BaseViewModel
import com.android.framework.mvvm.data.model.LoginModel
import com.android.framework.mvvm.ui.view.activity.MainActivity

class LoginViewModel: BaseViewModel(){

    val loginModel:LoginModel = LoginModel()

    fun onClickButtonLogin(view:View){
        if (loginModel.isValidate(view.context)){
            view.context.startActivity(Intent(
                view.context,MainActivity::class.java
            ))
        }
    }
}