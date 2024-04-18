package com.my.knowledge.view.fragments.studentAndTeacherFragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.my.knowledge.R
import com.my.knowledge.databinding.FragmentSettingsBinding
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.viewmodel.generalviewmodel.SettingsViewModel

class SettingsFragment : Fragment() {

    private var alertDialog : AlertDialog? = null
    private var settingsViewModel : SettingsViewModel? = null

    private var binding: FragmentSettingsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // выход из аккаунта
        binding?.idSettingsTvTitle?.setOnClickListener {
            exitAccount()
        }

        // выход обратно в аккаунт
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            MAIN?.navController?.navigate(R.id.action_settingsFragment_to_accountTeacherFragment)
        }

    }

    // очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // функция показа диалога о выходе из приложения
    private fun exitAccount(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("выход из аккаунта")
        builder.setMessage("вы точно хотите выйти?")

        builder.setPositiveButton("выйти") { _, _ ->
            settingsViewModel?.exitFromAccount()
        }

        builder.setNegativeButton("отмена") { _, _ ->
            alertDialog?.dismiss()
        }

        alertDialog = builder.create()
        alertDialog?.show()
    }
}