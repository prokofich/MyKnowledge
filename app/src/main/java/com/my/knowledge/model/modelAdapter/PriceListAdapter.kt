package com.my.knowledge.model.modelAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.my.knowledge.R

class PriceListAdapter: RecyclerView.Adapter<PriceListAdapter.PriceListViewHolder>() {

    private val listPrice = mutableListOf<String>()
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
        val imageViewDelete = holder.itemView.findViewById<ImageView>(R.id.id_item_price_iv_delete)
        val buttonRedact = holder.itemView.findViewById<Button>(R.id.id_item_price_button_redact)



    }

}