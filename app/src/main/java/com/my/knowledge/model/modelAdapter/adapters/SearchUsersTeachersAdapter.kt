package com.my.knowledge.model.modelAdapter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.my.knowledge.R
import com.my.knowledge.model.modelData.ModelTeacher

class SearchUsersTeachersAdapter() : RecyclerView.Adapter<SearchUsersTeachersAdapter.SearchUsersTeachersViewHolder>() {

    private var listTeachers = mutableListOf <ModelTeacher> ()
    private var listPredmets = mutableListOf <MutableList<String>> ()

    class SearchUsersTeachersViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUsersTeachersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teachers_rv_search,parent,false)
        return SearchUsersTeachersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTeachers.size
    }

    override fun onBindViewHolder(holder: SearchUsersTeachersViewHolder, position: Int) {

        val textViewFirstName = holder.itemView.findViewById <TextView> (R.id.id_item_teachers_rv_search_tv_first_name)
        val textViewLastName  = holder.itemView.findViewById <TextView> (R.id.id_item_teachers_rv_search_tv_last_name)
        val textViewPredmets  = holder.itemView.findViewById <TextView> (R.id.id_item_teachers_rv_search_tv_predmeti)

        textViewFirstName.text = listTeachers[position].firstName
        textViewLastName.text  = listTeachers[position].lastName
        textViewPredmets.text  = listPredmets[position].toString()

    }


    fun setListTeachers(list : MutableList<ModelTeacher>){
        listTeachers = list
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListPredmets(list : MutableList<MutableList<String>>){
        listPredmets = list
        notifyDataSetChanged()
    }

}