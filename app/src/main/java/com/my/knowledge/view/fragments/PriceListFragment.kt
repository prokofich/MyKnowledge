package com.my.knowledge.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.my.knowledge.R
import com.my.knowledge.databinding.FragmentPriceListBinding
import com.my.knowledge.model.constant.MAIN
import com.my.knowledge.model.database.Room.entity.PriceListEntity
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences
import com.my.knowledge.model.modelAdapter.adapters.PriceListAdapter
import com.my.knowledge.model.modelAdapter.interfaces.PriceListInterface
import com.my.knowledge.model.repository.Repository
import com.my.knowledge.viewmodel.teacherviewmodel.PriceListViewModel

class PriceListFragment : Fragment(),PriceListInterface {

    private var binding: FragmentPriceListBinding? = null
    private var listPrice = mutableListOf<PriceListEntity>()
    private var repository:Repository? = null
    private var recyclerView: RecyclerView? = null
    private var priceListAdapter: PriceListAdapter? = null
    private var sharedPreferences: SharedPreferences? = null
    private var priceListViewModel: PriceListViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPriceListBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        priceListViewModel = ViewModelProvider(this)[PriceListViewModel::class.java]

        recyclerView = binding?.idPriceListRv
        priceListAdapter = PriceListAdapter(this)
        recyclerView?.adapter = priceListAdapter

        priceListViewModel?.getAllPriceList()

        priceListViewModel?.priceList?.observe(viewLifecycleOwner){ data ->
            listPrice = data.toMutableList()
            priceListAdapter?.setListPrice(listPrice)
        }

        priceListViewModel?.isSuccessful?.observe(viewLifecycleOwner){ data ->
            repository = Repository()
            if(data){
                repository?.showToast("данные успешно добавлены",requireContext())
            }else{
                repository?.showToast("ошибка при сохранении",requireContext())
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            MAIN?.navController?.navigate(R.id.action_priceListFragment_to_teacherMenuFragment)
        }

        binding?.idPriceListButtonBack?.setOnClickListener {
            MAIN?.navController?.navigate(R.id.action_priceListFragment_to_teacherMenuFragment)
        }

        binding?.idPriceListButtonAdd?.setOnClickListener {
            listPrice.add(PriceListEntity(name = "", price = "", desc = ""))
            priceListAdapter?.setListPrice(listPrice)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun savePrice(item: PriceListEntity) {
        sharedPreferences = SharedPreferences(requireContext())
        priceListViewModel?.insertPriceInDatabase(item,sharedPreferences?.getUserId())
    }

    override fun showToast(message: String?) {
        repository = Repository()
        repository?.showToast(message,requireContext())
    }

}