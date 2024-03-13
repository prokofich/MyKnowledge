package com.my.knowledge.model.repository

import android.content.Context
import android.widget.Toast
import com.my.knowledge.model.modelData.ModelUser
import com.my.knowledge.model.constant.CORRECT
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.model.constant.alfavit
import com.my.knowledge.model.database.Room.entity.PriceListEntity
import com.my.knowledge.model.database.firebase.Firestore
import com.my.knowledge.model.modelData.ModelTeacher


class Repository{

    // функция закрытия приложения
    fun closeApplication(){
        MAIN?.closeApplication()
    }

    // функция проверки безлогинового входа
    fun checkOpenAccount():Boolean{
        return Firestore().checkOpenAccount()
    }

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

    // функция отправки данных из прайс листа в Firestore
    suspend fun setDataInPriceList(item: PriceListEntity, userId: String):Boolean{
        return Firestore().setDataInPriceList(item, userId)
    }

    // функция отправки данных из профиля учителя
    suspend fun setDataFromMyProfile(dataFromProfile:String,typeDataFromProfile:String,userId:String):Boolean{
        return Firestore().setDataFromMyProfile(dataFromProfile, typeDataFromProfile, userId)
    }

    // функция отправки имени и фамилии
    suspend fun setFirstAndLastName(firstName:String,lastName:String,userId:String):Boolean{
        return Firestore().setFirstAnsLastName(firstName, lastName, userId)
    }

    // функция регистрации пользователя
    suspend fun createAccount(email:String,password:String):String{
        return Firestore().createAccount(email,password)
    }

    // функция входа в аккаунт
    suspend fun loginInAccount(email:String,password:String):String{
        return Firestore().loginInAccount(email,password)
    }

    // функция добавления первичных данных на сервер после регистрации
    suspend fun setPrimaryDataAfterRegistration(modelUser: ModelUser){
        Firestore().setPrimaryDataAfterRegistration(modelUser)
    }

    // функция получения данных для аккаунта учителя
    suspend fun getMyInfoTeacher(idTeacher:String):ModelTeacher{
        return Firestore().getMyInfoTeacher(idTeacher)
    }

    // функция показа всплывающего сообщения
    fun showToast(message:String?,context:Context){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    // функция проверки правильности введенных данных
    fun checkInputData(email:String?,password:String?,firstName:String?,lastName:String?,status:String?):String{

        val set1 = setOf(
            "0","1","2","3","4","5","6","7","8","9","!","#","$","%","^","&","*","(",")","-",
            "_","=","+","/","{","}","[","]","|","'",";",":","?",">","<",",",".")

        if(email!="" && password!="" && firstName!="" && lastName!="" && status!=null){
            return if(email?.contains("@") == true){
                if((firstName!!.toSet() intersect set1).isEmpty()){
                    if((lastName!!.toSet() intersect set1).isEmpty()){
                        CORRECT
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
    fun checkInputDataInLogin(email:String?,password:String?):String{
        return if(email!="" && password!=""){
            CORRECT
        }else{
            "вы заполнили не все поля"
        }
    }
}