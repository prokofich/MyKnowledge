package com.my.knowledge.model.database.Room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude

@Entity(tableName = "PriceListEntity")
data class PriceListEntity(
    @PrimaryKey(autoGenerate = true) val id:Long = 0,
    @ColumnInfo(name = "name")val name:String,
    @ColumnInfo(name = "price")val price:String,
    @ColumnInfo(name = "desc")val desc:String
){
    fun toHashMap(): HashMap<String, Any?> {
        return hashMapOf(
            "id" to id,
            "name" to name,
            "price" to price,
            "desc" to desc
        )
    }

}