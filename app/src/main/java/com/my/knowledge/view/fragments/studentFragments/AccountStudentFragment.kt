package com.my.knowledge.view.fragments.studentFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my.knowledge.R
import com.my.knowledge.databinding.FragmentAccountStudentBinding
import com.my.knowledge.databinding.FragmentAccountTeacherBinding

class AccountStudentFragment : Fragment() {

    private var binding: FragmentAccountStudentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountStudentBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}