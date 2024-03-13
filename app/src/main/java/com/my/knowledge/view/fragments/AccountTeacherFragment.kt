package com.my.knowledge.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.my.knowledge.databinding.FragmentAccountTeacherBinding
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences
import com.my.knowledge.model.modelData.ModelTeacher
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

        val accountTeacherViewModel = ViewModelProvider(this)[AccountTeacherViewModel::class.java]
        accountTeacherViewModel.getMyInfoTeacher(sharedPreferences?.getUserId())

        accountTeacherViewModel.infoMyAccount.observe(viewLifecycleOwner){ data ->
            showMyInfo(data)
        }

        accountTeacherViewModel.isSuccessfull.observe(viewLifecycleOwner){ data ->
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

                accountTeacherViewModel.setDataFromMyProfile(
                    binding?.idAccountTeacherTvEducation?.text.toString(),
                    "education",
                    sharedPreferences?.getUserId()
                )

            }
        }

    }

    // очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // функция показа основной информации в профиле учителя
    private fun showMyInfo(modelTeacher:ModelTeacher){
        binding?.idAccountTvLastName?.setText(modelTeacher.last_name)
        binding?.idAccountTvFirstName?.setText(modelTeacher.first_name)
        binding?.idAccountTvStatus?.text = modelTeacher.status
        binding?.idAccountTeacherTvMyDesc?.setText(modelTeacher.myDescription)
        binding?.idAccountTeacherTvOpitRaboti?.setText(modelTeacher.experience)
        binding?.idAccountTeacherTvEducation?.setText(modelTeacher.education)
    }

    // функция открытия/закрытия для редактирования поля ввода
    private fun openOrClosedEditTextForName(flag:Boolean){
        binding?.idAccountTvFirstName?.isEnabled = flag
        binding?.idAccountTvLastName?.isEnabled = flag
    }

    // функция открытия/закрытия для редактирования поля ввода
    private fun openOrClosedEditText1(flag: Boolean){
        binding?.idAccountTeacherTvMyDesc?.isEnabled = flag
    }

    // функция открытия/закрытия для редактирования поля ввода
    private fun openOrClosedEditText2(flag: Boolean){
        binding?.idAccountTeacherTvOpitRaboti?.isEnabled = flag
    }

    // функция открытия/закрытия для редактирования поля ввода
    private fun openOrClosedEditText3(flag: Boolean){
        binding?.idAccountTeacherTvEducation?.isEnabled = flag
    }

}