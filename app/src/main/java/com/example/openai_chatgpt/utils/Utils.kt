package com.example.openai_chatgpt.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object Utils {
    fun isConnected(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
            val currentNetwork = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(currentNetwork)
            (capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
        } else {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
        }
    }
}