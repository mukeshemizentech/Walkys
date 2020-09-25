package com.walky.common.ui.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.walky.walkys.activities.SignUpActivity
import com.walky.walkys.activities.UserHome
import com.walky.data.netework.responses.Data
import com.walky.utils.Utils
import com.walky.utils.hide
import com.walky.utils.show
import com.walky.utils.toast
import com.walky.walkys.R
import com.walky.walkys.databinding.ActivityLoginBinding
import com.walky.walkys.ui.login.AuthListener
import com.walky.walkys.ui.login.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {
    lateinit var binding: ActivityLoginBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusColor(this, resourceColor = R.color.white)
        Utils.changeStatusTextColor(this)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.viewModel = viewModel
        viewModel.authListener = this


        binding.signUpTv.append(
            Utils.getColoredString(
                "Sign Up",
                resources.getColor(R.color.colorPrimary)
            )
        )
//        binding.loginBtn.setOnClickListener { view ->
//            startActivity(
//                Intent(
//                    this@LoginActivity,
//                    UserHome::class.java
//                )
//            )
//        }
        binding.signUpTv.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    SignUpActivity::class.java
                )
            )
        }
    }

    override fun onStarted() {
        progressBar.show()
    }

    override fun onSuccess(loginResponse: Data) {
        progressBar.hide()
        toast("${loginResponse.user_info?.full_name} is logged In")
        startActivity(
            Intent(
                this@LoginActivity,
                UserHome::class.java
            )
        )
    }

    override fun onFailure(message: String) {
        progressBar.hide()
        toast(message)
    }
}