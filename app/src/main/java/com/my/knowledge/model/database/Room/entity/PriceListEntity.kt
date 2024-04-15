package com.my.knowledge.model.database.Room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude

@Entity("PriceListEntity")
data class PriceListEntity(
    @PrimaryKey(autoGenerate = true) var id:Long = 0,   // ID записи в базе данных
    @ColumnInfo("idUser")      val idUser:String?, // ID учителя в базе данных
    @ColumnInfo("name")        val name:String,    // название услуги
    @ColumnInfo("price")       val price:String,   // цена услуги
    @ColumnInfo("desc")        val desc:String     // описание услуги
){
    fun toHashMap() : HashMap<String , Any?> {
        return hashMapOf(
            "id"      to  id,
            "user_id" to  idUser,
            "name"    to  name,
            "price"   to  price,
            "desc"    to  desc
        )
    }

}