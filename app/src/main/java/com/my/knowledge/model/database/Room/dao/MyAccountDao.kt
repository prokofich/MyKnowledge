package com.my.knowledge.model.database.Room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.my.knowledge.model.database.Room.entity.MyAccountEntity

@Dao
interface MyAccountDao {

    @Query("SELECT * FROM MyAccountEntity WHERE idUser = :userId")
    fun getAccountById(userId : String) : MyAccountEntity

    @Delete
    fun deleteAccount(item : MyAccountEntity)

    @Update
    fun updateAccount(item : MyAccountEntity)

    @Insert
    fun insertAccount(item : MyAccountEntity)

}