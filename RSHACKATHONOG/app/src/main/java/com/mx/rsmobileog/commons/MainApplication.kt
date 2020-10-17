package com.mx.rsmobileog.commons

import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.multidex.MultiDex
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mx.rsmobileog.R
import com.mx.rsmobileog.tools.UnsafeOkHttpClient
import okhttp3.OkHttpClient
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainApplication : Application(), Application.ActivityLifecycleCallbacks{

    private var activityReferences = 0
    private var isActivityChangingConfigurations = false

    companion object {

        var GSON: Gson? = null
        var RETROFIT: Retrofit? = null
        private var okHttpClient: OkHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()!!

        private fun createRetrofitBase(): Retrofit = Retrofit.Builder()
            .baseUrl(Config.SERVER_API)
            .addConverterFactory(GsonConverterFactory.create(getGSONInstance()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        fun getRetrofitInstance(): Retrofit {
            if (RETROFIT == null) {
                RETROFIT = createRetrofitBase()
            }
            return RETROFIT!!
        }

        fun getGSONInstance(): Gson {
            if (GSON == null) {
                GSON = GsonBuilder().setLenient().create()
            }
            return GSON!!
        }

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        // Init SharedPreferences
        AppPreferences.init(this)
        // Init HTTP image
        val configFresco = ImagePipelineConfig
            .newBuilder(this)
            .setDownsampleEnabled(true)
            .build()
        Fresco.initialize(this, configFresco)
        Fresco.getImagePipeline().clearCaches()
    }

    // starts and ends notification simulator :)
    override fun onActivityStarted(p0: Activity) {
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            Log.d("APP_RSMX", "APP_FOREGROUND")

        }
    }

    override fun onActivityStopped(p0: Activity) {
        isActivityChangingConfigurations = p0.isChangingConfigurations
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
            Log.d("APP_RSMX", "APP_BACKGROUND")
        }
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {}
    override fun onActivityResumed(p0: Activity) {}
    override fun onActivityDestroyed(p0: Activity) {}
    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {}
    override fun onActivityPaused(p0: Activity) {}
}