package com.my.knowledge.model.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class NetworkBroadcastReceiver(private val interfaceNetworkBroadcastReceiver: InterfaceNetworkBroadcastReceiver):BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val isConnected = checkInternet(context)
            interfaceNetworkBroadcastReceiver.changeNetworkState(isConnected)
        }

    }

    private fun checkInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected ?: false
    }


}