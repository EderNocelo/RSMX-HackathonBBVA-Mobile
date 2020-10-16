package com.mx.rsmobileog.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mx.rsmobileog.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // load layout
        setContentView(R.layout.splash_activity)
        // hide title bar
        supportActionBar!!.hide()
    }

    private fun openMainActivity() {
        OwnerActivity.start(this)
        finish()
    }
}