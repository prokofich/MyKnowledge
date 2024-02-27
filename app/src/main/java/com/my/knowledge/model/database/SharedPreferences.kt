package com.my.knowledge.model.database

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.my.knowledge.model.constant.USER_ID
import com.my.knowledge.model.constant.USER_TYPE

class SharedPreferences(context: Context) {

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

    // функция проверки типа пользователя при последней сессии
    fun getTypeUserInLastSession():String{
        return sharedPreferences.getString(USER_TYPE,"").toString()
    }

    // функция установки типа пользователя в последней сессии
    fun setTypeUserInLastSession(userType:String){
        sharedPreferences.edit()
            .putString(USER_TYPE,userType)
            .apply()
    }

}