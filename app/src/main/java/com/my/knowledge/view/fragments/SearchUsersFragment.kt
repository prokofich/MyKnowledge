package com.my.knowledge.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my.knowledge.databinding.FragmentSearchUsersBinding

class SearchUsersFragment : Fragment() {

    private var binding: FragmentSearchUsersBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchUsersBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    // очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}