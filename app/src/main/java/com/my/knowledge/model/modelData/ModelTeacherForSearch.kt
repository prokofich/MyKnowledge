package com.my.knowledge.model.modelData

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelTeacherForSearch(
    val teacherId : String,
    val firstName : String,
    val lastName  : String,
    val urlImage  : String,
    val listNamePredmets : MutableList<String>
) : Parcelable