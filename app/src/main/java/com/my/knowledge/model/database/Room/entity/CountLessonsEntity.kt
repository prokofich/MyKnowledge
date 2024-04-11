package com.my.knowledge.model.database.Room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity("CountLessonsEntity")
data class CountLessonsEntity(

    @PrimaryKey(autoGenerate = true)        val id:Int = 0,
    @ColumnInfo("id_user")            var  idUser:String,
    @ColumnInfo("count_in_monday")    var  countInMonday:Int,
    @ColumnInfo("count_in_tuesday")   var  countInTuesday:Int,
    @ColumnInfo("count_in_wednesday") var  countInWednesday:Int,
    @ColumnInfo("count_in_thursday")  var  countInThursday:Int,
    @ColumnInfo("count_in_friday")    var  countInFriday:Int,
    @ColumnInfo("count_in_saturday")  var  countInSaturday:Int,
    @ColumnInfo("count_in_sunday")    var  countInSunday:Int

):Parcelable