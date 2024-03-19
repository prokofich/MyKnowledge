package com.my.knowledge.view.fragments

import android.app.AlertDialog
import android.content.DialogInterface
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
            if(data != null){
                listPrice = data.toMutableList()
                priceListAdapter?.setListPrice(listPrice)
            }
        }

        priceListViewModel?.isSuccessful?.observe(viewLifecycleOwner){ data ->
            repository = Repository()
            if(data){
                repository?.showToast("действие успешно выполнено",requireContext())
            }else{
                repository?.showToast("ошибка",requireContext())
            }
        }

        // выход в меню
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            MAIN?.navController?.navigate(R.id.action_priceListFragment_to_teacherMenuFragment)
        }

        // выход в меню
        binding?.idPriceListButtonBack?.setOnClickListener {
            MAIN?.navController?.navigate(R.id.action_priceListFragment_to_teacherMenuFragment)
        }

        // добавление элемента в прокручиваемый список
        binding?.idPriceListButtonAdd?.setOnClickListener {
            listPrice.add(PriceListEntity(name = "", price = "", desc = ""))
            priceListAdapter?.setListPrice(listPrice)
        }

    }

    // очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // функция сохранения новой позиции в прайс-листе
    override fun savePrice(item: PriceListEntity) {
        sharedPreferences = SharedPreferences(requireContext())
        priceListViewModel?.insertPriceFromDatabase(item,sharedPreferences?.getUserId())
    }

    // функция обновления старой позиции в прайс-листе
    override fun updatePrice(item: PriceListEntity) {
        sharedPreferences = SharedPreferences(requireContext())
        priceListViewModel?.updatePriceFromDatabase(item,sharedPreferences?.getUserId())
    }

    // функция удаления старой позиции из прайс-листа
    override fun deletePrice(item: PriceListEntity) {
        sharedPreferences = SharedPreferences(requireContext())
        priceListViewModel?.deletePriceFromDatabase(item,sharedPreferences?.getUserId())
    }

    // функция показа всплывающего сообщения
    override fun showToast(message: String?) {
        repository = Repository()
        repository?.showToast(message,requireContext())
    }

    // функция показа диалога о подтверждении удаления
    override fun showDialog(item: PriceListEntity) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle("Удаление")
            setMessage("Вы уверены, что хотите удалить этот элемент?")
            setPositiveButton("Да") { dialogInterface: DialogInterface, _: Int ->
                if(item.id.toInt() != 0){
                    deletePrice(item)
                }else{
                    listPrice.remove(item)
                    priceListAdapter?.setListPrice(listPrice)
                }
                dialogInterface.dismiss()
            }
            setNegativeButton("Нет") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
        }
        alertDialogBuilder.create().show()
    }

}