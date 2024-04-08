package com.my.knowledge.model.database.Room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("MyAccountEntity")
data class MyAccountEntity(

    @PrimaryKey val idUser:String,
    @ColumnInfo("first_name")  val firstName:String,
    @ColumnInfo("last_name")   val lastName:String,
    @ColumnInfo("status")      val status:String,
    @ColumnInfo("description") val description:String,
    @ColumnInfo("experience")  val experience:String,
    @ColumnInfo("education")   val education:String,
    @ColumnInfo("url_photo")   val urlPhoto:String

){

    fun toHashMap(): HashMap<String, Any?> {
        return hashMapOf(
            "first_name"  to firstName,
            "last_name"   to lastName,
            "status"      to status,
            "description" to description,
            "experience"  to experience,
            "education"   to education,
            "url_photo"   to urlPhoto
        )
    }

}