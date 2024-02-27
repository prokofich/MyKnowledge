package com.my.knowledge.model.modelData

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelTeacher(
    var first_name:String = "",
    var last_name:String = "",
    var status:String = "",
    var user_id:String = "",
    var myDescription:String = "",
    var experience:String = "",
    var education:String = "",
    var myRating:String = ""
):Parcelable