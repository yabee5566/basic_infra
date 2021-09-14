package com.ian_no_1.basic_infra.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        val t1 = System.nanoTime()
        val requestLog = String.format(
            "Sending request %s on %s%n%s",
            request.url(),
            chain.connection(),
            request.headers()
        )
        Log.i("LoggingInterceptor", requestLog)

        val response = chain.proceed(request)
        val t2 = System.nanoTime()
        val responseLog = String.format(
            "Received response for %s in %.1fms%n%s",
            response.request().url(), (t2 - t1) / 1e6, response.headers()
        )
        Log.i("LoggingInterceptor", responseLog)
        Log.i("LoggingInterceptor", "response body:${response.body()}")
        return response
    }
}