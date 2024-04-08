package com.my.knowledge.model.repository

import android.graphics.Bitmap
import com.my.knowledge.model.database.Room.entity.MyAccountEntity
import com.my.knowledge.model.database.Room.entity.PriceListEntity
import com.my.knowledge.model.database.firebase.Firestore
import com.my.knowledge.model.modelData.ModelResponseLogin

class FirestoreRepository {

    // функция загрузки аватарки и получения её url адреса
    suspend fun uploadPhotoForProfileTeacher(bitmap : Bitmap) : String{
        return Firestore().uploadPhotoForProfileTeacher(bitmap)
    }

    // функция получения картинки из Storage
    suspend fun getImageFromStorageByUrl(url: String) : Bitmap?{
        return Firestore().getImageFromStorageByUrl(url)
    }

    // функция обновления url адреса аватарки
    fun updateUrlPhotoFromProfile(url:String,userId: String) {
        Firestore().updateUrlPhotoFromProfile(url, userId)
    }

    // асинхронная функция отправки данных из прайс листа в Firestore
    suspend fun setDataInPriceList(item : PriceListEntity, userId : String) : Boolean {
        return Firestore().setDataInPriceList(item, userId)
    }

    // асинхронная функция обновления данных из прайс-листа в Firestore
    suspend fun updateDataInPriceList(item : PriceListEntity, userId : String) : Boolean {
        return Firestore().updateDataInPriceList(item, userId)
    }

    // асинхронная функция удаления данных их прайс-листа в Firestore
    suspend fun deleteDataInPriceList(item : PriceListEntity, userId : String) : Boolean {
        return Firestore().deleteDataInPriceList(item, userId)
    }

    // асинхронная функция отправки данных из профиля учителя
    suspend fun setDataFromMyProfile(dataFromProfile : String,typeDataFromProfile : String,userId : String) : Boolean {
        return Firestore().setDataFromMyProfile(dataFromProfile, typeDataFromProfile, userId)
    }

    // асинхронная функция отправки имени и фамилии
    suspend fun setFirstAndLastName(firstName : String,lastName : String,userId : String) : Boolean {
        return Firestore().setFirstAnsLastName(firstName, lastName, userId)
    }

    // асинхронная функция регистрации пользователя
    suspend fun createAccount(email : String,password : String) : String {
        return Firestore().createAccount(email,password)
    }

    // асинхронная функция входа в аккаунт
    suspend fun loginInAccount(email : String,password : String) : ModelResponseLogin {
        return Firestore().loginInAccount(email,password)
    }

    // асинхронная функция добавления первичных данных на сервер после регистрации
    fun setPrimaryDataAfterRegistration(item : MyAccountEntity) {
        Firestore().setPrimaryDataAfterRegistration(item)
    }

    // асинхронная функция проверки безлогинового входа
    fun checkOpenAccount() : Boolean {
        return Firestore().checkOpenAccount()
    }

}