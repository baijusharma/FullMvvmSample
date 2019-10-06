package com.fullmvvmsample.baiju.ui.auth

import androidx.lifecycle.LiveData
import com.fullmvvmsample.baiju.data.db.entities.User

interface IAuthListener {

    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}