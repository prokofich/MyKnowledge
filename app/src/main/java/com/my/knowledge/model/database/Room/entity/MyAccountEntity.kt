package com.my.knowledge.model.database.Room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("MyAccountEntity")
data class MyAccountEntity(

    @PrimaryKey val idUser:String,                           // ID пользователя в базе данных
    @ColumnInfo("first_name")  val firstName:String,   // имя пользователя
    @ColumnInfo("last_name")   val lastName:String,    // фамилия пользователя
    @ColumnInfo("status")      val status:String,      // статус(ученик или учитель)
    @ColumnInfo("description") val description:String, // описание пользователя
    @ColumnInfo("experience")  val experience:String,  // описание опыта работы пользователя
    @ColumnInfo("education")   val education:String,   // описание образования пользователя
    @ColumnInfo("url_photo")   val urlPhoto:String     // url адрес аватарки в профиле

){

    fun toHashMap() : HashMap<String , Any?> {
        return hashMapOf(
            "user_id"     to idUser, 
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