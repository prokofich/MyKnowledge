package com.my.knowledge.viewmodel.generalviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.knowledge.model.database.firebase.repository.FirestoreRepository
import com.my.knowledge.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel:ViewModel() {

    private val firestoreRepository = FirestoreRepository()
    private val repository = Repository()

    val isLogin: MutableLiveData<String> = MutableLiveData()
    val isCorrectInputData: MutableLiveData<String> = MutableLiveData()

    fun checkInputDataInLogin(email:String?,password:String?){
        isCorrectInputData.value = repository.checkInputDataInLogin(email, password)
    }

    fun loginInAccount(email:String,password:String){
        viewModelScope.launch(Dispatchers.IO) {
            val answer = firestoreRepository.loginInAccount(email, password)
            withContext(Dispatchers.Main){
                isLogin.value = answer
            }
        }
    }

}