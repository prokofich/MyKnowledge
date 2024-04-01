package com.my.knowledge.model.database.firebase.repository

import com.my.knowledge.model.database.Room.entity.PriceListEntity
import com.my.knowledge.model.database.firebase.Firestore
import com.my.knowledge.model.modelData.ModelResponseLogin
import com.my.knowledge.model.modelData.ModelTeacher
import com.my.knowledge.model.modelData.ModelUser

class FirestoreRepository {

    // асинхронная функция отправки данных из прайс листа в Firestore
    suspend fun setDataInPriceList(item: PriceListEntity, userId: String):Boolean{
        return Firestore().setDataInPriceList(item, userId)
    }

    // асинхронная функция обновления данных из прайс-листа в Firestore
    suspend fun updateDataInPriceList(item: PriceListEntity, userId: String):Boolean{
        return Firestore().updateDataInPriceList(item, userId)
    }

    // асинхронная функция удаления данных их прайс-листа в Firestore
    suspend fun deleteDataInPriceList(item: PriceListEntity, userId: String):Boolean{
        return Firestore().deleteDataInPriceList(item, userId)
    }

    // асинхронная функция отправки данных из профиля учителя
    suspend fun setDataFromMyProfile(dataFromProfile:String,typeDataFromProfile:String,userId:String):Boolean{
        return Firestore().setDataFromMyProfile(dataFromProfile, typeDataFromProfile, userId)
    }

    // асинхронная функция отправки имени и фамилии
    suspend fun setFirstAndLastName(firstName:String,lastName:String,userId:String):Boolean{
        return Firestore().setFirstAnsLastName(firstName, lastName, userId)
    }

    // асинхронная функция регистрации пользователя
    suspend fun createAccount(email:String,password:String):String{
        return Firestore().createAccount(email,password)
    }

    // асинхронная функция входа в аккаунт
    suspend fun loginInAccount(email:String,password:String):ModelResponseLogin{
        return Firestore().loginInAccount(email,password)
    }

    // асинхронная функция добавления первичных данных на сервер после регистрации
    suspend fun setPrimaryDataAfterRegistration(modelUser: ModelUser){
        Firestore().setPrimaryDataAfterRegistration(modelUser)
    }

    // асинхронная функция получения данных для аккаунта учителя
    suspend fun getMyInfoTeacher(idTeacher:String): ModelTeacher {
        return Firestore().getMyInfoTeacher(idTeacher)
    }

    // асинхронная функция проверки безлогинового входа
    fun checkOpenAccount():Boolean{
        return Firestore().checkOpenAccount()
    }

}