package com.my.knowledge.viewmodel.generalviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.knowledge.model.repository.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel:ViewModel() {

    private val firestoreRepository = FirestoreRepository()

    val isOpenAccount: MutableLiveData<Boolean> = MutableLiveData()

    fun checkOpenAccount(){
        viewModelScope.launch(Dispatchers.IO) {
            val answer = firestoreRepository.checkOpenAccountInFirestore()
            withContext(Dispatchers.Main){
                isOpenAccount.value = answer
            }
        }
    }

}