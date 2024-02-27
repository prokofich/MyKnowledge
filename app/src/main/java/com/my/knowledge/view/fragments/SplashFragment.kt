package com.my.knowledge.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.my.knowledge.R
import com.my.knowledge.databinding.FragmentSplashBinding
import com.my.knowledge.databinding.FragmentTeacherMenuBinding
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.model.constant.STUDENT
import com.my.knowledge.model.constant.TEACHER
import com.my.knowledge.model.database.SharedPreferences
import com.my.knowledge.model.repository.Repository
import com.my.knowledge.viewmodel.SplashViewModel


class SplashFragment : Fragment() {

    private var binding: FragmentSplashBinding? = null
    private var sharedPreferences:SharedPreferences? = null
    private var repository:Repository? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = SharedPreferences(requireContext())

        val splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        splashViewModel.checkOpenAccount() // проверка безлогинового входа

        splashViewModel.isOpenAccount.observe(viewLifecycleOwner){
            if(it){
                when(sharedPreferences?.getTypeUserInLastSession()){
                    TEACHER -> { MAIN?.navController?.navigate(R.id.action_splashFragment_to_teacherMenuFragment) }
                    STUDENT -> { MAIN?.navController?.navigate(R.id.action_splashFragment_to_studentMenuFragment) }
                }
            }else{
                MAIN?.navController?.navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }

        // выход из приложения
         requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
             repository = Repository()
             repository?.closeApplication()
         }

    }

    // очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}