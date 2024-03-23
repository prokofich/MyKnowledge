package com.my.knowledge.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.my.knowledge.R
import com.my.knowledge.databinding.FragmentDaysWeekBinding
import com.my.knowledge.databinding.FragmentLoginBinding
import com.my.knowledge.model.constant.DAY_OF_WEEK
import com.my.knowledge.model.constant.FRIDAY
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.model.constant.MONDAY
import com.my.knowledge.model.constant.SATURDAY
import com.my.knowledge.model.constant.SUNDAY
import com.my.knowledge.model.constant.THURSDAY
import com.my.knowledge.model.constant.TUESDAY
import com.my.knowledge.model.constant.WEDNESDAY
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences

class DaysWeekFragment : Fragment() {

    private var binding: FragmentDaysWeekBinding? = null
    private var bundle:Bundle? = null
    private var sharedPreferences:SharedPreferences? = null

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

        binding?.idDaysWeekCs1Button?.setOnClickListener { goToTable(MONDAY) }
        binding?.idDaysWeekCs2Button?.setOnClickListener { goToTable(TUESDAY) }
        binding?.idDaysWeekCs3Button?.setOnClickListener { goToTable(WEDNESDAY) }
        binding?.idDaysWeekCs4Button?.setOnClickListener { goToTable(THURSDAY) }
        binding?.idDaysWeekCs5Button?.setOnClickListener { goToTable(FRIDAY) }
        binding?.idDaysWeekCs6Button?.setOnClickListener { goToTable(SATURDAY) }
        binding?.idDaysWeekCs7Button?.setOnClickListener { goToTable(SUNDAY) }

        binding?.idDaysWeekCs1Tv1?.text = "занятий: ${sharedPreferences?.getCountLessonsInMonday()}"
        binding?.idDaysWeekCs2Tv1?.text = "занятий: ${sharedPreferences?.getCountLessonsInTuesday()}"
        binding?.idDaysWeekCs3Tv1?.text = "занятий: ${sharedPreferences?.getCountLessonsInWednesday()}"
        binding?.idDaysWeekCs4Tv1?.text = "занятий: ${sharedPreferences?.getCountLessonsInThursday()}"
        binding?.idDaysWeekCs5Tv1?.text = "занятий: ${sharedPreferences?.getCountLessonsInFriday()}"
        binding?.idDaysWeekCs6Tv1?.text = "занятий: ${sharedPreferences?.getCountLessonsInSaturday()}"
        binding?.idDaysWeekCs7Tv1?.text = "занятий: ${sharedPreferences?.getCountLessonsInSunday()}"

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            MAIN?.navController?.navigate(R.id.action_daysWeekFragment_to_teacherMenuFragment)
        }

        binding?.idDaysWeekButtonBack?.setOnClickListener {
            MAIN?.navController?.navigate(R.id.action_daysWeekFragment_to_teacherMenuFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun goToTable(dayWeek:String){
        bundle = Bundle()
        bundle?.putString(DAY_OF_WEEK,dayWeek)
        MAIN?.navController?.navigate(R.id.action_daysWeekFragment_to_tableFragment,bundle)
    }

}