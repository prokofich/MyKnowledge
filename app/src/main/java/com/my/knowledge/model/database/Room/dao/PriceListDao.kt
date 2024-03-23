package com.my.knowledge.model.database.Room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.my.knowledge.model.database.Room.entity.PriceListEntity

@Dao
interface PriceListDao {

    @Query("SELECT * FROM PriceListEntity")
    fun getAllPriceList():List<PriceListEntity>

    @Query("SELECT * FROM PriceListEntity ORDER BY id DESC LIMIT 1")
    fun getLastPrice(): PriceListEntity

    @Delete
    fun deletePrice(item: PriceListEntity)

    @Update
    fun updatePrice(item: PriceListEntity)

    @Insert
    fun insertPrice(item: PriceListEntity)

}