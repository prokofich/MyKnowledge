package com.my.knowledge.model.modelAdapter.interfaces

import com.my.knowledge.model.database.Room.entity.PriceListEntity

interface PriceListInterface {

    fun updatePrice(item: PriceListEntity,indexItem: Int) // функция обновления элемента

    fun showDialog(indexItem:Int) // функция показа диалогового сообщения

    fun showToast(message:String?) // функция показа всплывающего сообщения
}