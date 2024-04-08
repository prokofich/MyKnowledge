package com.my.knowledge.model.database.Room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("CountLessonsEntity")
data class CountLessonsEntity(

    @PrimaryKey(autoGenerate = true)        val id:Int = 0,
    @ColumnInfo("id_user")            val  idUser:String,
    @ColumnInfo("count_in_monday")    val  countInMonday:Int,
    @ColumnInfo("count_in_tuesday")   val  countInTuesday:Int,
    @ColumnInfo("count_in_wednesday") val  countInWednesday:Int,
    @ColumnInfo("count_in_thursday")  val  countInThursday:Int,
    @ColumnInfo("count_in_friday")    val  countInFriday:Int,
    @ColumnInfo("count_in_saturday")  val  countInSaturday:Int,
    @ColumnInfo("count_in_sunday")    val  countInSunday:Int

)