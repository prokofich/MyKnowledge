package com.my.knowledge.model.repository

import android.graphics.Bitmap
import com.my.knowledge.model.database.Room.entity.MyAccountEntity
import com.my.knowledge.model.database.Room.entity.PriceListEntity
import com.my.knowledge.model.database.Room.entity.TimeTableEntity
import com.my.knowledge.model.database.firebase.Firestore
import com.my.knowledge.model.modelData.ModelResponseLogin

class FirestoreRepository {

    // функция загрузки аватарки и получения её url адреса
    suspend fun uploadPhotoForProfileTeacherInFirestore(bitmap : Bitmap) : String{
        return Firestore().uploadPhotoForProfileTeacherInFirestore(bitmap)
    }

    // функция получения картинки из Storage
    suspend fun getImageFromStorageByUrl(url: String) : Bitmap?{
        return Firestore().getImageFromStorageByUrl(url)
    }

    // функция обновления url адреса аватарки
    fun updateUrlPhotoFromProfileInFirestore(url:String,userId: String) {
        Firestore().updateUrlPhotoFromProfileInFirestore(url, userId)
    }

    // асинхронная функция отправки данных из расписания в Firestore
    suspend fun setDataInTableListInFirestore(item : TimeTableEntity , userId : String , day : String) : Boolean {
        return Firestore().setDataInTableListInFirestore(item, userId, day)
    }

    // асинхронная функция обновления данных из расписания в Firestore
    suspend fun updateDataInTableListInFirestore(item : TimeTableEntity , userId : String , day : String) : Boolean {
        return Firestore().updateDataInTableListInFirestore(item, userId, day)
    }

    // асинхронная функция удаления данных из расписания в Firestore
    suspend fun deleteDataInTableListInFirestore(item : TimeTableEntity , userId : String , day : String) : Boolean {
        return Firestore().deleteDataInTableListInFirestore(item, userId, day)
    }

    // асинхронная функция отправки данных из прайс листа в Firestore
    suspend fun setDataInPriceListInFirestore(item : PriceListEntity, userId : String) : Boolean {
        return Firestore().setDataInPriceListInFirestore(item, userId)
    }

    // асинхронная функция обновления данных из прайс-листа в Firestore
    suspend fun updateDataInPriceListInFirestore(item : PriceListEntity, userId : String) : Boolean {
        return Firestore().updateDataInPriceListInFirestore(item, userId)
    }

    // асинхронная функция удаления данных их прайс-листа в Firestore
    suspend fun deleteDataInPriceListInFirestore(item : PriceListEntity, userId : String) : Boolean {
        return Firestore().deleteDataInPriceListInFirestore(item, userId)
    }

    // асинхронная функция отправки данных из профиля учителя
    suspend fun setDataFromMyProfileInFirestore(dataFromProfile : String,typeDataFromProfile : String,userId : String) : Boolean {
        return Firestore().setDataFromMyProfileInFirestore(dataFromProfile, typeDataFromProfile, userId)
    }

    // асинхронная функция отправки имени и фамилии
    suspend fun setFirstAndLastNameInFirestore(firstName : String,lastName : String,userId : String) : Boolean {
        return Firestore().setFirstAndLastNameInFirestore(firstName, lastName, userId)
    }

    // асинхронная функция регистрации пользователя
    suspend fun createAccountInFirestore(email : String,password : String) : String {
        return Firestore().createAccountInFirestore(email,password)
    }

    // асинхронная функция входа в аккаунт
    suspend fun loginInAccountInFirestore(email : String,password : String) : ModelResponseLogin {
        return Firestore().loginInAccountInFirestore(email,password)
    }

    // асинхронная функция добавления первичных данных на сервер после регистрации
    fun setPrimaryDataAfterRegistrationInFirestore(item : MyAccountEntity) {
        Firestore().setPrimaryDataAfterRegistrationInFirestore(item)
    }

    // асинхронная функция проверки безлогинового входа
    fun checkOpenAccountInFirestore() : Boolean {
        return Firestore().checkOpenAccountInFirestore()
    }

}