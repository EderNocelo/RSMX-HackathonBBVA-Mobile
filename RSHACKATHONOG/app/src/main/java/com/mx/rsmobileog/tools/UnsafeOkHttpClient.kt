package com.mx.rsmobileog.tools

import android.os.Build
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

class UnsafeOkHttpClient {
    companion object {
        fun getUnsafeOkHttpClient(): OkHttpClient? {
            return try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate?>?,
                            authType: String?
                        ) {}

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate?>?,
                            authType: String?
                        ) {}

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )

                // Install the all-trusting trust manager
                val sslContext: SSLContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier(HostnameVerifier { _, _ -> true })

                // add HTTP logs (only for API 19 and higher)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                }

                builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}