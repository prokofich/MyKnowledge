package com.my.knowledge.view.fragments.studentAndTeacherFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.my.knowledge.R
import com.my.knowledge.databinding.FragmentSplashBinding
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.model.constant.UserType
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences
import com.my.knowledge.model.repository.Repository
import com.my.knowledge.viewmodel.generalviewmodel.SplashViewModel


class SplashFragment : Fragment() {

    private var binding: FragmentSplashBinding? = null
    private var sharedPreferences: SharedPreferences? = null
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

        repository = Repository()
        sharedPreferences = SharedPreferences(requireContext())

        val splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        splashViewModel.checkOpenAccount() // проверка безлогинового входа

        splashViewModel.isOpenAccount.observe(viewLifecycleOwner){
            if(it){
                when(sharedPreferences?.getTypeUserInLastSession()){

                    UserType.Teacher.user -> {
                        MAIN?.showOrHideBottomNavigationForTeacher(true)
                        MAIN?.navController?.navigate(R.id.accountTeacherFragment)
                    }
                    UserType.Student.user -> {

                    }
                }
            }else{
                MAIN?.navController?.navigate(R.id.action_splashFragment_to_loginFragment)
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

}