package com.fullmvvmsample.baiju.ui.profile

import androidx.lifecycle.ViewModel
import com.fullmvvmsample.baiju.data.repository.UserRepository

class ProfileViewModel(userRepository: UserRepository) : ViewModel() {

    val user = userRepository.getUser()
}
