package com.android.framework.mvvm.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.android.framework.mvvm.BR

data class LoginModel (

    private var _email: String = "",
    private var _password: String = ""

) : BaseObservable() {


    var email: String
        @Bindable get() = _email
        set(value) {
            _email = value
            notifyPropertyChanged(BR.email)
        }

    var password: String
        @Bindable get() = _password
        set(value) {
            _password = value
            notifyPropertyChanged(BR.password)
        }

//    constructor():this("","")

}

