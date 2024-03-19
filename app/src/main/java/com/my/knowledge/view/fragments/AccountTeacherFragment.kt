package com.my.knowledge.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my.knowledge.R
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.my.knowledge.databinding.FragmentAccountTeacherBinding
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences
import com.my.knowledge.model.repository.Repository
import com.my.knowledge.viewmodel.teacherviewmodel.AccountTeacherViewModel

class AccountTeacherFragment : Fragment() {

    private var binding: FragmentAccountTeacherBinding? = null
    private var sharedPreferences: SharedPreferences? = null
    private var repository:Repository? = null

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
        repository = Repository()

        showMyInfoTeacher()

        val accountTeacherViewModel = ViewModelProvider(this)[AccountTeacherViewModel::class.java]

        accountTeacherViewModel.isSuccessful.observe(viewLifecycleOwner){ data ->
            if(data){
                repository?.showToast("данные успешно сохранены",requireContext())
            }else{
                repository?.showToast("ошибка,данные не сохранены",requireContext())
            }
        }

        // разрешение на редактирование имени и фамилии
        binding?.idAccountTeacherTvRedact?.setOnClickListener {
            if(binding?.idAccountTeacherTvRedact?.text == "редактировать"){

                openOrClosedEditTextForName(true) // открытие для редактирования
                binding?.idAccountTeacherTvRedact?.text = "сохранить"

            }else{
                openOrClosedEditTextForName(false) // закрытие перед сохранением
                binding?.idAccountTeacherTvRedact?.text = "редактировать"

                // сохранение на телефон в SharedPreferences
                sharedPreferences?.saveFirstNameTeacher(binding?.idAccountTvFirstName?.text.toString())
                sharedPreferences?.saveLastNameTeacher(binding?.idAccountTvLastName?.text.toString())

                // сохранение в Firestore
                accountTeacherViewModel.setFirstAndLastName(
                    binding?.idAccountTvFirstName?.text.toString(),
                    binding?.idAccountTvLastName?.text.toString(),
                    sharedPreferences?.getUserId()
                )

            }
        }

        // изменение описания учителя
        binding?.idAccountTeacherTvRedact1?.setOnClickListener {
            if(binding?.idAccountTeacherTvRedact1?.text == "изменить"){
                openOrClosedEditText1(true) // открытие для редактирования
                binding?.idAccountTeacherTvRedact1?.text = "сохранить"
            }else{

                openOrClosedEditText1(false)
                binding?.idAccountTeacherTvRedact1?.text = "изменить"

                // сохранение на телефон в SharedPreferences
                sharedPreferences?.saveDescriptionTeacher(binding?.idAccountTeacherTvMyDesc?.text.toString())

                // сохранение в Firestore
                accountTeacherViewModel.setDataFromMyProfile(
                    binding?.idAccountTeacherTvMyDesc?.text.toString(),
                    "myDescription",
                    sharedPreferences?.getUserId()
                )

            }
        }

        // изменение опыта работы
        binding?.idAccountTeacherTvRedact2?.setOnClickListener {
            if(binding?.idAccountTeacherTvRedact2?.text == "изменить"){

                openOrClosedEditText2(true) // открытие для редактирования
                binding?.idAccountTeacherTvRedact2?.text = "сохранить"

            }else{

                openOrClosedEditText2(false)
                binding?.idAccountTeacherTvRedact2?.text = "изменить"

                // сохранение на телефон в SharedPreferences
                sharedPreferences?.saveExperienceTeacher(binding?.idAccountTeacherTvOpitRaboti?.text.toString())

                // сохранение в Firestore
                accountTeacherViewModel.setDataFromMyProfile(
                    binding?.idAccountTeacherTvOpitRaboti?.text.toString(),
                    "experience",
                    sharedPreferences?.getUserId()
                )

            }
        }

        // изменение образования
        binding?.idAccountTeacherTvRedact3?.setOnClickListener {
            if(binding?.idAccountTeacherTvRedact3?.text == "изменить"){

                openOrClosedEditText3(true) // открытие для редактирования
                binding?.idAccountTeacherTvRedact3?.text = "сохранить"

            }else{

                openOrClosedEditText3(false)
                binding?.idAccountTeacherTvRedact3?.text = "изменить"

                // сохранение на телефон в SharedPreferences
                sharedPreferences?.saveEducationTeacher(binding?.idAccountTeacherTvEducation?.text.toString())

                // сохранение в Firestore
                accountTeacherViewModel.setDataFromMyProfile(
                    binding?.idAccountTeacherTvEducation?.text.toString(),
                    "education",
                    sharedPreferences?.getUserId()
                )

            }
        }

        // выход в меню учителя
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            MAIN?.navController?.navigate(R.id.action_accountTeacherFragment_to_teacherMenuFragment)
        }

        // выход в меню учителя
        binding?.idAccountTeacherButtonBack?.setOnClickListener {
            MAIN?.navController?.navigate(R.id.action_accountTeacherFragment_to_teacherMenuFragment)
        }

    }

    // очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // функция показа основной информации в профиле учителя
    private fun showMyInfoTeacher(){
        binding?.idAccountTvLastName?.setText(sharedPreferences?.getLastNameTeacher())
        binding?.idAccountTvFirstName?.setText(sharedPreferences?.getFirstNameTeacher())
        binding?.idAccountTeacherTvMyDesc?.setText(sharedPreferences?.getDescriptionTeacher())
        binding?.idAccountTeacherTvOpitRaboti?.setText(sharedPreferences?.getExperienceTeacher())
        binding?.idAccountTeacherTvEducation?.setText(sharedPreferences?.getEducationTeacher())
    }

    // функция открытия/закрытия для редактирования поля ввода
    private fun openOrClosedEditTextForName(isOpen:Boolean){
        binding?.idAccountTvFirstName?.isEnabled = isOpen
        binding?.idAccountTvLastName?.isEnabled = isOpen
    }

    // функция открытия/закрытия для редактирования поля ввода
    private fun openOrClosedEditText1(isOpen: Boolean){
        binding?.idAccountTeacherTvMyDesc?.isEnabled = isOpen
    }

    // функция открытия/закрытия для редактирования поля ввода
    private fun openOrClosedEditText2(isOpen: Boolean){
        binding?.idAccountTeacherTvOpitRaboti?.isEnabled = isOpen
    }

    // функция открытия/закрытия для редактирования поля ввода
    private fun openOrClosedEditText3(isOpen: Boolean){
        binding?.idAccountTeacherTvEducation?.isEnabled = isOpen
    }

}