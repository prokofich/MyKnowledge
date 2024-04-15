package com.my.knowledge.viewmodel.generalviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.knowledge.model.modelData.ModelStudent
import com.my.knowledge.model.modelData.ModelTeacher
import com.my.knowledge.model.repository.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchUsersViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()

    val listTeachers : MutableLiveData <MutableList <ModelTeacher> > = MutableLiveData()
    val listStudents : MutableLiveData <MutableList <ModelStudent> > = MutableLiveData()
    val listNamePredmets : MutableLiveData <MutableList <String>> = MutableLiveData()

    // асинхронная функция получения всех учителей из Firestore
    fun getAllTeachersInFirestore(){
        viewModelScope.launch(Dispatchers.IO) {

            val answer = firestoreRepository.getTeachersInFirestore()
            withContext(Dispatchers.Main){
                listTeachers.value = answer
            }

        }
    }

    // асинхронная функция получения всех студентов из Firestore
    fun getAllStudentsInFirestore(){
        viewModelScope.launch(Dispatchers.IO) {

            val answer = firestoreRepository.getStudentsInFirestore()
            withContext(Dispatchers.Main){
                listStudents.value = answer
            }

        }
    }

    // асинхронная функция получения названия всех услуг из прайс листа выбранного учителя
    fun getAllNamePredmetsByIdTeacher(idUser:String){
        viewModelScope.launch(Dispatchers.IO) {

            val answer = firestoreRepository.getNamePredmetsByIdTeacherInFirestore(idUser)
            withContext(Dispatchers.Main){
                listNamePredmets.value = answer
            }

        }
    }

}