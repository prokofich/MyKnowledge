package com.my.knowledge.model.modelAdapter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.my.knowledge.R
import com.my.knowledge.model.modelData.ModelPriceList

class PriceListInProfileAdapter() : RecyclerView.Adapter<PriceListInProfileAdapter.PriceListInProfileViewHolder>() {

    private var listPrice = mutableListOf<ModelPriceList>()

    class PriceListInProfileViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent : ViewGroup , viewType: Int) : PriceListInProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile_teacher_price,parent,false)
        return PriceListInProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder : PriceListInProfileViewHolder , position: Int) {

        val textViewName = holder.itemView.findViewById<TextView> (R.id.id_item_profile_teacher_tv_name)
        val textViewPrice = holder.itemView.findViewById<TextView>(R.id.id_item_profile_teacher_tv_price)

        textViewName.text = listPrice[position].name
        textViewPrice.text = listPrice[position].price

    }

    override fun getItemCount() : Int {
        return listPrice.size
    }

    override fun onViewAttachedToWindow(holder: PriceListInProfileViewHolder) {
        super.onViewAttachedToWindow(holder)

        val textViewTitleDesc = holder.itemView.findViewById<TextView>(R.id.id_item_profile_teacher_tv_title_desc)
        val textViewDesc = holder.itemView.findViewById<TextView>(R.id.id_item_profile_teacher_tv_desc)

        textViewTitleDesc.setOnClickListener {
            if(textViewTitleDesc.text == "показать описание..."){

                textViewTitleDesc.text = "скрыть описание..."
                textViewDesc.isVisible = true
                textViewDesc.text = listPrice[holder.adapterPosition].desc

            }else{

                textViewTitleDesc.text = "показать описание..."
                textViewDesc.isVisible = false

            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list : MutableList<ModelPriceList>){
        listPrice = list
        notifyDataSetChanged()
    }

}