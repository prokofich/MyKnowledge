package com.my.knowledge.model.database.Room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.my.knowledge.model.database.Room.dao.CountLessonsDao
import com.my.knowledge.model.database.Room.dao.MyAccountDao
import com.my.knowledge.model.database.Room.dao.PriceListDao
import com.my.knowledge.model.database.Room.dao.TimeTableDao
import com.my.knowledge.model.database.Room.entity.CountLessonsEntity
import com.my.knowledge.model.database.Room.entity.MyAccountEntity
import com.my.knowledge.model.database.Room.entity.MyTeachersEntity
import com.my.knowledge.model.database.Room.entity.PriceListEntity
import com.my.knowledge.model.database.Room.entity.TimeTableEntity

@Database(entities = [
    PriceListEntity    :: class,
    TimeTableEntity    :: class,
    MyAccountEntity    :: class,
    CountLessonsEntity :: class,
    MyTeachersEntity   :: class],
    version = 20)
abstract class AppDatabase:RoomDatabase() {
    abstract fun databasePriceListDao    () : PriceListDao
    abstract fun databaseTimeTableDao    () : TimeTableDao
    abstract fun databaseMyAccountDao    () : MyAccountDao
    abstract fun databaseCountLessonsDao () : CountLessonsDao
}