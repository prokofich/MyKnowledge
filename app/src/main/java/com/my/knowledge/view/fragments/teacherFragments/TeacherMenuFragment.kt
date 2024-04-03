package com.my.knowledge.view.fragments.teacherFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.my.knowledge.R
import com.my.knowledge.databinding.FragmentTeacherMenuBinding
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences

class TeacherMenuFragment : Fragment() {

    private var binding: FragmentTeacherMenuBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeacherMenuBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(requireContext(),SharedPreferences(requireContext()).getUserId(),Toast.LENGTH_SHORT).show()

        // переход на мой профиль
        binding?.idMenuTeacherButtonMyAccount?.setOnClickListener {
            MAIN?.navController?.navigate(R.id.action_teacherMenuFragment_to_accountTeacherFragment)
        }

        // переход к прайс-листу
        binding?.idMenuTeacherButtonPriceList?.setOnClickListener{
            MAIN?.navController?.navigate(R.id.action_teacherMenuFragment_to_priceListFragment)
        }

    }

    // очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}