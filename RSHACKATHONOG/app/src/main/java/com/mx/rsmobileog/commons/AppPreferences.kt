package com.mx.rsmobileog.commons

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    private const val MAILBOX_APP = "RSMX_HACKATHON_APP"
    private const val MODE = Context.MODE_PRIVATE

    private lateinit var preferences: SharedPreferences

    //private val COLORS_RESOURCES = "COLORS_RESOURCES"

    //private var json: String = ""

    fun init(context: Context) {
        preferences = context.getSharedPreferences(MAILBOX_APP,MODE)
    }

    /*var colorResources: ResourcesApi?
        get() {
            json = preferences.getString(COLORS_RESOURCES, null).toString()

            return if (json.isNotEmpty()){
                MainApplication.getGSONInstance().fromJson(json, ResourcesApi::class.java)
            }else{
                null
            }
        }
        set(value) {
            value?.let {
                json = MainApplication.getGSONInstance().toJson(value)

                preferences
                    .edit()
                    .putString(COLORS_RESOURCES, json)
                    .apply()
            }
        }*/
}