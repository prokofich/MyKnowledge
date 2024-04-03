package com.my.knowledge.model.modelData

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelTeacher(
    @PropertyName("first_name") var firstName:String = "",
    @PropertyName("last_name") var lastName:String = "",
    @PropertyName("status") var status:String = "",
    @PropertyName("user_id") var userId:String = "",
    @PropertyName("myDescription") var myDescription:String = "",
    @PropertyName("experience") var experience:String = "",
    @PropertyName("education") var education:String = "",
    @PropertyName("myRating") var myRating:String = ""
):Parcelable