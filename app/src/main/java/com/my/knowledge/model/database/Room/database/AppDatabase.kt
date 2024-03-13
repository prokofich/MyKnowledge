package com.my.knowledge.model.database.Room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.my.knowledge.model.database.Room.dao.PriceListDao
import com.my.knowledge.model.database.Room.entity.PriceListEntity

@Database(entities = [PriceListEntity::class], version = 4)
abstract class AppDatabase:RoomDatabase() {
    abstract fun databaseDao():PriceListDao
}