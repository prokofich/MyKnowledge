package com.my.knowledge.model.database.Room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.my.knowledge.model.database.Room.entity.MyTeachersEntity

interface MyTeachersDao {

    @Query("SELECT * FROM PriceListEntity WHERE id_user = :userId")
    fun getTeachers(userId : String) : List <MyTeachersEntity>

    @Delete
    fun deletePrice(item : MyTeachersEntity)

    @Insert
    fun insertTeachers(item : MyTeachersEntity)

}