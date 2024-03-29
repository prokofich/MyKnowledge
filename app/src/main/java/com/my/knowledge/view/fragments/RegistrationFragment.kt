package com.my.knowledge.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.my.knowledge.model.database.firebase.Firestore
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.R
import com.my.knowledge.viewmodel.generalviewmodel.RegistrationViewModel
import com.my.knowledge.model.constant.STUDENT
import com.my.knowledge.model.constant.TEACHER
import com.my.knowledge.databinding.FragmentRegistrationBinding
import com.my.knowledge.model.constant.CORRECT
import com.my.knowledge.model.constant.ERROR
import com.my.knowledge.model.modelData.ModelUser
import com.my.knowledge.model.repository.Repository
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences

class RegistrationFragment : Fragment() {

    private var binding: FragmentRegistrationBinding? = null
    private var registrationViewModel: RegistrationViewModel? = null
    private var firestore: Firestore? = null
    private var statusUser:String? = null
    private var sharedPreferences: SharedPreferences? = null
    private var repository: Repository? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = Firestore()
        repository = Repository()
        sharedPreferences = SharedPreferences(requireContext())

        registrationViewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]

        // попытка зарегистрироваться
        binding?.idRegButtonRegistration?.setOnClickListener {
            binding?.idRegPb?.isVisible = true
            registrationViewModel?.checkInputData(
                binding?.idRegEtEmail?.text.toString(),
                binding?.idRegEtPassword?.text.toString(),
                binding?.idRegEtFirstName?.text.toString(),
                binding?.idRegEtLastName?.text.toString(),
                statusUser
            )
        }

        registrationViewModel?.isRegistration?.observe(viewLifecycleOwner){ data ->
            if(data != ERROR){
                binding?.idRegPb?.isVisible = false
                sharedPreferences?.saveUserId(data)

                val modelUser = ModelUser(
                    binding?.idRegEtFirstName?.text.toString(),
                    binding?.idRegEtLastName?.text.toString(),
                    statusUser!!,
                    data
                )

                registrationViewModel?.setPrimaryDataAfterRegistration(modelUser)

                sharedPreferences?.saveFirstNameTeacher(binding?.idRegEtFirstName?.text.toString())
                sharedPreferences?.saveLastNameTeacher(binding?.idRegEtLastName?.text.toString())

                repository?.showToast("вы успешно зарегистрировались!",requireContext())
            }
        }

        registrationViewModel?.isCorrectInputData?.observe(viewLifecycleOwner){ data ->
            if(data == CORRECT){
                binding?.idRegTvError?.text = ""
                registrationViewModel?.createAccount(binding?.idRegEtEmail?.text.toString(),binding?.idRegEtPassword?.text.toString())
            }else{
                binding?.idRegPb?.isVisible = false
                binding?.idRegTvError?.text = data
                repository?.showToast(data,requireContext())
            }
        }

        binding?.idRegRg?.setOnCheckedChangeListener { _, checkedId ->
            @Suppress("KotlinConstantConditions")
            when (checkedId) {
                binding?.idRegRbTeacher?.id -> { statusUser = TEACHER }
                binding?.idRegRbStudent?.id -> { statusUser = STUDENT }
            }
        }

        // переход обратно на экран входа
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            MAIN?.navController?.navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        // переход обратно на экран входа
        binding?.idRegButtonBack?.setOnClickListener {
            MAIN?.navController?.navigate(R.id.action_registrationFragment_to_loginFragment)
        }

    }

    // очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}