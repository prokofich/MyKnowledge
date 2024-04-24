package com.my.knowledge.model.modelData

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelPriceList(
    @PropertyName("id")      var id : Long,
    @PropertyName("id_user") var idTeacher : String,
    @PropertyName("name")    var name : String,
    @PropertyName("price")   var price : String,
    @PropertyName("desc")    var desc : String
):Parcelable