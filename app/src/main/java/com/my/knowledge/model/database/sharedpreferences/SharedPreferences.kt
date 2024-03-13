package com.my.knowledge.model.database.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
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

    // функция сохранения имени учителя
    fun saveFirstNameTeacher(firstNameTeacher:String){
        sharedPreferences.edit().putString(FIRST_NAME_TEACHER,firstNameTeacher).apply()
    }

    // функция сохранения фамилии учителя
    fun saveLastNameTeacher(lastNameTeacher:String){
        sharedPreferences.edit().putString(LAST_NAME_TEACHER,lastNameTeacher).apply()
    }

    // функция сохранения описания учителя
    fun saveDescriptionTeacher(descriptionTeacher:String){
        sharedPreferences.edit().putString(DESCRIPTION_TEACHER,descriptionTeacher).apply()
    }

    // функция сохранения опыта работы учителя
    fun saveExperienceTeacher(experienceTeacher:String){
        sharedPreferences.edit().putString(EXPERIENCE_TEACHER,experienceTeacher).apply()
    }

    // функция сохранения образования учителя
    fun saveEducationTeacher(educationTeacher:String){
        sharedPreferences.edit().putString(EDUCATION_TEACHER,educationTeacher).apply()
    }

    //функция получения образования учителя
    fun getEducationTeacher():String{
        return sharedPreferences.getString(EDUCATION_TEACHER,"").toString()
    }

    // функция получения описания учителя
    fun getDescriptionTeacher():String{
        return sharedPreferences.getString(DESCRIPTION_TEACHER,"").toString()
    }

    //функция получения имени фамилии
    fun getFirstNameTeacher():String{
        return sharedPreferences.getString(FIRST_NAME_TEACHER,"").toString()
    }

    // функция получения фамилии учителя
    fun getLastNameTeacher():String{
        return sharedPreferences.getString(LAST_NAME_TEACHER,"").toString()
    }

    // функция получения опыта работы учителя
    fun getExperienceTeacher():String{
        return sharedPreferences.getString(EXPERIENCE_TEACHER,"").toString()
    }

}