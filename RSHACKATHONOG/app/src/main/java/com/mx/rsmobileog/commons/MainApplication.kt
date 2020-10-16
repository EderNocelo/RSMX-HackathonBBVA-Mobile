package com.mx.rsmobileog.commons

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mx.rsmobileog.tools.UnsafeOkHttpClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainApplication : Application() {

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
}