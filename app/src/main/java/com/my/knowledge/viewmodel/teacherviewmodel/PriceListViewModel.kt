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

    private var databaseRoom : AppDatabase? = null
    private val firestoreRepository = FirestoreRepository()

    val priceList: MutableLiveData <List <PriceListEntity>? > = MutableLiveData()

    val isSuccessfulInsertInRoom: MutableLiveData <PriceListEntity> = MutableLiveData()
    val isSuccessfulInsertInFirestore: MutableLiveData <Boolean> = MutableLiveData()
    val isSuccessfulUpdateInFirestore: MutableLiveData <Boolean> = MutableLiveData()
    val isSuccessfulDeleteInFirestore: MutableLiveData <Boolean> = MutableLiveData()

    private val lock = Any()

    // асинхронная функция сохранения элемента в Room
    fun insertItemPriceListInRoom(item : PriceListEntity){
        viewModelScope.launch(Dispatchers.IO) {

            databaseRoom = RoomRepository(getApplication()).database
            val answer = databaseRoom?.databasePriceListDao()?.insertPrice(item)
            withContext(Dispatchers.Main){
                answer?.let {
                    item.id = answer
                    isSuccessfulInsertInRoom.value = item
                }
            }

        }
    }

    // асинхронная функция обновления элемента в Room
    fun updateItemPriceListInRoom(item : PriceListEntity){
        viewModelScope.launch(Dispatchers.IO) {
            databaseRoom = RoomRepository(getApplication()).database
            databaseRoom?.databasePriceListDao()?.updatePrice(item)
        }
    }

    // асинхронная функция сохранения в Firestore
    fun insertItemPriceListInFirestore(item : PriceListEntity , userId : String?){
        viewModelScope.launch(Dispatchers.IO) {

            userId?.let {
                val answer = firestoreRepository.setDataInPriceList(item , it)
                withContext(Dispatchers.Main){
                    synchronized(lock){
                        isSuccessfulInsertInFirestore.value = answer
                    }
                }
            }

        }
    }

    // асинхронная функция обновления элемента в Firestore
    fun updateItemPriceListInFirestore(item : PriceListEntity , userId : String?){
        viewModelScope.launch(Dispatchers.IO) {

            userId?.let {
                val answer = firestoreRepository.updateDataInPriceList(item , it)
                withContext(Dispatchers.Main){
                    synchronized(lock){
                        isSuccessfulUpdateInFirestore.value = answer
                    }
                }
            }

        }
    }

    // асинхронная функция удаления элемента из Room
    fun deleteItemPriceListInRoom(item : PriceListEntity){
        viewModelScope.launch(Dispatchers.IO) {
            databaseRoom = RoomRepository(getApplication()).database
            databaseRoom?.databasePriceListDao()?.deletePrice(item)
        }
    }

    // асинхронная функция удаления элемента из Firestore
    fun deleteItemPriceListInFirestore( item: PriceListEntity , userId : String?){
        viewModelScope.launch(Dispatchers.IO) {

            userId?.let {
                val answer = firestoreRepository.deleteDataInPriceList(item , it)
                withContext(Dispatchers.Main){
                    synchronized(lock){
                        isSuccessfulDeleteInFirestore.value = answer
                    }
                }
            }

        }
    }

    // асинхронная функция получения всех элементов
    fun getAllPriceList(userId : String?){
        viewModelScope.launch(Dispatchers.IO) {

            userId?.let {
                databaseRoom = RoomRepository(getApplication()).database
                val answer = databaseRoom?.databasePriceListDao()?.getAllPriceList(it)
                withContext(Dispatchers.Main){
                    priceList.value = answer
                }
            }

        }
    }

}