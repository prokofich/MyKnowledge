package com.my.knowledge.model.modelData

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelUser(
    var firstName:String,
    var lastName:String,
    var status:String,
    var userId:String,
    var urlPhoto:String
):Parcelable{

    fun toHashMap(): HashMap<String, Any?> {
        return hashMapOf(
            "id"    to firstName,
            "name"  to lastName,
            "price" to status,
            "desc"  to userId
        )
    }

}
