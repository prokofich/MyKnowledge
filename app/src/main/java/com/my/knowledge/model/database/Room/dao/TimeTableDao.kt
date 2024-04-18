package com.my.knowledge.model.database.Room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.my.knowledge.model.database.Room.entity.TimeTableEntity

@Dao
interface TimeTableDao {

    @Query("SELECT * FROM TimeTableEntity WHERE day_week = :dayOfWeek AND user_id = :userId")
    fun getListTimeTableByDayOfWeek(dayOfWeek : String , userId : String) : List <TimeTableEntity>

    @Query("SELECT COUNT(*) FROM TimeTableEntity WHERE day_week = :dayOfWeek")
    fun getCountTimeTableByDayOfWeek(dayOfWeek : String) : Int

    @Insert
    fun insertTimeTableItem(item : TimeTableEntity) : Long

    @Delete
    fun deleteTimeTableItem(item : TimeTableEntity)

    @Update
    fun updateTimeTableItem(item : TimeTableEntity)

}