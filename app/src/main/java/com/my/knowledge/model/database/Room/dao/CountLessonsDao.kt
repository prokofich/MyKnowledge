package com.my.knowledge.model.database.Room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.my.knowledge.model.database.Room.entity.CountLessonsEntity

@Dao
interface CountLessonsDao {

    @Query("SELECT * FROM CountLessonsEntity WHERE id_user = :id")
    fun getCountLessonsByIdUser(id : String) : CountLessonsEntity

    @Insert
    fun insertCountLessons(item : CountLessonsEntity)

    @Delete
    fun deleteCountLessons(item : CountLessonsEntity)

    @Update
    fun updateCountLessons(item : CountLessonsEntity)

}