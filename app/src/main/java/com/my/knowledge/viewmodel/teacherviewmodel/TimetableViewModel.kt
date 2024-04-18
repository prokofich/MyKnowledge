package com.my.knowledge.viewmodel.teacherviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.my.knowledge.model.database.Room.database.AppDatabase
import com.my.knowledge.model.database.Room.entity.CountLessonsEntity
import com.my.knowledge.model.database.Room.entity.TimeTableEntity
import com.my.knowledge.model.repository.FirestoreRepository
import com.my.knowledge.model.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimetableViewModel(application: Application) : AndroidViewModel(application) {

    private var databaseRoom : AppDatabase? = null
    private val firestoreRepository = FirestoreRepository()

    val timeTableList : MutableLiveData <List <TimeTableEntity>? > = MutableLiveData()

    val isSuccessfulInsertInRoom : MutableLiveData <TimeTableEntity> = MutableLiveData()

    val isSuccessfulInsertInFirestore : MutableLiveData <Boolean> = MutableLiveData()
    val isSuccessfulUpdateInFirestore : MutableLiveData <Boolean> = MutableLiveData()
    val isSuccessfulDeleteInFirestore : MutableLiveData <Boolean> = MutableLiveData()

    private val lock = Any()

    // асинхронная функция обновления количества занятий в выбранный день недели
    fun updateCountLessonsInDayOfWeek(item : CountLessonsEntity){
        viewModelScope.launch(Dispatchers.IO) {
            databaseRoom = RoomRepository(getApplication()).database
            databaseRoom?.databaseCountLessonsDao()?.updateCountLessons(item)
        }
    }

    // асинхронная функция сохранения элемента в Room
    fun insertItemTableListInRoom(item : TimeTableEntity){
        viewModelScope.launch(Dispatchers.IO) {

            databaseRoom = RoomRepository(getApplication()).database
            val answer = databaseRoom?.databaseTimeTableDao()?.insertTimeTableItem(item)
            withContext(Dispatchers.Main){
                answer?.let {
                    item.id = it
                    synchronized(lock){ isSuccessfulInsertInRoom.value = item }
                }
            }

        }
    }

    // асинхронная функция обновления элемента в Room
    fun updateItemTableListInRoom(item : TimeTableEntity){
        viewModelScope.launch(Dispatchers.IO) {
            databaseRoom = RoomRepository(getApplication()).database
            databaseRoom?.databaseTimeTableDao()?.updateTimeTableItem(item)
        }
    }

    // асинхронная функция удаления элемента из Room
    fun deleteItemTableListInRoom(item : TimeTableEntity){
        viewModelScope.launch(Dispatchers.IO) {
            databaseRoom = RoomRepository(getApplication()).database
            databaseRoom?.databaseTimeTableDao()?.deleteTimeTableItem(item)
        }
    }

    // асинхронная функция сохранения элемента в Firestore
    fun insertItemTableListInFirestore(item : TimeTableEntity , userId : String? , day : String?){
        viewModelScope.launch(Dispatchers.IO) {

            userId?.let {
                day?.let {
                    val answer = firestoreRepository.setDataInTableListInFirestore(item, userId, day)
                    withContext(Dispatchers.Main){
                        synchronized(lock){ isSuccessfulInsertInFirestore.value = answer }
                    }
                }
            }

        }
    }

    // асинхронная функция обновления элемента в Firestore
    fun updateItemTableListInFirestore(item : TimeTableEntity , userId : String? , day: String?){
        viewModelScope.launch(Dispatchers.IO) {

            userId?.let {
                day?.let {
                    val answer = firestoreRepository.updateDataInTableListInFirestore(item, userId, day)
                    withContext(Dispatchers.Main){
                        synchronized(lock){ isSuccessfulUpdateInFirestore.value = answer }
                    }
                }
            }

        }
    }

    // асинхронная функция удаления элемента из Firestore
    fun deleteItemTableListInFirestore(item : TimeTableEntity , userId : String? , day: String?){
        viewModelScope.launch(Dispatchers.IO) {

            userId?.let {
                day?.let {
                    val answer = firestoreRepository.deleteDataInTableListInFirestore(item, userId, day)
                    withContext(Dispatchers.Main){
                        synchronized(lock){ isSuccessfulDeleteInFirestore.value = answer }
                    }
                }
            }

        }
    }

    // асинхронная функция получения расписания определенного дня
    fun getListTimeTableByDayOfWeek(day:String?,userId:String?){
        viewModelScope.launch(Dispatchers.IO) {

            userId?.let {
                day?.let {
                    databaseRoom = RoomRepository(getApplication()).database
                    val answer = databaseRoom?.databaseTimeTableDao()?.getListTimeTableByDayOfWeek(day,userId)
                    withContext(Dispatchers.Main){
                        synchronized(lock){ timeTableList.value = answer }
                    }
                }
            }

        }
    }

}