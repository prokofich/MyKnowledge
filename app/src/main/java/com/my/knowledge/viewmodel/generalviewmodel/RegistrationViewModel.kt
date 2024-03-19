package com.my.knowledge.viewmodel.generalviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.knowledge.model.database.firebase.repository.FirestoreRepository
import com.my.knowledge.model.modelData.ModelUser
import com.my.knowledge.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel:ViewModel() {

    private val firestoreRepository = FirestoreRepository()
    private val repository = Repository()

    val isRegistration: MutableLiveData<String> = MutableLiveData()
    val isCorrectInputData: MutableLiveData<String> = MutableLiveData()

    fun createAccount(email:String,password:String){
        viewModelScope.launch(Dispatchers.IO) {
            val answer = firestoreRepository.createAccount(email, password)
            withContext(Dispatchers.Main){
                isRegistration.value = answer
            }
        }
    }

    fun setPrimaryDataAfterRegistration(modelUser: ModelUser){
        viewModelScope.launch(Dispatchers.IO) {
            firestoreRepository.setPrimaryDataAfterRegistration(modelUser)
        }
    }

    fun checkInputData(email:String?,password:String?,firstName:String?,lastName:String?,status:String?){
        isCorrectInputData.value = repository.checkInputData(email, password, firstName, lastName, status)
    }

}