package com.my.knowledge.view.fragments.studentAndTeacherFragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.my.knowledge.R
import com.my.knowledge.databinding.FragmentDaysWeekBinding
import com.my.knowledge.model.constant.DAY_OF_WEEK
import com.my.knowledge.model.constant.DayOfWeek
import com.my.knowledge.model.constant.KEY1
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.model.database.Room.entity.CountLessonsEntity
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences
import com.my.knowledge.model.repository.Repository
import com.my.knowledge.viewmodel.generalviewmodel.DaysWeekViewModel

class DaysWeekFragment : Fragment() {

    private var bundle:Bundle? = null
    private var repository:Repository? = null
    private var binding: FragmentDaysWeekBinding? = null
    private var daysWeekViewModel:DaysWeekViewModel? = null
    private var sharedPreferences:SharedPreferences? = null
    private var itemCountLessons:CountLessonsEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDaysWeekBinding.inflate(inflater,container,false)
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = Repository()
        sharedPreferences = SharedPreferences(requireContext())
        daysWeekViewModel = ViewModelProvider(this)[DaysWeekViewModel::class.java]

        daysWeekViewModel?.getCountAllLessonsByIdUser(sharedPreferences?.getUserId())

        daysWeekViewModel?.countLessons?.observe(viewLifecycleOwner){
            showCountLessons(it)
            itemCountLessons = it
        }

        binding?.idDaysWeekCs1Button?.setOnClickListener { goToTable(DayOfWeek.Monday.day) }
        binding?.idDaysWeekCs2Button?.setOnClickListener { goToTable(DayOfWeek.Tuesday.day) }
        binding?.idDaysWeekCs3Button?.setOnClickListener { goToTable(DayOfWeek.Wednesday.day) }
        binding?.idDaysWeekCs4Button?.setOnClickListener { goToTable(DayOfWeek.Thursday.day) }
        binding?.idDaysWeekCs5Button?.setOnClickListener { goToTable(DayOfWeek.Friday.day) }
        binding?.idDaysWeekCs6Button?.setOnClickListener { goToTable(DayOfWeek.Saturday.day) }
        binding?.idDaysWeekCs7Button?.setOnClickListener { goToTable(DayOfWeek.Sunday.day) }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            repository?.exitTheApplication()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun showCountLessons(data:CountLessonsEntity?){
        binding?.idDaysWeekCs1Tv1?.text = "занятий: ${data?.countInMonday}"
        binding?.idDaysWeekCs2Tv1?.text = "занятий: ${data?.countInTuesday}"
        binding?.idDaysWeekCs3Tv1?.text = "занятий: ${data?.countInWednesday}"
        binding?.idDaysWeekCs4Tv1?.text = "занятий: ${data?.countInThursday}"
        binding?.idDaysWeekCs5Tv1?.text = "занятий: ${data?.countInFriday}"
        binding?.idDaysWeekCs6Tv1?.text = "занятий: ${data?.countInSaturday}"
        binding?.idDaysWeekCs7Tv1?.text = "занятий: ${data?.countInSunday}"
    }

    private fun goToTable(dayWeek:String){
        bundle = Bundle()
        bundle?.putString(DAY_OF_WEEK,dayWeek)
        itemCountLessons?.let { bundle?.putParcelable(KEY1,it) }
        MAIN?.navController?.navigate(R.id.action_daysWeekFragment_to_tableFragment,bundle)
    }

}