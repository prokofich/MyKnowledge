package com.my.knowledge.viewmodel.teacherviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my.knowledge.model.database.Room.database.AppDatabase
import com.my.knowledge.model.database.Room.entity.TimeTableEntity
import com.my.knowledge.model.repository.FirestoreRepository

class TimetableViewModel:ViewModel() {

    var timetableList: MutableLiveData<List<TimeTableEntity>?> = MutableLiveData()
    private var databaseRoom: AppDatabase? = null
    var isSuccessful: MutableLiveData<Boolean> = MutableLiveData()

    private val lock = Any()

    private val firestoreRepository = FirestoreRepository()

}