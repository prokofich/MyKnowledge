package com.my.knowledge.model.modelAdapter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.my.knowledge.R
import com.my.knowledge.model.modelData.ModelStudent

class SearchUsersStudentsAdapter() : RecyclerView.Adapter<SearchUsersStudentsAdapter.SearchUsersStudentsViewHolder>() {

    private var listStudents = mutableListOf<ModelStudent>()

    class SearchUsersStudentsViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : SearchUsersStudentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_students_rv_search,parent,false)
        return SearchUsersStudentsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listStudents.size
    }

    override fun onBindViewHolder(holder: SearchUsersStudentsViewHolder, position: Int) {

        val textViewFirstName = holder.itemView.findViewById <TextView> (R.id.id_item_teachers_rv_search_tv_first_name)
        val textViewLastName  = holder.itemView.findViewById <TextView> (R.id.id_item_teachers_rv_search_tv_last_name)

        textViewFirstName.text = listStudents[position].firstName
        textViewLastName.text  = listStudents[position].lastName

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListStudents(list: MutableList<ModelStudent>){
        listStudents = list
        notifyDataSetChanged()
    }

}