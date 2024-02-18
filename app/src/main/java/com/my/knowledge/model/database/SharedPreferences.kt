package com.my.knowledge.model.database

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.my.knowledge.model.constant.USER_ID

class SharedPreferences(private val context: Context) {

    private var sharedPreferences:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    // функция сохранения ID пользователя
    fun saveUserId(userId:String){
        sharedPreferences.edit()
            .putString(USER_ID,userId)
            .apply()
    }

    // функция получения ID пользователя
    fun getUserId():String{
        return sharedPreferences.getString(USER_ID,"").toString()
    }



}