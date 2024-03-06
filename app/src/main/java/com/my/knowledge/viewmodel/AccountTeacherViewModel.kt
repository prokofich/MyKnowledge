package com.my.knowledge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.knowledge.model.modelData.ModelTeacher
import com.my.knowledge.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountTeacherViewModel:ViewModel() {

    private val repository = Repository()

    val infoMyAccount: MutableLiveData<ModelTeacher> = MutableLiveData()
    val isSuccessfull:MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessfull1:MutableLiveData<Boolean> = MutableLiveData()

    fun getMyInfoTeacher(idTeacher:String?){
        viewModelScope.launch(Dispatchers.IO) {
            if(idTeacher != null) {
                val answer = repository.getMyInfoTeacher(idTeacher)
                withContext(Dispatchers.Main) {
                    infoMyAccount.value = answer
                }
            }
        }
    }

    fun setFirstAndLastName(firstName:String,lastName:String,userId:String?){
        viewModelScope.launch(Dispatchers.IO) {
            if(userId != null){
                val answer = repository.setFirstAndLastName(firstName, lastName, userId)
                withContext(Dispatchers.Main){
                    isSuccessfull.value = answer
                }
            }
        }
    }

    fun setDataFromMyProfile(dataFromProfile:String,typeDataFromProfile:String,userId:String?){
        viewModelScope.launch(Dispatchers.IO) {
            if(userId != null){
                
            }
        }
    }

}