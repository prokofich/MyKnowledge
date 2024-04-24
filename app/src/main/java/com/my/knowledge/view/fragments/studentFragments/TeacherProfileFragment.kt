package com.my.knowledge.view.fragments.studentFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.my.knowledge.databinding.FragmentTeacherProfileBinding
import com.my.knowledge.model.constant.SELECTED_TEACHER
import com.my.knowledge.model.modelAdapter.adapters.PriceListAdapter
import com.my.knowledge.model.modelAdapter.adapters.PriceListInProfileAdapter
import com.my.knowledge.model.modelData.ModelTeacher
import com.my.knowledge.viewmodel.studentsviewmodel.TeacherProfileViewModel

class TeacherProfileFragment : Fragment() {

    private var binding : FragmentTeacherProfileBinding? = null
    private var teacherProfileViewModel : TeacherProfileViewModel? = null
    private var recyclerView: RecyclerView? = null
    private var priceListInProfileAdapter: PriceListInProfileAdapter? = null
    private var idTeacher = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeacherProfileBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idTeacher = requireArguments().getString(SELECTED_TEACHER).toString()

        teacherProfileViewModel = ViewModelProvider(this)[TeacherProfileViewModel::class.java]

        // подключение адаптера для списка
        recyclerView = binding?.idProfileTeacherRvPriceList
        priceListInProfileAdapter = PriceListInProfileAdapter()
        recyclerView?.adapter = priceListInProfileAdapter

        teacherProfileViewModel?.getDataFromProfileTeacherById(idTeacher) // запрос получение данных из профиля
        teacherProfileViewModel?.getPriceListByIdTeacherInFirestore(idTeacher) // запрос получения прайс-листа

        // показ основной информации из профиля учителя
        teacherProfileViewModel?.teacher?.observe(viewLifecycleOwner){
            if(teacherProfileViewModel?.checkEmptyModelTeacher(it) == true){
                showInfoTeacher(it)
            }
        }

        teacherProfileViewModel?.priceList?.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                priceListInProfileAdapter?.setList(it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // функция показа основной информации в профиле выбранного учителя
    private fun showInfoTeacher(data: ModelTeacher){
        binding?.idProfileTeacherTvFirstName?.text = data.firstName
        binding?.idProfileTeacherTvLastName?.text = data.lastName
        binding?.idProfileTeacherTvMyDesc?.text = data.myDescription
        binding?.idProfileTeacherTvOpitRaboti?.text = data.experience
        binding?.idProfileTeacherTvEducation?.text = data.education
    }

}