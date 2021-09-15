package com.android.framework.mvvm.data.model

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.android.framework.mvvm.BR

data class LoginModel(
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

    fun isValidate(context: Context): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(_email).matches()) {
            Toast.makeText(context, "Please enter valid email", Toast.LENGTH_LONG).show()
            return false
        } else if (_password.length < 6) {
            Toast.makeText(context, "Password must be greater then 6 characters", Toast.LENGTH_LONG)
                .show()
            return false
        }
        return true
    }
}

