package com.mx.rsmobileog.ui.activities

import android.Manifest
import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.mx.rsmobileog.R
import com.mx.rsmobileog.databinding.OwnerActivityBinding
import com.mx.rsmobileog.tools.LocalNotificationManager
import com.mx.rsmobileog.tools.Tools


class OwnerActivity : AppCompatActivity() {

    // binding main layout
    private lateinit var bindingView: OwnerActivityBinding

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
        // inflate and load layout
        bindingView = OwnerActivityBinding.inflate(layoutInflater)
        setContentView(bindingView.root)
        // hide title bar
        supportActionBar!!.hide()
        // add main menu
        inflateCurrentMenu()
    }

    override fun onResume() {
        super.onResume()
    }

    /**
     * This method call to inflate menu for INVENTORY
     * */
    private fun inflateCurrentMenu() {
        bindingView.bnvMainMenu.menu.clear()
        bindingView.bnvMainMenu.inflateMenu(R.menu.navigation_main)
        bindingView.bnvMainMenu.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
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
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //scheduleNotification(getNotification("Local notification", "Awesome message")!!)
    }

    private fun scheduleNotification(notification: Notification) {
        val notificationIntent = Intent(this, LocalNotificationManager::class.java)
        notificationIntent.putExtra(LocalNotificationManager.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(LocalNotificationManager.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val futureInMillis: Long = SystemClock.elapsedRealtime() + 5000
        val alarmManager =
            (getSystemService(Context.ALARM_SERVICE) as AlarmManager)
        alarmManager[AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis] = pendingIntent
    }

    private fun getNotification(title:String, message: String): Notification? {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, LocalNotificationManager.NOTIFICATION_CHANNEL_ID)
        builder.setContentTitle(title)
        builder.setContentText(message)
        builder.setSmallIcon(R.drawable.ic_near)
        builder.setAutoCancel(true)
        builder.setVibrate(longArrayOf(500, 500))
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
        builder.setChannelId(LocalNotificationManager.NOTIFICATION_CHANNEL_ID)
        return builder.build()
    }
}