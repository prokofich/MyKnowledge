package com.my.knowledge.model.modelData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelPriceList(
    var name:String,
    var price:String,
    var desc:String
):Parcelable