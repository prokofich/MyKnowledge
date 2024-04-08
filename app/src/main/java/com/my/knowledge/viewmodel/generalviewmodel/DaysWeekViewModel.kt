package com.my.knowledge.viewmodel.generalviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.my.knowledge.model.database.Room.database.AppDatabase
import com.my.knowledge.model.database.Room.entity.CountLessonsEntity
import com.my.knowledge.model.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DaysWeekViewModel(application: Application):AndroidViewModel(application) {

    private var databaseRoom: AppDatabase? = null
    var countLessons: MutableLiveData<CountLessonsEntity?> = MutableLiveData()

    fun getCountAllLessonsByIdUser(idUser:String?){
        viewModelScope.launch(Dispatchers.IO) {

            idUser?.let {
                databaseRoom = RoomRepository(getApplication()).database
                val answer = databaseRoom?.databaseCountLessonsDao()?.getCountLessonsByIdUser(it)
                withContext(Dispatchers.Main){
                    countLessons.value = answer
                }
            }


        }
    }


}