package com.my.knowledge.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.R
import com.my.knowledge.model.repository.Repository
import com.my.knowledge.databinding.FragmentLoginBinding
import com.my.knowledge.model.constant.CORRECT
import com.my.knowledge.model.constant.STUDENT
import com.my.knowledge.model.constant.TEACHER
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences
import com.my.knowledge.viewmodel.generalviewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private var alertDialog:AlertDialog? = null
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

        loginViewModel.isLogin.observe(viewLifecycleOwner){ data ->

            SharedPreferences(requireContext()).saveUserId(data.userId)
            SharedPreferences(requireContext()).setTypeUserInLastSession(data.statusUser)
            showOrHideProgressBar(false)

            when(data.statusUser){
                TEACHER -> { MAIN?.navController?.navigate(R.id.action_loginFragment_to_teacherMenuFragment) }
                STUDENT -> { MAIN?.navController?.navigate(R.id.action_loginFragment_to_studentMenuFragment) }
                else -> {
                    repository?.showToast("ошибка входа",requireContext())
                    binding!!.idLoginTvError.text = "ошибка при входе"
                }
            }

        }

        loginViewModel.isCorrectInputData.observe(viewLifecycleOwner){ data ->

            when(data){
                CORRECT -> {
                    loginViewModel.loginInAccount(binding?.idLoginEtEmail?.text.toString(),binding?.idLoginEtPassword?.text.toString())
                }
                else -> {
                    showOrHideProgressBar(false)
                    showError(data)
                    repository?.showToast(data,requireContext())
                }
            }

        }

        // переход к созданию аккаунта
        binding?.idLoginTvCreateAccount?.setOnClickListener {
            MAIN?.navController?.navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        // выход из приложения
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            exitTheApplication()
        }

    }

    // очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // функция показа диалогового сообщения о выходе из приложения
    private fun exitTheApplication(){

        val builder = AlertDialog.Builder(context)
        builder.setTitle("выход из приложения")
        builder.setMessage("вы точно хотите выйти?")

        builder.setPositiveButton("выйти") { _, _ ->
            repository?.closeApplication()
        }

        builder.setNegativeButton("отмена") { _, _ ->
            alertDialog?.dismiss()
        }

        alertDialog = builder.create()
        alertDialog?.show()

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