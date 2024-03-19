package com.my.knowledge.model.modelAdapter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.my.knowledge.R
import com.my.knowledge.model.database.Room.entity.PriceListEntity
import com.my.knowledge.model.modelAdapter.interfaces.PriceListInterface
import com.my.knowledge.model.repository.Repository

class PriceListAdapter(private val interfaceAdapter:PriceListInterface): RecyclerView.Adapter<PriceListAdapter.PriceListViewHolder>() {

    private var listPrice = mutableListOf<PriceListEntity>()
    private var isOpenEditText = false
    private var repository = Repository()
    private var answer:String? = null

    class PriceListViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_price_teacher,parent,false)
        return PriceListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPrice.size
    }

    override fun onBindViewHolder(holder: PriceListViewHolder, position: Int) {

        val textViewName = holder.itemView.findViewById<TextView>(R.id.id_item_price_et_name)
        val textViewPrice = holder.itemView.findViewById<TextView>(R.id.id_item_price_et_price)
        val textViewDescription = holder.itemView.findViewById<TextView>(R.id.id_item_price_et_desc)

        textViewName.text = listPrice[position].name
        textViewPrice.text = listPrice[position].price
        textViewDescription.text = listPrice[position].desc

    }

    override fun onViewAttachedToWindow(holder: PriceListViewHolder) {
        super.onViewAttachedToWindow(holder)

        val name = holder.itemView.findViewById<EditText>(R.id.id_item_price_et_name)
        val price = holder.itemView.findViewById<EditText>(R.id.id_item_price_et_price)
        val description = holder.itemView.findViewById<EditText>(R.id.id_item_price_et_desc)
        val redact = holder.itemView.findViewById<Button>(R.id.id_item_price_button_redact)
        val delete = holder.itemView.findViewById<ImageView>(R.id.id_item_price_iv_delete)

        redact.setOnClickListener {
            if(redact.text == "редактировать"){

                redact.text = "сохранить"
                delete.isVisible = true
                openOrClosedEditText(true)
                name.isEnabled = isOpenEditText
                price.isEnabled = isOpenEditText
                description.isEnabled = isOpenEditText

            }else{
                answer = repository.checkInputPriceData(name.text.toString(), price.text.toString(), description.text.toString())
                if(answer == "верно"){

                    if(listPrice[holder.adapterPosition].id.toInt() == 0){
                        interfaceAdapter.savePrice(
                            PriceListEntity(
                                name = name.text.toString(),
                                price = price.text.toString(),
                                desc = description.text.toString())
                        )
                    }else{
                        interfaceAdapter.updatePrice(
                            PriceListEntity(
                                id = listPrice[holder.adapterPosition].id,
                                name = name.text.toString(),
                                price = price.text.toString(),
                                desc = description.text.toString())
                        )
                    }

                    redact.text = "редактировать"
                    delete.isVisible = false
                    openOrClosedEditText(false)
                    name.isEnabled = isOpenEditText
                    price.isEnabled = isOpenEditText
                    description.isEnabled = isOpenEditText

                }else{ interfaceAdapter.showToast(answer) }

            }

        }

        delete.setOnClickListener {
            interfaceAdapter.showDialog(
                PriceListEntity(
                    id = listPrice[holder.adapterPosition].id,
                    name = name.text.toString(),
                    price = price.text.toString(),
                    desc = description.text.toString()
                )
            )
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListPrice(list:MutableList<PriceListEntity>){
        listPrice = list
        notifyDataSetChanged()
    }

    private fun openOrClosedEditText(isOpen:Boolean){
        isOpenEditText = isOpen
    }

}