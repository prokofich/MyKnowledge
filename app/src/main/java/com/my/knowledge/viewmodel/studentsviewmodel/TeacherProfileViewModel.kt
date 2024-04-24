package com.my.knowledge.viewmodel.studentsviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.knowledge.model.modelData.ModelPriceList
import com.my.knowledge.model.modelData.ModelTeacher
import com.my.knowledge.model.repository.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeacherProfileViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()

    val teacher : MutableLiveData <ModelTeacher> = MutableLiveData()
    val priceList : MutableLiveData <MutableList <ModelPriceList> > = MutableLiveData()

    // асинхронная функция получения данных из профиля выбранного учителя из Firestore
    fun getDataFromProfileTeacherById(idTeacher : String){
        viewModelScope.launch(Dispatchers.IO) {

            val answer = firestoreRepository.getDataFromProfileTeacherById(idTeacher)
            withContext(Dispatchers.Main){
                teacher.value = answer
            }

        }
    }

    // асинхронная функция получения данных прайс-листа выбранного учителя из Firestore
    fun getPriceListByIdTeacherInFirestore(idTeacher : String){
        viewModelScope.launch(Dispatchers.IO) {

            val answer = firestoreRepository.getPriceListByIdTeacherInFirestore(idTeacher)
            withContext(Dispatchers.Main){
                priceList.value = answer
            }

        }
    }

    // функция проверки на пустоту
    fun checkEmptyModelTeacher(modelTeacher: ModelTeacher) : Boolean{
        return modelTeacher.firstName.isNotEmpty() && modelTeacher.lastName.isNotEmpty()
    }

}