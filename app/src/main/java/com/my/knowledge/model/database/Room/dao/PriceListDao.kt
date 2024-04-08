package com.my.knowledge.model.database.Room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.my.knowledge.model.database.Room.entity.PriceListEntity

@Dao
interface PriceListDao {

    @Query("SELECT * FROM PriceListEntity WHERE idUser = :userId")
    fun getAllPriceList(userId:String):List<PriceListEntity>

    @Delete
    fun deletePrice(item: PriceListEntity)

    @Update
    fun updatePrice(item: PriceListEntity)

    @Insert
    fun insertPrice(item: PriceListEntity):Long

}