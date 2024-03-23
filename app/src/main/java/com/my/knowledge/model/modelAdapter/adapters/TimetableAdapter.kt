package com.my.knowledge.model.modelAdapter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.my.knowledge.R
import com.my.knowledge.model.database.Room.entity.TimeTableEntity

class TimetableAdapter : RecyclerView.Adapter<TimetableAdapter.TimetableViewHolder>() {

    private var listTimetable = mutableListOf<TimeTableEntity>()

    class TimetableViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimetableViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_time_table,parent,false)
        return TimetableViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTimetable.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TimetableViewHolder, position: Int) {

        val editTextName = holder.itemView.findViewById<EditText>(R.id.id_item_time_table_et_name)
        val editTextStudent = holder.itemView.findViewById<EditText>(R.id.id_item_time_table_et_student)
        val editTextPrice = holder.itemView.findViewById<EditText>(R.id.id_item_time_table_et_price)

        val editTextTimeStartHours = holder.itemView.findViewById<EditText>(R.id.id_item_time_table_cs_2_tv_time_1_hour)
        val editTextTimeStartMinute = holder.itemView.findViewById<EditText>(R.id.id_item_time_table_cs_2_tv_time_1_minute)
        val editTextTimeEndHours = holder.itemView.findViewById<EditText>(R.id.id_item_time_table_cs_2_tv_time_2_hour)
        val editTextTimeEndMinute = holder.itemView.findViewById<EditText>(R.id.id_item_time_table_cs_2_tv_time_2_minute)

        editTextName.setText(listTimetable[position].nameLesson)
        editTextStudent.setText("студент: ${listTimetable[position].student}")
        editTextPrice.setText(listTimetable[position].price)

        editTextTimeStartHours.setText(listTimetable[position].startTime.substring(0,1))
        editTextTimeStartMinute.setText(listTimetable[position].startTime.substring(2,3))

        editTextTimeEndHours.setText(listTimetable[position].endTime.substring(0,1))
        editTextTimeEndMinute.setText(listTimetable[position].endTime.substring(2,3))

    }

    override fun onViewAttachedToWindow(holder: TimetableViewHolder) {
        super.onViewAttachedToWindow(holder)



    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: MutableList<TimeTableEntity>){
        listTimetable = list
        notifyDataSetChanged()
    }

}