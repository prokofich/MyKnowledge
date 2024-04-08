package com.my.knowledge.view.fragments.studentAndTeacherFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.R
import com.my.knowledge.model.repository.Repository
import com.my.knowledge.databinding.FragmentLoginBinding
import com.my.knowledge.model.constant.OperationStatus
import com.my.knowledge.model.constant.UserType
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences
import com.my.knowledge.viewmodel.generalviewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private var repository: Repository? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = Repository()

        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        // попытка входа в аккаунт
        binding?.idLoginButtonInput?.setOnClickListener {

            showError("")
            showOrHideProgressBar(true)
            loginViewModel.checkInputDataInLogin(
                binding?.idLoginEtEmail?.text.toString(),
                binding?.idLoginEtPassword?.text.toString(),
                MAIN?.getStateNetwork()
            )

        }

        loginViewModel.isLogin.observe(viewLifecycleOwner){

            SharedPreferences(requireContext()).saveUserId(it.userId)
            SharedPreferences(requireContext()).setTypeUserInLastSession(it.statusUser)
            showOrHideProgressBar(false)

            when(it.statusUser){
                UserType.Teacher.user -> {
                    MAIN?.showOrHideBottomNavigationForTeacher(true)
                    MAIN?.navController?.navigate(R.id.accountTeacherFragment)
                }
                UserType.Student.user -> {

                }
                else -> {
                    showError("ошибка,попробуйте еще раз")
                    repository?.showToast("ошибка,попробуйте еще раз",requireContext())
                }
            }

        }

        loginViewModel.isCorrectInputData.observe(viewLifecycleOwner){

            when(it){
                OperationStatus.Correct.status -> {
                    loginViewModel.loginInAccount(binding?.idLoginEtEmail?.text.toString(),binding?.idLoginEtPassword?.text.toString())
                }
                else -> {
                    showOrHideProgressBar(false)
                    showError(it)
                    repository?.showToast(it,requireContext())
                }
            }

        }

        // переход к созданию аккаунта
        binding?.idLoginTvCreateAccount?.setOnClickListener {
            MAIN?.navController?.navigate(R.id.action_loginFragment_to_registrationFragment)
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

    // функция показа ошибки на экране
    private fun showError(error:String){
        binding?.idLoginTvError?.text = error
    }

    // функция показа или скрытия прогресс бара
    private fun showOrHideProgressBar(flag:Boolean){
        binding?.idLoginPb?.isVisible = flag
    }

}