package com.my.knowledge.model.database.Room.repository

import androidx.room.Room
import com.my.knowledge.model.database.Room.database.AppDatabase
import android.content.Context

class RoomRepository(private val context:Context) {

    val database = Room.databaseBuilder(context, AppDatabase::class.java, "MyDatabase").build()

}