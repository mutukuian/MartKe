package com.example.martke.util

import android.util.Patterns

fun validateEmail(email:String):RegisterValidation{
    if (email.isEmpty())
        return RegisterValidation.Failed("Email cannot be empty!")
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("Wrong Email Format!")

    return RegisterValidation.Success
}

fun validatePassword(password:String):RegisterValidation{
    if (password.isEmpty())
        return RegisterValidation.Failed("Password Cannot be Empty!")
    if (password.length<6)
        return RegisterValidation.Failed("Password should contain 6 characters")

    return RegisterValidation.Success
}