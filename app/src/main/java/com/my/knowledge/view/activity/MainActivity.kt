package com.my.knowledge.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.R

class MainActivity : AppCompatActivity() {

    var navController:NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MAIN = this

        //установка полноэкранного режима
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        navController = Navigation.findNavController(this, R.id.id_nav_host)

    }

    // функция закрытия приложения
    fun closeApplication(){
        this.finishAffinity()
    }



}