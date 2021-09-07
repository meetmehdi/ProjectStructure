package com.android.framework.mvvm.data.api

import android.util.Log
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class CustomOkHttpClient {
    private val timeOut = 3

    fun createOkHttpClient(): OkHttpClient {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        return OkHttpClient.Builder()
            .addInterceptor(
                LoggingInterceptor()

            )
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .retryOnConnectionFailure(true)
            .readTimeout(timeOut.toLong(), TimeUnit.MINUTES)
            .writeTimeout(timeOut.toLong(), TimeUnit.MINUTES)
            .connectTimeout(timeOut.toLong(), TimeUnit.MINUTES)
            .build()
    }

    private class LoggingInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val t1 = System.nanoTime()

            var requestLog = String.format(
                "Sending request %s on %s%n%s",
                request.url,
                chain.connection(),
                request.headers
            )

            if (request.method.compareTo("post", ignoreCase = true) == 0) {
                requestLog = "\n" + requestLog + "\n" + bodyToString(request)
            }

            Log.i("Retrofit", "request\n$requestLog");

            val response = chain.proceed(request)

            val t2 = System.nanoTime()
            val responseLog = String.format(
                "Received response for %s in %.1fms%n%s",
                response.request.url,
                (t2 - t1) / 1e6,
                response.headers
            )
            val bodyString = response.body!!.string()
            Log.i("Retrofit", "response\n$responseLog\n$bodyString");
            return response.newBuilder()
                .body(ResponseBody.create(response.body!!.contentType(), bodyString)).build()
        }

        private fun bodyToString(request: Request): String {
            return try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                copy.body!!.writeTo(buffer)
                buffer.readUtf8()
            } catch (e: IOException) {
                "did not work"
            }
        }
    }
}