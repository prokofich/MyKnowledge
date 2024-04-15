package com.my.knowledge.model.modelData

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelStudentForSearch(
    val studentId : String,
    val firstName : String,
    val lastName  : String,
    val urlImage  : String,
) : Parcelable