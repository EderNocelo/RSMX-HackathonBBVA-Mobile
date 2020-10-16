package com.mx.rsmobileog.tools

import android.content.Context
import android.widget.Toast
import com.mx.rsmobileog.BuildConfig

class Tools {

    companion object {
        /**
         * Get current version name
         * @return String
         * */
        fun getCurrentVersionName(): String {
            return "v ${BuildConfig.VERSION_NAME}"
        }

        /**
         * Get current version id
         * @return String
         * */
        fun getCurrentAppID(): String {
            return BuildConfig.APPLICATION_ID
        }

        /**
         * Displays toast
         * @return String
         * */
        fun displayToast(
            context: Context?,
            message: String?
        ) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

    }
}