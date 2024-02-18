package com.my.knowledge.model.modelData

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelTeacher(
    var firstName:String? = "",
    var lastName:String? = "",
    var status:String? = "",
    var userId:String? = "",
    val myDescription:String? = "",
    var experience:String? = "",
    var education:String? = "",
    var myRating:String? = ""
):Parcelable