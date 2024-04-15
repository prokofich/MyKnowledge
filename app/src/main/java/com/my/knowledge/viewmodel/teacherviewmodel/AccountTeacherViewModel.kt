package com.my.knowledge.viewmodel.teacherviewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.my.knowledge.model.database.Room.database.AppDatabase
import com.my.knowledge.model.database.Room.entity.MyAccountEntity
import com.my.knowledge.model.repository.RoomRepository
import com.my.knowledge.model.repository.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountTeacherViewModel(application: Application): AndroidViewModel(application) {

    private var databaseRoom: AppDatabase? = null
    private val firestoreRepository = FirestoreRepository()
    private val lock = Any()

    val isSuccessful : MutableLiveData <Boolean> = MutableLiveData ()
    val infoMyAccount : MutableLiveData <MyAccountEntity?> = MutableLiveData ()
    val urlPhoto : MutableLiveData <String> = MutableLiveData ()
    val photoFromStorage : MutableLiveData <Bitmap?> = MutableLiveData ()

    fun setFirstAndLastName(firstName:String,lastName:String,userId:String?){
        viewModelScope.launch(Dispatchers.IO) {

            userId?.let {
                val answer = firestoreRepository.updateFirstAndLastNameInFirestore(firstName, lastName, it)
                withContext(Dispatchers.Main){
                    synchronized(lock){
                        isSuccessful.value = answer
                    }
                }
            }

        }
    }

    fun uploadPhotoForProfile(bitmap: Bitmap){
        viewModelScope.launch(Dispatchers.IO) {

            val answer = firestoreRepository.uploadPhotoForProfileTeacherInFirestore(bitmap)
            withContext(Dispatchers.Main){
                urlPhoto.value = answer
            }

        }
    }

    fun getImageFromStorageByUrl(url: String?){
        viewModelScope.launch(Dispatchers.IO) {

            url?.let {
                if(url.isNotEmpty()){
                    val answer = firestoreRepository.getImageFromStorageByUrl(it)
                    withContext(Dispatchers.Main){
                        photoFromStorage.value = answer
                    }
                }
            }

        }
    }

    fun setDataFromMyProfile(dataFromProfile:String,typeDataFromProfile:String,userId:String?){
        viewModelScope.launch(Dispatchers.IO) {

            userId?.let {
                val answer = firestoreRepository.updateDataFromMyProfileInFirestore(dataFromProfile, typeDataFromProfile, it)
                withContext(Dispatchers.Main){
                    synchronized(lock){
                        isSuccessful.value = answer
                    }
                }
            }

        }
    }

    fun getInfoMyAccount(userId: String){
        viewModelScope.launch(Dispatchers.IO) {

            databaseRoom = RoomRepository(getApplication()).database
            val answer = databaseRoom?.databaseMyAccountDao()?.getAccountById(userId)
            withContext(Dispatchers.Main){
                infoMyAccount.value = answer
            }

        }
    }

    fun updateInfoMyAccount(item:MyAccountEntity){
        viewModelScope.launch(Dispatchers.IO) {
            databaseRoom = RoomRepository(getApplication()).database
            databaseRoom?.databaseMyAccountDao()?.updateAccount(item)
        }
    }

    fun updateUrlPhotoFromProfile(url:String,userId: String?){
        viewModelScope.launch(Dispatchers.IO) {

            userId?.let {
                firestoreRepository.updateUrlPhotoFromProfileInFirestore(url, it)
            }

        }
    }

}