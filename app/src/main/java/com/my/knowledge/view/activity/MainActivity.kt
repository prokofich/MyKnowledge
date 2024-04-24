package com.my.knowledge.view.activity

import android.app.AlertDialog
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.R
import com.my.knowledge.databinding.ActivityMainBinding
import com.my.knowledge.model.network.InterfaceNetworkBroadcastReceiver
import com.my.knowledge.model.network.NetworkBroadcastReceiver

class MainActivity : AppCompatActivity(),InterfaceNetworkBroadcastReceiver {

    var navController : NavController? = null
    private var stateNetwork : Boolean? = null
    private var alertDialog : AlertDialog? = null
    private var networkBroadcast : NetworkBroadcastReceiver? = null
    private var binding : ActivityMainBinding? = null

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

        // навигация для учителя
        binding?.bottomNavigationTeacher?.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.id_bottom_menu_teacher_profile    -> navController?.navigate(R.id.accountTeacherFragment)
                R.id.id_bottom_menu_teacher_search     -> navController?.navigate(R.id.searchUsersFragment)
                R.id.id_bottom_menu_teacher_table      -> navController?.navigate(R.id.daysWeekFragment)
                R.id.id_bottom_menu_teacher_price_list -> navController?.navigate(R.id.priceListFragment)
            }
            true
        }

        binding?.bottomNavigationStudent?.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.id_bottom_menu_student_profile    -> navController?.navigate(R.id.accountStudentFragment)
                R.id.id_bottom_menu_student_search     -> navController?.navigate(R.id.searchUsersFragment)
                R.id.id_bottom_menu_student_table      -> navController?.navigate(R.id.daysWeekFragment)
                R.id.id_bottom_menu_teacher_price_list -> navController?.navigate(R.id.priceListFragment)
            }
            true
        }

    }

    // функция показа диалога о выходе из приложения
    fun exitApplication(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("выход из приложения")
        builder.setMessage("вы точно хотите выйти?")

        builder.setPositiveButton("выйти") { _, _ ->
            this.finishAffinity()
        }

        builder.setNegativeButton("отмена") { _, _ ->
            alertDialog?.dismiss()
        }

        alertDialog = builder.create()
        alertDialog?.show()
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

    // функция показа или скрытия нижнего бара с меню для учителя
    fun showOrHideBottomNavigationForTeacher(isShow:Boolean){
        when(isShow){
            true  -> { binding?.bottomNavigationTeacher?.visibility = View.VISIBLE }
            false -> { binding?.bottomNavigationTeacher?.visibility = View.GONE }
        }
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