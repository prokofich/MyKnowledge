package com.my.knowledge.view.fragments.teacherFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my.knowledge.databinding.FragmentTableBinding
import com.my.knowledge.model.repository.Repository

class TableFragment : Fragment() {

    private var binding: FragmentTableBinding? = null
    private var repository:Repository? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTableBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = Repository()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}