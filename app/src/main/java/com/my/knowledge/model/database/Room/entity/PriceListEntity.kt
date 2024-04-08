package com.my.knowledge.model.database.Room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude

@Entity("PriceListEntity")
data class PriceListEntity(
    @PrimaryKey(autoGenerate = true) var id:Long = 0,
    @ColumnInfo("idUser")      val idUser:String?,
    @ColumnInfo("name")        val name:String,
    @ColumnInfo("price")       val price:String,
    @ColumnInfo("desc")        val desc:String
){
    fun toHashMap(): HashMap<String, Any?> {
        return hashMapOf(
            "id"    to  id,
            "name"  to  name,
            "price" to  price,
            "desc"  to  desc
        )
    }

}