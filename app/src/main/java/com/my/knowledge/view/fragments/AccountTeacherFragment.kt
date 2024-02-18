package com.my.knowledge.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.my.knowledge.databinding.FragmentAccountTeacherBinding
import com.my.knowledge.model.database.SharedPreferences
import com.my.knowledge.model.modelData.ModelTeacher
import com.my.knowledge.viewmodel.AccountTeacherViewModel

class AccountTeacherFragment : Fragment() {

    private var binding: FragmentAccountTeacherBinding? = null
    private var sharedPreferences:SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountTeacherBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = SharedPreferences(requireContext())

        val accountTeacherViewModel = ViewModelProvider(this)[AccountTeacherViewModel::class.java]

        accountTeacherViewModel.getMyInfoTeacher(sharedPreferences?.getUserId())

        accountTeacherViewModel.infoMyAccount.observe(viewLifecycleOwner){ data ->
            showMyInfo(data)
        }

    }

    // очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // функция показа основной информации в профиле учителя
    private fun showMyInfo(modelTeacher:ModelTeacher){
        binding?.idAccountTvLastName?.text = modelTeacher.lastName
        binding?.idAccountTvFirstName?.text = modelTeacher.firstName
        binding?.idAccountTvStatus?.text = modelTeacher.status
        binding?.idAccountTeacherTvMyDesc?.setText(modelTeacher.myDescription)
        binding?.idAccountTeacherTvOpitRaboti?.setText(modelTeacher.experience)
        binding?.idAccountTeacherTvEducation?.setText(modelTeacher.education)
    }

}