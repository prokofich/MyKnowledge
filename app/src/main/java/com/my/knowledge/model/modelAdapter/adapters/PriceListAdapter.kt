package com.my.knowledge.model.modelAdapter.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.my.knowledge.R
import com.my.knowledge.databinding.ItemPriceTeacherBinding
import com.my.knowledge.model.constant.OperationStatus
import com.my.knowledge.model.database.Room.entity.PriceListEntity
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences
import com.my.knowledge.model.modelAdapter.interfaces.PriceListInterface
import com.my.knowledge.model.repository.Repository

class PriceListAdapter(private val interfaceAdapter:PriceListInterface,context: Context) : RecyclerView.Adapter<PriceListAdapter.PriceListViewHolder>() {

    private var listPriceEntity = mutableListOf<PriceListEntity>()
    private var isOpenEditText = false
    private var repository = Repository()
    private var answer:String? = null
    private var idUser = SharedPreferences(context).getUserId()

    class PriceListViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_price_teacher,parent,false)
        return PriceListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPriceEntity.size
    }

    override fun onBindViewHolder(holder: PriceListViewHolder, position: Int) {

        val editTextName = holder.itemView.findViewById<EditText>(R.id.id_item_price_et_name)
        val editTextPrice = holder.itemView.findViewById<EditText>(R.id.id_item_price_et_price)
        val editTextDesc = holder.itemView.findViewById<EditText>(R.id.id_item_price_et_desc)

        editTextName.setText(listPriceEntity[position].name)
        editTextPrice.setText(listPriceEntity[position].price)
        editTextDesc.setText(listPriceEntity[position].desc)
        
    }

    override fun onViewAttachedToWindow(holder: PriceListViewHolder) {
        super.onViewAttachedToWindow(holder)

        val editTextName = holder.itemView.findViewById<EditText>(R.id.id_item_price_et_name)
        val editTextPrice = holder.itemView.findViewById<EditText>(R.id.id_item_price_et_price)
        val editTextDesc = holder.itemView.findViewById<EditText>(R.id.id_item_price_et_desc)
        val buttonRedact = holder.itemView.findViewById<Button>(R.id.id_item_price_button_redact)
        val imageViewDelete = holder.itemView.findViewById<ImageView>(R.id.id_item_price_iv_delete)

        buttonRedact.setOnClickListener {
            if(buttonRedact.text == "редактировать"){

                buttonRedact.text = "сохранить"
                imageViewDelete.isVisible = true
                openOrClosedEditText(true)
                editTextName.isEnabled = isOpenEditText
                editTextPrice.isEnabled = isOpenEditText
                editTextDesc.isEnabled = isOpenEditText

            }else{

                answer = repository.checkInputPriceData(
                    name = editTextName.text.toString(),
                    price = editTextPrice.text.toString(),
                    desc = editTextDesc.text.toString()
                )

                if(answer == OperationStatus.Correct.status){

                    val itemPriceList = PriceListEntity(
                        id = listPriceEntity[holder.adapterPosition].id,
                        name = editTextName.text.toString(),
                        price = editTextPrice.text.toString(),
                        desc = editTextDesc.text.toString(),
                        idUser = idUser
                    )

                    interfaceAdapter.updatePrice(itemPriceList,holder.adapterPosition)

                    buttonRedact.text = "редактировать"
                    imageViewDelete.isVisible = false
                    openOrClosedEditText(false)
                    editTextName.isEnabled = isOpenEditText
                    editTextPrice.isEnabled = isOpenEditText
                    editTextDesc.isEnabled = isOpenEditText

                }else{ interfaceAdapter.showToast(answer) }

            }

        }

        imageViewDelete.setOnClickListener {
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