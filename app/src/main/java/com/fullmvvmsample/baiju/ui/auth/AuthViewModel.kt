package com.fullmvvmsample.baiju.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.fullmvvmsample.baiju.data.repository.UserRepository
import com.fullmvvmsample.baiju.util.ApiException
import com.fullmvvmsample.baiju.util.MyCoroutines
import com.fullmvvmsample.baiju.util.NoInternetException

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    var email: String? = null
    var password: String? = null
    var authListener: IAuthListener? = null
    var name: String? = null
    var confirmPassword: String? = null

    fun getLoggedInUser() = userRepository.getUser()

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
                    userRepository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(loginResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }

        }
    }

    fun onSignUp(view: View) { // Go to another activity
        Intent(view.context, SignUpActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onLogin(view: View) { // Go to another activity
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignupButtonClick(view: View) {
        authListener?.onStarted()

        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Name is required")
            return
        }
        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email is required")
            return
        }
        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Password is empty")
            return
        }
        if (confirmPassword.isNullOrEmpty()) {
            authListener?.onFailure("Confirm password is empty")
            return
        }
        if (password != confirmPassword) {
            authListener?.onFailure("Password mismatch")
            return
        }

        MyCoroutines.main {

            try {
                val loginResponse = userRepository.userSignUp(name!!, email!!, password!!)

                loginResponse.user?.let {
                    authListener?.onSuccess(it)
                    userRepository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(loginResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }

        }
    }

}