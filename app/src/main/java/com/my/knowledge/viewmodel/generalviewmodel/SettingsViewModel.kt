package com.my.knowledge.viewmodel.generalviewmodel

import androidx.lifecycle.ViewModel
import com.my.knowledge.model.repository.FirestoreRepository

class SettingsViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()

    // функция выхода из аккаунта Firebase
    fun exitFromAccount() = firestoreRepository.exitFromAccount()


}