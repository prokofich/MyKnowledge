package com.my.knowledge.model.database.Room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("TimeTableEntity")
data class TimeTableEntity(
    @PrimaryKey(autoGenerate = true) var  id:Long = 0,       // ID записи в базе данных
    @ColumnInfo("user_id")      val userId:String?,     // ID учителя в базе данных
    @ColumnInfo("name_lesson")  val  nameLesson:String, // название учебной дисциплины
    @ColumnInfo("price")        val  price:Int,         // цена услуги
    @ColumnInfo("start_time")   val  startTime:String,  // время начало занятия
    @ColumnInfo("end_time")     val  endTime:String,    // время окончания занятия
    @ColumnInfo("student")      val  student:String,    // ФИО студента
    @ColumnInfo("student_id")   val  studentId:String,  // ID студента в базе данных
    @ColumnInfo("day_week")     val  dayWeek:String     // день недели проведения занятия
){

    fun toHashMap() : HashMap<String , Any?> {
        return hashMapOf(
            "id"          to  id,
            "user_id"     to  userId,
            "name_lesson" to  nameLesson,
            "price"       to  price,
            "start_time"  to  startTime,
            "end_time"    to  endTime,
            "student"     to  student,
            "student_id"  to  studentId,
            "day_week"    to  dayWeek
        )
    }

}
