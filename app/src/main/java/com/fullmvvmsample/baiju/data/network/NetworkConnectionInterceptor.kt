package com.fullmvvmsample.baiju.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.fullmvvmsample.baiju.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response


class NetworkConnectionInterceptor(context: Context) : Interceptor {


    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {

        if(!isInternetAvailable())
            throw NoInternetException("Make sure you have an active Internet")

        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager // as keyword to typecast

        connectivityManager.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    else -> false

                }
            }
        }
        return result
    }
}