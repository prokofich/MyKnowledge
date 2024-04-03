package com.my.knowledge.viewmodel.teacherviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.my.knowledge.model.database.Room.database.AppDatabase
import com.my.knowledge.model.database.Room.entity.PriceListEntity
import com.my.knowledge.model.repository.RoomRepository
import com.my.knowledge.model.repository.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PriceListViewModel(application: Application):AndroidViewModel(application) {

    var priceList: MutableLiveData<List<PriceListEntity>?> = MutableLiveData()
    private var databaseRoom:AppDatabase? = null
    var isSuccessful: MutableLiveData<Boolean> = MutableLiveData()

    private val lock = Any()

    private val firestoreRepository = FirestoreRepository()

    // асинхронная функция сохранения элемента в прайс-листе
    fun insertPriceFromDatabase(item: PriceListEntity,userId:String?){
        viewModelScope.launch(Dispatchers.IO) {

            databaseRoom = RoomRepository(getApplication()).database
            databaseRoom?.databasePriceListDao()?.insertPrice(item)

            val itemPriceList = databaseRoom?.databasePriceListDao()?.getLastPrice()

            if (userId != null){
                val answer = firestoreRepository.setDataInPriceList(itemPriceList!!, userId)
                withContext(Dispatchers.Main){
                    synchronized(lock){
                        isSuccessful.value = answer
                    }
                }
            }

        }
    }

    // асинхронная функция обновления элемента из прайс-листа
    fun updatePriceFromDatabase(item: PriceListEntity,userId:String?){
        viewModelScope.launch(Dispatchers.IO) {

            databaseRoom = RoomRepository(getApplication()).database
            databaseRoom?.databasePriceListDao()?.updatePrice(item)

            if (userId != null){
                val answer = firestoreRepository.updateDataInPriceList(item, userId)
                withContext(Dispatchers.Main){
                    synchronized(lock){
                        isSuccessful.value = answer
                    }
                }
            }

        }
    }

    // асинхронная функция удаления элемента из прайс-листа
    fun deletePriceFromDatabase(item: PriceListEntity,userId: String?){
        viewModelScope.launch(Dispatchers.IO) {

            databaseRoom = RoomRepository(getApplication()).database
            databaseRoom?.databasePriceListDao()?.deletePrice(item)

            if(userId != null){
                val answer = firestoreRepository.deleteDataInPriceList(item, userId)
                withContext(Dispatchers.Main){
                    synchronized(lock){
                        isSuccessful.value = answer
                    }
                }
            }

        }
    }

    fun getAllPriceList(){
        viewModelScope.launch(Dispatchers.IO) {

            databaseRoom = RoomRepository(getApplication()).database
            val answer = databaseRoom?.databasePriceListDao()?.getAllPriceList()

            withContext(Dispatchers.Main){
                priceList.value = answer
            }

        }
    }

}