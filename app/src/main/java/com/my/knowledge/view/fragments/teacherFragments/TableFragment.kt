package com.my.knowledge.view.fragments.teacherFragments

import android.annotation.SuppressLint
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
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.model.database.Room.entity.TimeTableEntity
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences
import com.my.knowledge.model.modelAdapter.adapters.PriceListAdapter
import com.my.knowledge.model.modelAdapter.adapters.TimetableAdapter
import com.my.knowledge.model.repository.Repository
import com.my.knowledge.viewmodel.teacherviewmodel.TimetableViewModel

class TableFragment : Fragment() {

    private var binding : FragmentTableBinding? = null
    private var repository : Repository? = null
    private var dayOfWeek : String? = null
    private var recyclerView: RecyclerView? = null
    private var listTimeTable = mutableListOf<TimeTableEntity>()
    private var timeTableAdapter: TimetableAdapter? = null
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
        timeTableAdapter = TimetableAdapter()
        recyclerView?.adapter = timeTableAdapter

        dayOfWeek = requireArguments().getString(DAY_OF_WEEK)
        binding?.idTableTvTitle?.text = "Расписание в $dayOfWeek"

        tableViewModel?.getListTimeTableByDayOfWeek(dayOfWeek,sharedPreferences?.getUserId())

        // передача всех занятий в выбранный день недели в адаптер
        tableViewModel?.timeTableList?.observe(viewLifecycleOwner){
            it?.let {
                listTimeTable = it.toMutableList()
                timeTableAdapter?.setList(it.toMutableList())
            }
        }

        // выход в дни недели
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            MAIN?.navController?.navigate(R.id.action_tableFragment_to_daysWeekFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}