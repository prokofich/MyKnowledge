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
import com.my.knowledge.model.database.SharedPreferences
import com.my.knowledge.viewmodel.LoginViewModel

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
            binding?.idLoginPb?.isVisible = true

            loginViewModel.checkInputDataInLogin(
                binding?.idLoginEtEmail?.text.toString(),
                binding?.idLoginEtPassword?.text.toString()
            )
        }

        loginViewModel.isLogin.observe(viewLifecycleOwner){ data ->
            binding?.idLoginPb?.isVisible = false
            if(data == TEACHER){
                SharedPreferences(requireContext()).setTypeUserInLastSession(TEACHER)
                MAIN?.navController?.navigate(R.id.action_loginFragment_to_teacherMenuFragment) // переход в аккаунт учителя
            }else{
                if(data == STUDENT){
                    SharedPreferences(requireContext()).setTypeUserInLastSession(STUDENT)
                    MAIN?.navController?.navigate(R.id.action_loginFragment_to_studentMenuFragment) // переход в аккаунт ученика
                }else{
                    repository?.showToast("ошибка входа",requireContext())
                    binding!!.idLoginTvError.text = "ошибка при входе"
                }
            }
        }

        loginViewModel.isCorrectInputData.observe(viewLifecycleOwner){ data ->
            if(data == CORRECT){
                binding?.idLoginTvTitle?.text = ""
                loginViewModel.loginInAccount(binding?.idLoginEtEmail?.text.toString(),binding?.idLoginEtPassword?.text.toString())
            }else{
                binding?.idLoginPb?.isVisible = false
                binding?.idLoginTvError?.text = data
                repository?.showToast(data,requireContext())
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

}