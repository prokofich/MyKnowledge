package com.my.knowledge.model.database.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.my.knowledge.model.constant.COUNT_LESSONS_IN_FRIDAY
import com.my.knowledge.model.constant.COUNT_LESSONS_IN_MONDAY
import com.my.knowledge.model.constant.COUNT_LESSONS_IN_SATURDAY
import com.my.knowledge.model.constant.COUNT_LESSONS_IN_SUNDAY
import com.my.knowledge.model.constant.COUNT_LESSONS_IN_THURSDAY
import com.my.knowledge.model.constant.COUNT_LESSONS_IN_TUESDAY
import com.my.knowledge.model.constant.COUNT_LESSONS_IN_WEDNESDAY
import com.my.knowledge.model.constant.DESCRIPTION_TEACHER
import com.my.knowledge.model.constant.EDUCATION_TEACHER
import com.my.knowledge.model.constant.EXPERIENCE_TEACHER
import com.my.knowledge.model.constant.FIRST_NAME_TEACHER
import com.my.knowledge.model.constant.LAST_NAME_TEACHER
import com.my.knowledge.model.constant.USER_ID
import com.my.knowledge.model.constant.USER_TYPE

class SharedPreferences(context: Context) {

    private var sharedPreferences:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

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