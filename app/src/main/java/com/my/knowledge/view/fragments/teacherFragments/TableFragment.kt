package com.my.knowledge.view.fragments.teacherFragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.my.knowledge.R
import com.my.knowledge.databinding.FragmentTableBinding
import com.my.knowledge.model.constant.DAY_OF_WEEK
import com.my.knowledge.model.constant.DayOfWeek
import com.my.knowledge.model.constant.KEY1
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.model.database.Room.entity.CountLessonsEntity
import com.my.knowledge.model.database.Room.entity.TimeTableEntity
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences
import com.my.knowledge.model.modelAdapter.adapters.TimetableAdapter
import com.my.knowledge.model.modelAdapter.interfaces.TimeTableInterface
import com.my.knowledge.model.repository.Repository
import com.my.knowledge.viewmodel.teacherviewmodel.TimetableViewModel

class TableFragment : Fragment(),TimeTableInterface {

    private var binding : FragmentTableBinding? = null
    private var repository : Repository? = null
    private var dayOfWeek : String? = null
    private var recyclerView: RecyclerView? = null
    private var listTimeTable = mutableListOf<TimeTableEntity>()
    private var timeTableAdapter: TimetableAdapter? = null
    private var countLessonsEntity:CountLessonsEntity? = null
    private var tableViewModel:TimetableViewModel? = null
    private var sharedPreferences:SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTableBinding.inflate(inflater,container,false)
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = Repository()
        sharedPreferences = SharedPreferences(requireContext())
        tableViewModel = ViewModelProvider(this)[TimetableViewModel::class.java]

        recyclerView = binding?.idTableRv
        timeTableAdapter = TimetableAdapter(this,requireContext())
        recyclerView?.adapter = timeTableAdapter

        dayOfWeek?.let {
            timeTableAdapter?.setDay(it)
        }

        dayOfWeek = requireArguments().getString(DAY_OF_WEEK)
        countLessonsEntity = requireArguments().getParcelable(KEY1)
        binding?.idTableTvTitle?.text = "Расписание в $dayOfWeek"

        tableViewModel?.getListTimeTableByDayOfWeek(dayOfWeek,sharedPreferences?.getUserId())

        // передача всех занятий в выбранный день недели в адаптер
        tableViewModel?.timeTableList?.observe(viewLifecycleOwner){
            it?.let {
                listTimeTable = it.toMutableList()
                timeTableAdapter?.setList(it.toMutableList())
            }
        }

        // добавление элемента в прокручиваемый список
        binding?.idTableButtonAdd?.setOnClickListener {
            if(repository?.checkNetworkState() == true){

                val newItem = TimeTableEntity(userId = sharedPreferences?.getUserId(), nameLesson = "",
                    price = 0, startTime = "", endTime = "", student = "", studentId = "", dayWeek = dayOfWeek!!)
                saveTableInRoom(newItem)

            }
        }

        // обработка ответа от Room при сохранении
        tableViewModel?.isSuccessfulInsertInRoom?.observe(viewLifecycleOwner){
            if(repository?.checkNetworkState() == true){
                saveTableInFirestore(it)
                listTimeTable.add(it)
                timeTableAdapter?.addItemInList(it)
                updateCountLessons(1)
            }
        }

        // обработка ответа от Firestore при сохранении
        tableViewModel?.isSuccessfulInsertInFirestore?.observe(viewLifecycleOwner){
            if(!it) {
                repository?.showToast("ошибка, попробуйте еще раз", requireContext())
            }
        }

        // обработка ответа от Firestore при обновлении
        tableViewModel?.isSuccessfulUpdateInFirestore?.observe(viewLifecycleOwner){
            if(it){
                repository?.showToast("данные успешно обновлены", requireContext())
            }else{
                repository?.showToast("ошибка при обновлении", requireContext())
            }
        }

        // обработка ответа от Firestore при удалении
        tableViewModel?.isSuccessfulDeleteInFirestore?.observe(viewLifecycleOwner){
            if(it){
                repository?.showToast("данные успешно удалены", requireContext())
            }else{
                repository?.showToast("ошибка при удалении", requireContext())
            }
        }

        // выход в дни недели
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            MAIN?.navController?.navigate(R.id.action_tableFragment_to_daysWeekFragment)
        }

    }

    // сохранение количества занятий в Room
    override fun onStop() {
        super.onStop()
        countLessonsEntity?.let { tableViewModel?.updateCountLessonsInDayOfWeek(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // функция сохранения элемента в Room
    private fun saveTableInRoom(item : TimeTableEntity) {
        tableViewModel?.insertItemTableListInRoom(item)
    }

    // функция сохранения элемента в Firestore
    private fun saveTableInFirestore(item : TimeTableEntity){
        tableViewModel?.insertItemTableListInFirestore(item,sharedPreferences?.getUserId(),dayOfWeek)
    }

    // функция обновления элемента
    override fun updateTable(item : TimeTableEntity , indexItem : Int) {
        tableViewModel?.updateItemTableListInRoom(item)
        tableViewModel?.updateItemTableListInFirestore(item,sharedPreferences?.getUserId(),dayOfWeek)
        listTimeTable[indexItem] = item
        timeTableAdapter?.updateItemInList(item, indexItem)
    }

    // функция отправления пуш-уведомления студенту
    override fun pushForStudent() {
        TODO("Not yet implemented")
    }

    // функция удаления старой позиции из прайс-листа
    private fun deleteTable(item : TimeTableEntity , indexItem : Int) {
        listTimeTable.removeAt(indexItem)
        timeTableAdapter?.deleteItemInList(indexItem)
        tableViewModel?.deleteItemTableListInRoom(item)
        tableViewModel?.deleteItemTableListInFirestore(item,sharedPreferences?.getUserId(),dayOfWeek)
        updateCountLessons(-1)
    }

    // функция показа диалога о подтверждении удаления
    override fun showDialog(indexItem : Int) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {

            setTitle("Удаление")
            setMessage("Вы уверены, что хотите удалить этот элемент?")

            setPositiveButton("Да") { dialogInterface: DialogInterface, _: Int ->
                deleteTable(listTimeTable[indexItem],indexItem)
                dialogInterface.dismiss()
            }
            setNegativeButton("Нет") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
        }

        alertDialogBuilder.create().show()
    }

    // функция показа всплывающего сообщения
    override fun showToast(message : String?) {
        repository?.showToast(message,requireContext())
    }

    private fun updateCountLessons(plus:Int){
        countLessonsEntity?.let {
            when(dayOfWeek){
                DayOfWeek.Monday.day    ->  { it.countInMonday += plus }
                DayOfWeek.Tuesday.day   ->  { it.countInTuesday += plus }
                DayOfWeek.Wednesday.day ->  { it.countInWednesday += plus }
                DayOfWeek.Thursday.day  ->  { it.countInThursday += plus }
                DayOfWeek.Friday.day    ->  { it.countInFriday += plus }
                DayOfWeek.Saturday.day  ->  { it.countInSaturday += plus }
                DayOfWeek.Sunday.day    ->  { it.countInSunday += plus }
            }
        }
    }

}