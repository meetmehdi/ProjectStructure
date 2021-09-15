package com.android.framework.mvvm.bindingAdapter

import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import androidx.databinding.BindingAdapter

object DataBindingAdapter {
    @JvmStatic
    @BindingAdapter("passwordValidator")
    fun passwordValidator(editText: EditText, password: String?) {
        // ignore infinite loops
        val minimumLength = 6
        if (TextUtils.isEmpty(password)) {
            editText.error = null
            return
        }
        if (editText.text.toString().length < minimumLength) {
            editText.error = "Password must be minimum $minimumLength length"
        } else editText.error = null
    }

    @JvmStatic
    @BindingAdapter("emailValidation")
    fun isValidEmail(editText: EditText, email: String?) {

        if (TextUtils.isEmpty(email)) {
            editText.error = null
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText.error = "Please enter a valid email"
            return
        } else {
            editText.error = null
            return
        }
    }

}