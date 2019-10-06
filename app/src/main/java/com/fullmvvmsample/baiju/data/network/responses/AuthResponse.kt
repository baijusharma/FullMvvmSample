package com.fullmvvmsample.baiju.data.network.responses

import com.fullmvvmsample.baiju.data.db.entities.User

// JSON obj
data class AuthResponse(val isSuccessful: Boolean?, val message: String?, val user: User?) {
}