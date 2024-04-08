package com.my.knowledge.model.modelAdapter.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.my.knowledge.databinding.ItemPriceTeacherBinding
import com.my.knowledge.model.constant.OperationStatus
import com.my.knowledge.model.database.Room.entity.PriceListEntity
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences
import com.my.knowledge.model.modelAdapter.interfaces.PriceListInterface
import com.my.knowledge.model.repository.Repository

class PriceListAdapter(private val interfaceAdapter:PriceListInterface,context: Context): RecyclerView.Adapter<PriceListAdapter.PriceListViewHolder>() {

    lateinit var binding:ItemPriceTeacherBinding
    private var listPriceEntity = mutableListOf<PriceListEntity>()
    private var isOpenEditText = false
    private var repository = Repository()
    private var answer:String? = null
    private var idUser = SharedPreferences(context).getUserId()

    class PriceListViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceListViewHolder {
        binding = ItemPriceTeacherBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PriceListViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return listPriceEntity.size
    }

    override fun onBindViewHolder(holder: PriceListViewHolder, position: Int) {
        binding.idItemPriceEtName.setText(listPriceEntity[position].name)
        binding.idItemPriceEtPrice.setText(listPriceEntity[position].price)
        binding.idItemPriceEtDesc.setText(listPriceEntity[position].desc)
    }

    override fun onViewAttachedToWindow(holder: PriceListViewHolder) {
        super.onViewAttachedToWindow(holder)

        val bindingItem = binding

        bindingItem.idItemPriceButtonRedact.setOnClickListener {
            if(bindingItem.idItemPriceButtonRedact.text == "редактировать"){

                bindingItem.idItemPriceButtonRedact.text = "сохранить"
                bindingItem.idItemPriceIvDelete.isVisible = true
                openOrClosedEditText(true)
                bindingItem.idItemPriceEtName.isEnabled = isOpenEditText
                bindingItem.idItemPriceEtPrice.isEnabled = isOpenEditText
                bindingItem.idItemPriceEtDesc.isEnabled = isOpenEditText

            }else{

                answer = repository.checkInputPriceData(
                    bindingItem.idItemPriceEtName.text.toString(),
                    bindingItem.idItemPriceEtPrice.text.toString(),
                    bindingItem.idItemPriceEtDesc.text.toString()
                )

                if(answer == OperationStatus.Correct.status){

                    val itemPriceList = PriceListEntity(
                        id = listPriceEntity[holder.adapterPosition].id,
                        name = bindingItem.idItemPriceEtName.text.toString(),
                        price = bindingItem.idItemPriceEtPrice.text.toString(),
                        desc = bindingItem.idItemPriceEtDesc.text.toString(),
                        idUser = idUser
                    )

                    interfaceAdapter.updatePrice(itemPriceList,holder.adapterPosition)

                    bindingItem.idItemPriceButtonRedact.text = "редактировать"
                    bindingItem.idItemPriceIvDelete.isVisible = false
                    openOrClosedEditText(false)
                    bindingItem.idItemPriceEtName.isEnabled = isOpenEditText
                    bindingItem.idItemPriceEtPrice.isEnabled = isOpenEditText
                    bindingItem.idItemPriceEtDesc.isEnabled = isOpenEditText

                }else{ interfaceAdapter.showToast(answer) }

            }

        }

        bindingItem.idItemPriceIvDelete.setOnClickListener {
            interfaceAdapter.showDialog(holder.adapterPosition)
        }

    }

    private fun openOrClosedEditText(isOpen:Boolean){
        isOpenEditText = isOpen
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItemsInList(list:MutableList<PriceListEntity>){
        listPriceEntity = list
        notifyDataSetChanged()
    }

    // добавление элемента в прокручиваемый список
    fun addItemInList(item:PriceListEntity){
        listPriceEntity.add(item)
        notifyItemChanged(listPriceEntity.lastIndex)
    }

    // обновление элемента в прокручиваемом списке
    fun updateItemInList(item:PriceListEntity,indexItem:Int){
        listPriceEntity[indexItem] = item
        notifyItemChanged(indexItem)
    }

    // удаление элемента из прокручиваемого списка
    fun deleteItemInList(indexItem:Int){
        listPriceEntity.removeAt(indexItem)
        notifyItemRemoved(indexItem)
        notifyItemRangeChanged(indexItem, listPriceEntity.size)
    }

}