package com.my.knowledge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.knowledge.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel:ViewModel() {

    private val repository = Repository()

    val isLogin: MutableLiveData<Boolean> = MutableLiveData()
    val isCorrectInputData: MutableLiveData<String> = MutableLiveData()

    // функция проверки правильности данных
    fun checkInputDataInLogin(email:String?,password:String?,status:String?){
        isCorrectInputData.value = repository.checkInputDataInLogin(email, password, status)
    }

    // функция входа в аккаунт
    fun loginInAccount(email:String,password:String){
        viewModelScope.launch(Dispatchers.IO) {
            val answer = repository.loginInAccount(email, password)
            withContext(Dispatchers.Main){
                isLogin.value = answer
            }
        }
    }

}