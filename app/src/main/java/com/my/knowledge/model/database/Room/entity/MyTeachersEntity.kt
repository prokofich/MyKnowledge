package com.my.knowledge.model.database.Room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("MyTeachersEntity")
data class MyTeachersEntity (

    @PrimaryKey val id : Long,
    @ColumnInfo("id_user")     val idUser : String = "",
    @ColumnInfo("id_teachers") val idTeacher : String = ""

){
    fun toHashMap() : HashMap<String , Any?> {
        return hashMapOf(
            "id"           to id,
            "id_teachers"  to idTeacher,
        )
    }

}