package com.my.knowledge.model.modelData

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelUser(
    var firstName:String,
    var lastName:String,
    var status:String,
    var userId:String
):Parcelable
