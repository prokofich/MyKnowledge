package com.my.knowledge.model.repository

import android.content.Context
import android.widget.Toast
import com.my.knowledge.model.constant.CORRECT
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.model.constant.alfavit


class Repository{

    //функция проверки интернет соединения
    fun checkNetworkState():Boolean? {
        return MAIN?.getStateNetwork()
    }

    // функция закрытия приложения
    fun closeApplication(){
        MAIN?.closeApplication()
    }

    // функция проверки правильности введенных данных
    fun checkInputPriceData(name:String,price:String,desc:String):String{
        return if(name!="" && price!="" && desc!=""){
            if(price.toSet().intersect(alfavit.toSet()).isEmpty()){
                "верно"
            }else{
                "в поле цены запишите число"
            }
        }else{
            "вы ввели не все данные"
        }
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
                            CORRECT
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
            CORRECT
        }else{
            return if(stateNetwork == false){
                "проверьте интернет соединение"
            }else{
                "вы заполнили не все поля"
            }
        }
    }
}