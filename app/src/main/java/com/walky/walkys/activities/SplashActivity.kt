package com.walky.walkys.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.walky.walkys.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initApp()
    }

    private fun initApp() {
        Handler().postDelayed({

//            if (Prefs.getBoolean(Constans.IsLogin, false))
//                startActivity(new Intent(Splash.this, LoginActivity.class));
//            else
            startActivity(Intent(this@SplashActivity, SignUpActivity::class.java))
            finish()

        },2500)
    }
}