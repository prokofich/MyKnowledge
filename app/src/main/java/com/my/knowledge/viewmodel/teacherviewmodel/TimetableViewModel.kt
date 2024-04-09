package com.my.knowledge.viewmodel.teacherviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.knowledge.model.database.Room.database.AppDatabase
import com.my.knowledge.model.database.Room.entity.TimeTableEntity
import com.my.knowledge.model.repository.FirestoreRepository
import com.my.knowledge.model.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimetableViewModel(application: Application) : AndroidViewModel(application) {

    private var databaseRoom : AppDatabase? = null
    private val firestoreRepository = FirestoreRepository()

    val isSuccessful : MutableLiveData <Boolean> = MutableLiveData()
    val timeTableList : MutableLiveData <List <TimeTableEntity>? > = MutableLiveData()

    private val lock = Any()

    fun getListTimeTableByDayOfWeek(day:String?,userId:String?){
        viewModelScope.launch(Dispatchers.IO) {

            userId?.let {
                day?.let {
                    databaseRoom = RoomRepository(getApplication()).database
                    val answer = databaseRoom?.databaseTimeTableDao()?.getListTimeTableByDayOfWeek(day,userId)
                    withContext(Dispatchers.Main){
                        synchronized(lock){
                            timeTableList.value = answer
                        }
                    }
                }
            }

        }
    }

}