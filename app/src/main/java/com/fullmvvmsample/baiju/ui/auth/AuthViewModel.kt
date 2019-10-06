package com.fullmvvmsample.baiju.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.fullmvvmsample.baiju.data.repository.UserRepository
import com.fullmvvmsample.baiju.util.ApiException
import com.fullmvvmsample.baiju.util.MyCoroutines

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    var email: String? = null
    var password: String? = null
    var authListener: IAuthListener? = null

    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        MyCoroutines.main {

            try {
                val loginResponse = userRepository.userLogin(email!!, password!!)

                loginResponse.user?.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                authListener?.onFailure(loginResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            }


        }
    }

    fun onSignup(view: View) {}

}