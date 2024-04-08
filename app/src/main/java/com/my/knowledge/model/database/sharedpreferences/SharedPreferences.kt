package com.my.knowledge.model.database.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.my.knowledge.model.constant.URL_PHOTO_FROM_PROFILE
import com.my.knowledge.model.constant.USER_ID
import com.my.knowledge.model.constant.USER_TYPE

class SharedPreferences(context: Context) {

    private var sharedPreferences:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    // функция получения url адреса аватарки
    fun getUrlPhotoFromProfile() : String {
        return sharedPreferences.getString(URL_PHOTO_FROM_PROFILE,"").toString()
    }

    // функция установки url адреса для аватарки
    fun setUrlPhotoFromProfile(url:String) {
        sharedPreferences.edit().putString(URL_PHOTO_FROM_PROFILE,url).apply()
    }

    // функция сохранения ID пользователя
    fun saveUserId(userId:String){
        sharedPreferences.edit().putString(USER_ID,userId).apply()
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
        sharedPreferences.edit().putString(USER_TYPE,userType).apply()
    }

}