package com.walky.walkys.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.walky.utils.Utils
import com.walky.walkys.R
import com.walky.walkys.databinding.ActivitySignUp2Binding

class SignUpActivity2 : AppCompatActivity() {
    lateinit var binding : ActivitySignUp2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusColor(this, R.color.white)
        Utils.changeStatusTextColor(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up2)
        binding.backIv.setOnClickListener { view -> onBackPressed() }

        binding.signUpBtn.setOnClickListener { view ->
            startActivity(
                Intent(
                    this@SignUpActivity2,
                    UserHome::class.java
                )
            )
        }
    }
}