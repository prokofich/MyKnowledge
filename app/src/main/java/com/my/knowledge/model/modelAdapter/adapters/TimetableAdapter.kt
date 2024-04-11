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
import com.my.knowledge.model.constant.OperationStatus
import com.my.knowledge.model.database.Room.entity.PriceListEntity
import com.my.knowledge.model.database.Room.entity.TimeTableEntity
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences
import com.my.knowledge.model.modelAdapter.interfaces.PriceListInterface
import com.my.knowledge.model.modelAdapter.interfaces.TimeTableInterface
import com.my.knowledge.model.repository.Repository

class TimetableAdapter(private val interfaceAdapter: TimeTableInterface, context: Context) : RecyclerView.Adapter<TimetableAdapter.TimetableViewHolder>() {

    private var isOpenEditText = false
    private var answer = ""
    private var repository = Repository()
    private var dayOfWeek:String = ""
    private var listTimetable = mutableListOf<TimeTableEntity>()
    private var userId = SharedPreferences(context).getUserId()

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
        editTextPrice.setText(listTimetable[position].price.toString())

        if(listTimetable[position].startTime.isNotEmpty() && listTimetable[position].endTime.isNotEmpty()){

            editTextTimeStartHours.setText(listTimetable[position].startTime.substring(0,2))
            editTextTimeStartMinute.setText(listTimetable[position].startTime.substring(2,4))

            editTextTimeEndHours.setText(listTimetable[position].endTime.substring(0,2))
            editTextTimeEndMinute.setText(listTimetable[position].endTime.substring(2,4))

        }

    }

    override fun onViewAttachedToWindow(holder: TimetableViewHolder) {
        super.onViewAttachedToWindow(holder)

        val editTextName = holder.itemView.findViewById<EditText>(R.id.id_item_time_table_et_name)
        val editTextStudent = holder.itemView.findViewById<EditText>(R.id.id_item_time_table_et_student)
        val editTextPrice = holder.itemView.findViewById<EditText>(R.id.id_item_time_table_et_price)

        val editTextTimeStartHours = holder.itemView.findViewById<EditText>(R.id.id_item_time_table_cs_2_tv_time_1_hour)
        val editTextTimeStartMinute = holder.itemView.findViewById<EditText>(R.id.id_item_time_table_cs_2_tv_time_1_minute)
        val editTextTimeEndHours = holder.itemView.findViewById<EditText>(R.id.id_item_time_table_cs_2_tv_time_2_hour)
        val editTextTimeEndMinute = holder.itemView.findViewById<EditText>(R.id.id_item_time_table_cs_2_tv_time_2_minute)

        val buttonRedact = holder.itemView.findViewById<Button>(R.id.id_item_time_table_button_redact)
        val imageViewDelete = holder.itemView.findViewById<ImageView>(R.id.id_item_time_table_iv_delete)
        val buttonPush = holder.itemView.findViewById<Button>(R.id.id_item_time_table_button_push)

        buttonRedact.setOnClickListener {
            if(buttonRedact.text == "изменить"){

                buttonRedact.text = "сохранить"
                imageViewDelete.isVisible = true
                openOrClosedEditText(true)
                editTextName.isEnabled = isOpenEditText
                editTextPrice.isEnabled = isOpenEditText
                editTextStudent.isEnabled = isOpenEditText
                editTextTimeStartHours.isEnabled = isOpenEditText
                editTextTimeStartMinute.isEnabled = isOpenEditText
                editTextTimeEndHours.isEnabled = isOpenEditText
                editTextTimeEndMinute.isEnabled = isOpenEditText

            }else{

                answer = repository.checkInputTableData(
                    nameLesson = editTextName.text.toString(),
                    nameStudent = editTextStudent.text.toString(),
                    price = editTextPrice.text.toString(),
                    startTime = editTextTimeStartHours.text.toString()+editTextTimeStartMinute.text.toString(),
                    endTime = editTextTimeEndHours.text.toString()+editTextTimeEndMinute.text.toString()
                )

                if(answer == OperationStatus.Correct.status){

                    val itemTimeTable = TimeTableEntity(
                        id = listTimetable[holder.adapterPosition].id,
                        userId = userId,
                        nameLesson = editTextName.text.toString(),
                        price = editTextPrice.text.toString().toInt(),
                        startTime = editTextTimeStartHours.text.toString()+editTextTimeStartMinute.text.toString(),
                        endTime = editTextTimeEndHours.text.toString()+editTextTimeEndMinute.text.toString(),
                        student = editTextStudent.text.toString(),
                        studentId = "dh56gfh",
                        dayWeek = dayOfWeek
                    )

                    interfaceAdapter.updateTable(itemTimeTable,holder.adapterPosition)

                    buttonRedact.text = "изменить"
                    imageViewDelete.isVisible = false
                    openOrClosedEditText(false)
                    editTextName.isEnabled = isOpenEditText
                    editTextPrice.isEnabled = isOpenEditText
                    editTextStudent.isEnabled = isOpenEditText
                    editTextTimeStartHours.isEnabled = isOpenEditText
                    editTextTimeStartMinute.isEnabled = isOpenEditText
                    editTextTimeEndHours.isEnabled = isOpenEditText
                    editTextTimeEndMinute.isEnabled = isOpenEditText

                }else{ interfaceAdapter.showToast(answer) }

            }
        }

        imageViewDelete.setOnClickListener {
            interfaceAdapter.showDialog(holder.adapterPosition)
        }


    }

    // функция открытия/закрытия поля ввода
    private fun openOrClosedEditText(isOpen:Boolean){
        isOpenEditText = isOpen
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: MutableList<TimeTableEntity>){
        listTimetable = list
        notifyDataSetChanged()
    }

    // добавление элемента в прокручиваемый список
    fun addItemInList(item: TimeTableEntity){
        listTimetable.add(item)
        notifyItemChanged(listTimetable.lastIndex)
    }

    // обновление элемента в прокручиваемом списке
    fun updateItemInList(item: TimeTableEntity, indexItem:Int){
        listTimetable[indexItem] = item
        notifyItemChanged(indexItem)
    }

    // удаление элемента из прокручиваемого списка
    fun deleteItemInList(indexItem:Int){
        listTimetable.removeAt(indexItem)
        notifyItemRemoved(indexItem)
        notifyItemRangeChanged(indexItem, listTimetable.size)
    }

    // функция установки выбранного дня
    fun setDay(day:String){
        dayOfWeek = day
    }

}