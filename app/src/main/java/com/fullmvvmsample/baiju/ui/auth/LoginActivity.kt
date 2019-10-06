package com.fullmvvmsample.baiju.ui.auth

//Simplified coding example
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fullmvvmsample.baiju.R
import com.fullmvvmsample.baiju.data.db.AppDatabase
import com.fullmvvmsample.baiju.data.db.entities.User
import com.fullmvvmsample.baiju.data.network.MyApi
import com.fullmvvmsample.baiju.data.repository.UserRepository
import com.fullmvvmsample.baiju.databinding.LoginActivityBinding
import com.fullmvvmsample.baiju.util.hide
import com.fullmvvmsample.baiju.util.show
import com.fullmvvmsample.baiju.util.snackbar
import com.fullmvvmsample.baiju.util.toast
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : AppCompatActivity(), IAuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = MyApi()
        val db = AppDatabase(this)
        val repository = UserRepository(api, db)

        val binding: LoginActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.login_activity)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(AuthViewModel::class.java)

        binding.authviewmodel = viewModel

        viewModel.authListener = this
    }

    override fun onStarted() {
        progress_bar.show() // extension function
    }

    override fun onSuccess(user: User) {
        root_layout.snackbar("Is logged In ${user.name}")
        progress_bar.hide()
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

}
