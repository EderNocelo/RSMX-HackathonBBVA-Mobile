package com.mx.rsmobileog.ui.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mx.rsmobileog.R
import com.mx.rsmobileog.tools.Tools
import kotlin.system.exitProcess

class OwnerActivity : AppCompatActivity() {
    // Permissions codes
    private val LOCATION_REQUEST_CODE = 101
    private val WRITE_REQUEST_CODE = 102

    /**
     * Instantiate activity
     * **/
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, OwnerActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // load layout
        setContentView(R.layout.owner_activity)
        // hide title bar
        supportActionBar!!.hide()

        addActions()
    }

    override fun onResume() {
        super.onResume()
    }

    /** OPEN APP ABOUT AND MULTIMEDIA SETTINGS DIALOG **/
    private fun addActions() {
        opensAboutApp()
    }

    private fun opensAboutApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission()
        } else {
            //displaysAbout()
        }
    }

    private fun checkLocationPermission() {
        val permission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            //displaysAbout()
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            LOCATION_REQUEST_CODE
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Tools.displayToast(this, "R.string.mailbox_location_permissions_fail")
                } else {
                    // displaysAbout()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            WRITE_REQUEST_CODE -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Check if permissions are granted
                    if (!Settings.System.canWrite(this)) {
                        Tools.displayToast(this, "")
                    } else {
                        // displaysMediaSettings()
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        exitFromApp()
    }

    fun exitFromApp(){
        moveTaskToBack(true)
        exitProcess(-1)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        exitFromApp()
    }
}