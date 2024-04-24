package com.my.knowledge.model.repository

import android.graphics.Bitmap
import com.my.knowledge.model.database.Room.entity.MyAccountEntity
import com.my.knowledge.model.database.Room.entity.PriceListEntity
import com.my.knowledge.model.database.Room.entity.TimeTableEntity
import com.my.knowledge.model.database.firebase.FirestoreLoginManager
import com.my.knowledge.model.database.firebase.FirestorePriceListManager
import com.my.knowledge.model.database.firebase.FirestoreProfileManager
import com.my.knowledge.model.database.firebase.FirestoreRegistrationManager
import com.my.knowledge.model.database.firebase.FirestoreSearchManager
import com.my.knowledge.model.database.firebase.FirestoreTableManager
import com.my.knowledge.model.modelData.ModelPriceList
import com.my.knowledge.model.modelData.ModelResponseLogin
import com.my.knowledge.model.modelData.ModelStudent
import com.my.knowledge.model.modelData.ModelTeacher

class FirestoreRepository {

    // функция загрузки аватарки и получения её url адреса
    suspend fun uploadPhotoForProfileTeacherInFirestore(bitmap : Bitmap) : String{
        return FirestoreProfileManager().uploadPhoto(bitmap)
    }

    // функция получения картинки из Storage
    suspend fun getImageFromStorageByUrl(url: String) : Bitmap?{
        return FirestoreProfileManager().getImage(url)
    }

    // функция обновления url адреса аватарки
    fun updateUrlPhotoFromProfileInFirestore(url:String,userId: String) {
        FirestoreProfileManager().updateUrlPhoto(url, userId)
    }

    // асинхронная функция отправки данных из расписания в Firestore
    suspend fun setDataInTableListInFirestore(item : TimeTableEntity , userId : String , day : String) : Boolean {
        return FirestoreTableManager().save(item, userId, day)
    }

    // асинхронная функция обновления данных из расписания в Firestore
    suspend fun updateDataInTableListInFirestore(item : TimeTableEntity , userId : String , day : String) : Boolean {
        return FirestoreTableManager().update(item, userId, day)
    }

    // асинхронная функция удаления данных из расписания в Firestore
    suspend fun deleteDataInTableListInFirestore(item : TimeTableEntity , userId : String , day : String) : Boolean {
        return FirestoreTableManager().delete(item, userId, day)
    }

    // асинхронная функция отправки данных из прайс листа в Firestore
    suspend fun setDataInPriceListInFirestore(item : PriceListEntity, userId : String) : Boolean {
        return FirestorePriceListManager().save(item, userId)
    }

    // асинхронная функция обновления данных из прайс-листа в Firestore
    suspend fun updateDataInPriceListInFirestore(item : PriceListEntity, userId : String) : Boolean {
        return FirestorePriceListManager().update(item, userId)
    }

    // асинхронная функция удаления данных их прайс-листа в Firestore
    suspend fun deleteDataInPriceListInFirestore(item : PriceListEntity, userId : String) : Boolean {
        return FirestorePriceListManager().delete(item, userId)
    }

    // асинхронная функция получения данных из прайс-листа выбранного учителя в Firestore
    suspend fun getPriceListByIdTeacherInFirestore(idTeacher : String) : MutableList<ModelPriceList> {
        return FirestorePriceListManager().get(idTeacher)
    }

    // асинхронная функция отправки данных из профиля учителя
    suspend fun updateDataFromMyProfileInFirestore(dataFromProfile : String,typeDataFromProfile : String,userId : String) : Boolean {
        return FirestoreProfileManager().updateData(dataFromProfile, typeDataFromProfile, userId)
    }

    // асинхронная функция отправки имени и фамилии
    suspend fun updateFirstAndLastNameInFirestore(firstName : String,lastName : String,userId : String) : Boolean {
        return FirestoreProfileManager().updateName(firstName, lastName, userId)
    }

    // асинхронная функция регистрации пользователя
    suspend fun createAccountInFirestore(email : String,password : String) : String {
        return FirestoreRegistrationManager().createAccount(email,password)
    }

    // функция выхода из аккаунта Firebase
    fun exitFromAccount(){
        FirestoreLoginManager().exitAccount()
    }

    // асинхронная функция входа в аккаунт
    suspend fun loginInAccountInFirestore(email : String,password : String) : ModelResponseLogin {
        return FirestoreLoginManager().inputInAccount(email,password)
    }

    // асинхронная функция добавления первичных данных на сервер после регистрации
    fun savePrimaryDataAfterRegistrationInFirestore(item : MyAccountEntity) {
        FirestoreRegistrationManager().saveData(item)
    }

    // асинхронная функция проверки безлогинового входа
    fun checkOpenAccountInFirestore() : Boolean {
        return FirestoreLoginManager().checkOpenAccount()
    }

    // асинхронная функция получения всех учителей из Firestore
    suspend fun getTeachersInFirestore() : MutableList<ModelTeacher>{
        return FirestoreSearchManager().getTeachers()
    }

    // асинхронная функция получения всех учеников из Firestore
    suspend fun getStudentsInFirestore() : MutableList<ModelStudent>{
        return FirestoreSearchManager().getStudents()
    }

    // асинхронная функция получения названия всех услуг у учителя из Firestore
    suspend fun getNamePredmetsByIdTeacherInFirestore(idUser:String) : MutableList<String> {
        return FirestoreSearchManager().getPredmetsById(idUser)
    }

    // функция получения данных из профиля выбранного учителя
    suspend fun getDataFromProfileTeacherById(idTeacher : String) : ModelTeacher {
        return FirestoreProfileManager().getDataFromProfileTeacherById(idTeacher)
    }

}