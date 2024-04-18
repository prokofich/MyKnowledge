package com.my.knowledge.model.modelData

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelTeacher(
    @PropertyName("first_name")   var firstName:String = "",
    @PropertyName("last_name")    var lastName:String = "",
    @PropertyName("status")       var status:String = "",
    @PropertyName("id_user")      var userId:String = "",
    @PropertyName("description")  var myDescription:String = "",
    @PropertyName("experience")   var experience:String = "",
    @PropertyName("education")    var education:String = "",
    @PropertyName("url_photo")    var urlPhoto:String = ""
):Parcelable