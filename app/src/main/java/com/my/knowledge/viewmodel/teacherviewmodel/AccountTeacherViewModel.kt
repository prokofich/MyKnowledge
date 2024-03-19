package com.my.knowledge.viewmodel.teacherviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.knowledge.model.database.firebase.repository.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountTeacherViewModel:ViewModel() {

    private val firestoreRepository = FirestoreRepository()
    private val lock = Any()

    val isSuccessful:MutableLiveData<Boolean> = MutableLiveData()

    fun setFirstAndLastName(firstName:String,lastName:String,userId:String?){
        viewModelScope.launch(Dispatchers.IO) {

            if(userId != null){
                val answer = firestoreRepository.setFirstAndLastName(firstName, lastName, userId)
                withContext(Dispatchers.Main){
                    synchronized(lock){
                        isSuccessful.value = answer
                    }
                }
            }

        }
    }

    fun setDataFromMyProfile(dataFromProfile:String,typeDataFromProfile:String,userId:String?){
        viewModelScope.launch(Dispatchers.IO) {

            if(userId != null){
                val answer = firestoreRepository.setDataFromMyProfile(dataFromProfile, typeDataFromProfile, userId)
                withContext(Dispatchers.Main){
                    synchronized(lock){
                        isSuccessful.value = answer
                    }
                }
            }

        }
    }

}