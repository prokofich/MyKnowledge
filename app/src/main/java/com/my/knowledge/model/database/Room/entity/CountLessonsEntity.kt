package com.my.knowledge.model.database.Room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity("CountLessonsEntity")
data class CountLessonsEntity(

    @PrimaryKey(autoGenerate = true)        val id:Int = 0,            // ID записи в базе данных
    @ColumnInfo("id_user")            var  idUser:String,        // ID пользователя в базе данных
    @ColumnInfo("count_in_monday")    var  countInMonday:Int,    // количество занятий в понедельник
    @ColumnInfo("count_in_tuesday")   var  countInTuesday:Int,   // количество занятий во вторник
    @ColumnInfo("count_in_wednesday") var  countInWednesday:Int, // количество занятий в среду
    @ColumnInfo("count_in_thursday")  var  countInThursday:Int,  // количество занятий в четверг
    @ColumnInfo("count_in_friday")    var  countInFriday:Int,    // количество занятий в пятницу
    @ColumnInfo("count_in_saturday")  var  countInSaturday:Int,  // количество занятий в субботу
    @ColumnInfo("count_in_sunday")    var  countInSunday:Int     // количество занятий в воскресенье

):Parcelable