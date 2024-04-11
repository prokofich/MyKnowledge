package com.my.knowledge.model.repository

import android.content.Context
import android.widget.Toast
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.model.constant.OperationStatus
import com.my.knowledge.model.constant.alfavit

class Repository{

    //функция проверки интернет соединения
    fun checkNetworkState():Boolean? {
        return MAIN?.getStateNetwork()
    }

    // функция выхода из приложения
    fun exitTheApplication(){
        MAIN?.exitApplication()
    }

    // функция проверки правильности введенных данных
    fun checkInputPriceData(name:String,price:String,desc:String):String{
        return if(name.isNotEmpty() && price.isNotEmpty() && desc.isNotEmpty()){
            if(price.toSet().intersect(alfavit.toSet()).isEmpty()){
                OperationStatus.Correct.status
            }else{
                "в поле цены запишите число"
            }
        }else{
            "вы ввели не все данные"
        }
    }

    fun checkInputTableData(nameLesson : String , nameStudent : String , price : String , startTime : String , endTime : String) : String {
        return if(nameLesson.isNotEmpty() && nameStudent.isNotEmpty() && price.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty()){
            if(checkTimeFormat(startTime) && checkTimeFormat(endTime)){
                OperationStatus.Correct.status
            }else{
                "неверный формат времени"
            }
        }else{
            "вы ввели не все данные"
        }
    }

    private fun checkTimeFormat(str: String): Boolean {
        if (str.length != 4) return false
        val hours = str.substring(0, 2).toIntOrNull()
        val minutes = str.substring(2, 4).toIntOrNull()
        return !(hours == null || hours !in 0..23 || minutes == null || minutes !in 0..59)
    }

    // функция показа всплывающего сообщения
    fun showToast(message:String?,context:Context){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    // функция проверки правильности введенных данных
    fun checkInputData(email:String?,password:String?,firstName:String?,lastName:String?,status:String?,networkState:Boolean?):String{

        val set1 = setOf(
            "0","1","2","3","4","5","6","7","8","9","!","#","$","%","^","&","*","(",")","-",
            "_","=","+","/","{","}","[","]","|","'",";",":","?",">","<",",",".")

        if(email!="" && password!="" && firstName!="" && lastName!="" && status!=null){
            return if(email?.contains("@") == true){
                if((firstName!!.toSet() intersect set1).isEmpty()){
                    if((lastName!!.toSet() intersect set1).isEmpty()){
                        if(networkState == true){
                            OperationStatus.Correct.status
                        }else{
                            "проверьте состояние сети"
                        }
                    }else{
                        "в фамилии должны быть только буквы"
                    }
                }else{
                    "в имени должны быть только буквы"
                }
            }else{
                "неверный формат вашей почты"
            }
        }else{
            return "вы заполнили не все поля"
        }
    }

    // функция проверки правильности введенных данных для входа в аккаунт
    fun checkInputDataInLogin(email:String?,password:String?,stateNetwork:Boolean?):String{
        return if(email!="" && password!="" && stateNetwork == true){
            OperationStatus.Correct.status
        }else{
            return if(stateNetwork == false){
                "проверьте интернет соединение"
            }else{
                "вы заполнили не все поля"
            }
        }
    }
}