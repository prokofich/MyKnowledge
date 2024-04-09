package com.my.knowledge.model.database.Room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("TimeTableEntity")
data class TimeTableEntity(
    @PrimaryKey(autoGenerate = true) val  id:Long = 0,
    @ColumnInfo("user_id")      val userId:String,
    @ColumnInfo("name_lesson")  val  nameLesson:String,
    @ColumnInfo("price")        val  price:Int,
    @ColumnInfo("start_time")   val  startTime:String,
    @ColumnInfo("end_time")     val  endTime:String,
    @ColumnInfo("student")      val  student:String,
    @ColumnInfo("student_id")   val  studentId:String,
    @ColumnInfo("day_week")     val  dayWeek:String
){

    fun toHashMap(): HashMap<String, Any?> {
        return hashMapOf(
            "id"          to  id,
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
