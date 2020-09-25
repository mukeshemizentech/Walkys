package com.walky.walkys.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.walky.utils.Utils
import com.walky.walkys.R
import com.walky.walkys.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Utils.changeStatusColor(this, R.color.white)
        Utils.changeStatusTextColor(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        binding.alreadyTv.append(
            Utils.getColoredString(
                "Sign In",
                resources.getColor(R.color.appThemeColor)
            )
        )
        binding.backIv.setOnClickListener { onBackPressed() }

        binding.signUpBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@SignUpActivity,
                    UserHome::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
        binding.alreadyTv.setOnClickListener { finish() }
    }
}