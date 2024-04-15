package com.my.knowledge.model.modelData

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelStudent(
    @PropertyName("first_name")   var firstName:String = "",
    @PropertyName("last_name")    var lastName:String = "",
    @PropertyName("status")       var status:String = "",
    @PropertyName("user_id")      var userId:String = "",
    @PropertyName("description")  var myDescription:String = "",
    @PropertyName("url_photo")    var urlPhoto:String = ""
) : Parcelable