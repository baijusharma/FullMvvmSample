package com.fullmvvmsample.baiju.ui.auth

//Simplified coding example
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fullmvvmsample.baiju.R
import com.fullmvvmsample.baiju.data.db.entities.User
import com.fullmvvmsample.baiju.databinding.LoginActivityBinding
import com.fullmvvmsample.baiju.ui.home.HomeActivity
import com.fullmvvmsample.baiju.util.hide
import com.fullmvvmsample.baiju.util.show
import com.fullmvvmsample.baiju.util.snackbar
import kotlinx.android.synthetic.main.login_activity.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class LoginActivity : AppCompatActivity(), IAuthListener, KodeinAware {

    override val kodein by kodein() //dependency

    private val factory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*  val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
          val api = MyApi(networkConnectionInterceptor) // creating instance of Api
          val db = AppDatabase(this) // creating instance of DB
          val repository = UserRepository(api, db) // creating instance of Repo
          val factory = AuthViewModelFactory(repository)
  */
        val binding: LoginActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.login_activity)

        val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        binding.authviewmodel = viewModel

        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
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
