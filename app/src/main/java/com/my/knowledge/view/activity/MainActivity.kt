package com.my.knowledge.view.activity

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.R
import com.my.knowledge.databinding.ActivityMainBinding
import com.my.knowledge.model.network.InterfaceNetworkBroadcastReceiver
import com.my.knowledge.model.network.NetworkBroadcastReceiver

class MainActivity : AppCompatActivity(),InterfaceNetworkBroadcastReceiver {

    var navController:NavController? = null
    private var stateNetwork:Boolean? = null
    private var networkBroadcast: NetworkBroadcastReceiver? = null
    private var binding:ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        MAIN = this

        networkBroadcast = NetworkBroadcastReceiver(this)

        //установка полноэкранного режима
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // установка контроллера для навигации
        navController = Navigation.findNavController(this, R.id.id_nav_host)

    }

    // функция регистрации ресивера
    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkBroadcast,filter)
    }


    // функция отвязки ресивера
    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkBroadcast)
    }

    // функция закрытия приложения
    fun closeApplication(){
        this.finishAffinity()
    }

    // функция получения состояния сети
    fun getStateNetwork(): Boolean? {
        return stateNetwork
    }

    // функция отслеживания состояния сети
    override fun changeNetworkState(flag: Boolean) {
        stateNetwork = flag
    }

}