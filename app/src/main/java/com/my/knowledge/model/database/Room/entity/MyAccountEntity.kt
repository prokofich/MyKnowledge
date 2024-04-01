package com.my.knowledge.model.database.Room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MyAccountEntity")
data class MyAccountEntity(
    @PrimaryKey val idUser:String,
    @ColumnInfo(name = "firstName") val firstName:String,
    @ColumnInfo(name = "lastName") val lastName:String,
    @ColumnInfo(name = "status") val status:String,
    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "experience") val experience:String,
    @ColumnInfo(name = "education") val education:String
)