package com.my.knowledge.model.modelAdapter.interfaces

import com.my.knowledge.model.database.Room.entity.PriceListEntity

interface PriceListInterface {
    fun savePrice(item:PriceListEntity)
    fun showToast(message:String?)

}