package com.my.knowledge.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.my.knowledge.R
import com.my.knowledge.databinding.FragmentTableBinding
import com.my.knowledge.databinding.FragmentTeacherMenuBinding
import com.my.knowledge.model.constant.MAIN

class TableFragment : Fragment() {

    private var binding: FragmentTableBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTableBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        // возврат в меню выбора дня недели
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            MAIN?.navController?.navigate(R.id.action_tableFragment_to_daysWeekFragment)
        }

        // возврат в меню выбора дня недели
        binding?.idTableButtonBack?.setOnClickListener {
            MAIN?.navController?.navigate(R.id.action_tableFragment_to_daysWeekFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}