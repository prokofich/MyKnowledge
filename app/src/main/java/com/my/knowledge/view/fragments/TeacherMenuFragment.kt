package com.my.knowledge.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my.knowledge.R
import com.my.knowledge.databinding.FragmentLoginBinding
import com.my.knowledge.databinding.FragmentRegistrationBinding
import com.my.knowledge.databinding.FragmentTeacherMenuBinding
import com.my.knowledge.model.constant.MAIN

class TeacherMenuFragment : Fragment() {

    private var binding: FragmentTeacherMenuBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeacherMenuBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // переход на мой профиль
        binding?.idMenuTeacherButtonMyAccount?.setOnClickListener {
            MAIN?.navController?.navigate(R.id.action_teacherMenuFragment_to_accountTeacherFragment)
        }

    }

    // очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}