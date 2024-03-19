package com.my.knowledge.model.modelAdapter.interfaces

import com.my.knowledge.model.database.Room.entity.PriceListEntity

interface PriceListInterface {

    fun savePrice(item:PriceListEntity) // функция сохранения элемента в прайс-листе

    fun updatePrice(item: PriceListEntity) // функция обновления элемента в прайс-листе

    fun deletePrice(item: PriceListEntity) // функция удаления элемента в прайс-листе

    fun showToast(message:String?) // функция показа всплывающего сообщения

    fun showDialog(item: PriceListEntity) // функция показа диалогового сообщения

}