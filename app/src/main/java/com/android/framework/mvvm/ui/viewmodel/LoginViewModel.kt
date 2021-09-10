package com.android.framework.mvvm.ui.viewmodel

import android.content.Intent
import android.view.View
import com.android.framework.mvvm.baseClass.BaseViewModel
import com.android.framework.mvvm.data.model.LoginModel
import com.android.framework.mvvm.ui.view.activity.LoginActivity
import com.android.framework.mvvm.ui.view.activity.MainActivity
import com.app.imagepickerlibrary.ImagePickerActivityClass
import com.app.imagepickerlibrary.ImagePickerBottomsheet
import com.app.imagepickerlibrary.bottomSheetActionFragment

class LoginViewModel: BaseViewModel(){

    val loginModel:LoginModel = LoginModel()
    lateinit var imagePicker: ImagePickerActivityClass

    fun onClickButtonLogin(view:View){
        if (loginModel.isValidate(view.context)){
            view.context.startActivity(Intent(
                view.context,MainActivity::class.java
            ))
        }
    }

    fun onClickImagePicker(view:View){
        val fragment = ImagePickerBottomsheet()
        fragment.show((view.context as LoginActivity).supportFragmentManager, bottomSheetActionFragment)
    }
}