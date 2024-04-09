package com.my.knowledge.model.constant

import com.my.knowledge.view.activity.MainActivity

var MAIN: MainActivity? = null

const val USER_ID = "user_id"

const val URL_PHOTO_FROM_PROFILE = "URL_PHOTO_FROM_PROFILE"

const val Teachers = "Teachers"
const val Students = "Students"
const val Teachers_and_Students = "Students and Teachers"
const val Teachers_price_list = "Teachers price list"

const val USER_TYPE = "USER_TYPE"

const val alfavit = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNMёйцукенгшщзхъфывапролджэячсмитьбюЁЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ"

const val DAY_OF_WEEK = "DAY_OF_WEEK"

enum class DayOfWeek(val day:String){
    Monday("ПН"),
    Tuesday("ВТ"),
    Wednesday("СР"),
    Thursday("ЧТ"),
    Friday("ПТ"),
    Saturday("СБ"),
    Sunday("ВС")
}

enum class UserType(val user:String){
    Teacher("учитель"),
    Student("ученик")
}

enum class OperationStatus(val status:String){
    Correct("correct"),
    Error("error")
}