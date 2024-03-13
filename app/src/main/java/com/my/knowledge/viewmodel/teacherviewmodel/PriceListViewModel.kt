package com.my.knowledge.viewmodel.teacherviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.my.knowledge.model.database.Room.entity.PriceListEntity
import com.my.knowledge.model.database.Room.repository.RoomRepository
import com.my.knowledge.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PriceListViewModel(application: Application):AndroidViewModel(application) {

    var priceList: MutableLiveData<List<PriceListEntity>> = MutableLiveData()
    private var databaseRoom:RoomRepository? = null
    var isSuccessful: MutableLiveData<Boolean> = MutableLiveData()
    private val repository = Repository()

    fun insertPriceInDatabase(item: PriceListEntity,userId:String?){
        viewModelScope.launch(Dispatchers.IO) {

            val databaseRoom = RoomRepository(getApplication()).database
            databaseRoom.databaseDao().insertPrice(item)

            if (userId!=null){
                val answer = repository.setDataInPriceList(item, userId)
                withContext(Dispatchers.Main){
                    isSuccessful.value = answer
                }
            }

        }
    }

    fun getAllPriceList(){
        viewModelScope.launch(Dispatchers.IO) {
            val databaseRoom = RoomRepository(getApplication()).database
            val answer = databaseRoom.databaseDao().getAllPriceList()
            withContext(Dispatchers.Main){
                priceList.value = answer
            }
        }
    }

}