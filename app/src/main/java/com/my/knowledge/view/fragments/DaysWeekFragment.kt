package com.my.knowledge.view.fragments

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
import com.my.knowledge.model.constant.MAIN

class DaysWeekFragment : Fragment() {

    private var binding: FragmentDaysWeekBinding? = null
    private var bundle:Bundle? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDaysWeekBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.idDaysWeekCs1Button?.setOnClickListener { goToTable("ПН") }
        binding?.idDaysWeekCs2Button?.setOnClickListener { goToTable("ВТ") }
        binding?.idDaysWeekCs3Button?.setOnClickListener { goToTable("СР") }
        binding?.idDaysWeekCs4Button?.setOnClickListener { goToTable("ЧТ") }
        binding?.idDaysWeekCs5Button?.setOnClickListener { goToTable("ПТ") }
        binding?.idDaysWeekCs6Button?.setOnClickListener { goToTable("СБ") }
        binding?.idDaysWeekCs7Button?.setOnClickListener { goToTable("ВС") }

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