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

}