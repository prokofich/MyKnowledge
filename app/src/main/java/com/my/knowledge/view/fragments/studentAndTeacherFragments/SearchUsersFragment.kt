package com.my.knowledge.view.fragments.studentAndTeacherFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.my.knowledge.databinding.FragmentSearchUsersBinding
import com.my.knowledge.model.modelAdapter.adapters.SearchUsersStudentsAdapter
import com.my.knowledge.model.modelAdapter.adapters.SearchUsersTeachersAdapter
import com.my.knowledge.model.modelData.ModelStudent
import com.my.knowledge.model.modelData.ModelTeacher
import com.my.knowledge.model.repository.Repository
import com.my.knowledge.viewmodel.generalviewmodel.SearchUsersViewModel

class SearchUsersFragment : Fragment() {

    private var binding : FragmentSearchUsersBinding? = null
    private var repository : Repository? = null
    private var recyclerView : RecyclerView? = null
    private var listAllTeachers = mutableListOf <ModelTeacher> ()
    private var listAllStudents = mutableListOf <ModelStudent> ()
    private var searchUsersViewModel : SearchUsersViewModel? = null
    private var searchStudentsAdapter :SearchUsersStudentsAdapter? = null
    private var searchTeachersAdapter : SearchUsersTeachersAdapter? = null
    private var listNamePredmetsAllTeachers = mutableListOf <MutableList <String> > ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchUsersBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = Repository()
        searchUsersViewModel = ViewModelProvider(this)[SearchUsersViewModel::class.java]

        recyclerView = binding?.idSearchRv

        searchStudentsAdapter = SearchUsersStudentsAdapter()
        searchTeachersAdapter = SearchUsersTeachersAdapter()

        // показ всех учителей
        binding?.idSearchButtonAllTeachers?.setOnClickListener {
            if(listAllTeachers.isNotEmpty() && listNamePredmetsAllTeachers.isNotEmpty() && listNamePredmetsAllTeachers.size == listAllTeachers.size){
                setDataInTeachersAdapter()
            }else{
                 getAllTeachers()
            }
        }

        // показ всех учеников
        binding?.idSearchButtonAllStudents?.setOnClickListener {
            if(listAllStudents.isNotEmpty()){
                setDataInStudentsAdapter(listAllStudents)
            }else{
                getAllStudents()
            }
        }

        searchUsersViewModel?.listStudents?.observe(viewLifecycleOwner){
            showOrHideProgressBar(false)
            if(it.isNotEmpty()){
                setDataInStudentsAdapter(it)
            }else{
                repository?.showToast("ошибка" , requireContext())
            }
        }

        searchUsersViewModel?.listTeachers?.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                listAllTeachers = it
                repository?.showToast("получены учителя",requireContext())
                for (teacher in it){
                    searchUsersViewModel?.getAllNamePredmetsByIdTeacher(teacher.userId)
                }
            }else{
                showOrHideProgressBar(false)
                repository?.showToast("ошибка" , requireContext())
            }
        }

        searchUsersViewModel?.listNamePredmets?.observe(viewLifecycleOwner){
            listNamePredmetsAllTeachers.add(it)
            if(listNamePredmetsAllTeachers.size == listAllTeachers.size){
                setDataInTeachersAdapter()
                showOrHideProgressBar(false)
            }
        }

        // выход из приложения
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            repository?.exitTheApplication()
        }

    }

    // очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // функция получения всех учеников
    private fun getAllStudents(){
        if(repository?.checkNetworkState() == true){
            showOrHideProgressBar(true)
            searchUsersViewModel?.getAllStudentsInFirestore()
        }else{
            repository?.showToast("проверьте соединение с интернетом" , requireContext())
        }
    }

    // функция получения всех учителей
    private fun getAllTeachers(){
        if(repository?.checkNetworkState() == true){
            showOrHideProgressBar(true)
            searchUsersViewModel?.getAllTeachersInFirestore()
        }else{
            repository?.showToast("проверьте соединение с интернетом" , requireContext())
        }
    }

    // функция отправки всех данных в адаптер
    private fun setDataInTeachersAdapter(){
        recyclerView?.adapter = searchTeachersAdapter
        searchTeachersAdapter?.setListTeachers(listAllTeachers)
        searchTeachersAdapter?.setListPredmets(listNamePredmetsAllTeachers)
    }

    // функция отправки всех данных в адаптер
    private fun setDataInStudentsAdapter(list : MutableList<ModelStudent>){
        recyclerView?.adapter = searchStudentsAdapter
        searchStudentsAdapter?.setListStudents(list)
    }

    // функция показа или сокрытия прогресса
    private fun showOrHideProgressBar(flag : Boolean){
        binding?.idSearchPb?.isVisible = flag
    }

}