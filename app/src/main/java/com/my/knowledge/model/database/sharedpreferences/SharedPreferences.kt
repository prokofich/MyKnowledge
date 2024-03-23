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

    // функция получения количества занятий в понедельник
    fun getCountLessonsInMonday():Int{
        return sharedPreferences.getInt(COUNT_LESSONS_IN_MONDAY,0)
    }

    // функция обновления количества занятий в понедельник
    fun updateCountLessonsInMonday(count:Int){
        sharedPreferences.edit().putInt(COUNT_LESSONS_IN_MONDAY,count).apply()
    }

    // функция получения количества занятий во вторник
    fun getCountLessonsInTuesday():Int{
        return sharedPreferences.getInt(COUNT_LESSONS_IN_TUESDAY,0)
    }

    // функция обновления количества занятий во вторник
    fun updateCountLessonsInTuesday(count:Int){
        sharedPreferences.edit().putInt(COUNT_LESSONS_IN_TUESDAY,count).apply()
    }

    // функция получения количества занятий в среду
    fun getCountLessonsInWednesday():Int{
        return sharedPreferences.getInt(COUNT_LESSONS_IN_WEDNESDAY,0)
    }

    // функция обновления количества занятий в среду
    fun updateCountLessonsInWednesday(count:Int){
        sharedPreferences.edit().putInt(COUNT_LESSONS_IN_WEDNESDAY,count).apply()
    }

    // функция получения количества занятий в четверг
    fun getCountLessonsInThursday():Int{
        return sharedPreferences.getInt(COUNT_LESSONS_IN_THURSDAY,0)
    }

    // функция обновления количества занятий в четверг
    fun updateCountLessonsInThursday(count:Int){
        sharedPreferences.edit().putInt(COUNT_LESSONS_IN_THURSDAY,count).apply()
    }

    // функция получения количества занятий в пятницу
    fun getCountLessonsInFriday():Int{
        return sharedPreferences.getInt(COUNT_LESSONS_IN_FRIDAY,0)
    }

    // функция обновления количества занятий в пятницу
    fun updateCountLessonsInFriday(count:Int){
        sharedPreferences.edit().putInt(COUNT_LESSONS_IN_FRIDAY,count).apply()
    }

    // функция получения количества занятий в субботу
    fun getCountLessonsInSaturday():Int{
        return sharedPreferences.getInt(COUNT_LESSONS_IN_SATURDAY,0)
    }

    // функция обновления количества занятий в субботу
    fun updateCountLessonsInSaturday(count:Int){
        sharedPreferences.edit().putInt(COUNT_LESSONS_IN_SATURDAY,count).apply()
    }

    // функция получения количества занятий в воскресенье
    fun getCountLessonsInSunday():Int{
        return sharedPreferences.getInt(COUNT_LESSONS_IN_SUNDAY,0)
    }

    // функция обновления количества занятий в воскресенье
    fun updateCountLessonsInSunday(count:Int){
        sharedPreferences.edit().putInt(COUNT_LESSONS_IN_SUNDAY,count).apply()
    }

}