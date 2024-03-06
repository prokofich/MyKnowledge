package com.my.knowledge.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my.knowledge.R
import com.my.knowledge.databinding.FragmentAccountTeacherBinding
import com.my.knowledge.databinding.FragmentPriceListBinding

class PriceListFragment : Fragment() {

    private var binding: FragmentPriceListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPriceListBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}