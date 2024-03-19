package com.my.knowledge.model.database.Room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("TimeTableEntity")
data class TimeTableEntity(
    @PrimaryKey(autoGenerate = true) val id:Long = 0,
    @ColumnInfo(name = "nameLesson")  val nameLesson:String,
    @ColumnInfo(name = "price") val price:Int,
    @ColumnInfo(name = "startTime")  val startTime:String,
    @ColumnInfo(name = "endTime") val endTime:String,
    @ColumnInfo(name = "student") val student:String,
    @ColumnInfo(name = "studentId") val studentId:String,
    @ColumnInfo(name = "dayWeek") val dayWeek:String
)
