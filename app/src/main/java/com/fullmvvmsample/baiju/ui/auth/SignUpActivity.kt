package com.fullmvvmsample.baiju.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fullmvvmsample.baiju.R
import com.fullmvvmsample.baiju.data.db.entities.User
import com.fullmvvmsample.baiju.databinding.ActivitySignupBinding
import com.fullmvvmsample.baiju.ui.home.HomeActivity
import com.fullmvvmsample.baiju.util.hide
import com.fullmvvmsample.baiju.util.show
import com.fullmvvmsample.baiju.util.snackbar
import kotlinx.android.synthetic.main.login_activity.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignUpActivity : AppCompatActivity(), IAuthListener, KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySignupBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_signup)
        val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        binding.authviewmodel = viewModel

        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
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
