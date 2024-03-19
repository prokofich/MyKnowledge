package com.my.knowledge.model.database.Room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.my.knowledge.model.database.Room.dao.PriceListDao
import com.my.knowledge.model.database.Room.dao.TimeTableDao
import com.my.knowledge.model.database.Room.entity.PriceListEntity
import com.my.knowledge.model.database.Room.entity.TimeTableEntity

@Database(entities = [PriceListEntity::class,TimeTableEntity::class],version = 9)
abstract class AppDatabase:RoomDatabase() {
    abstract fun databasePriceListDao():PriceListDao
    abstract fun databaseTimeTableDao():TimeTableDao
}