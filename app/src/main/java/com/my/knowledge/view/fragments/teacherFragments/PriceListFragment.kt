package com.my.knowledge.view.fragments.teacherFragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.my.knowledge.databinding.FragmentPriceListBinding
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

        repository = Repository()
        sharedPreferences = SharedPreferences(requireContext())
        priceListViewModel = ViewModelProvider(this)[PriceListViewModel::class.java]

        recyclerView = binding?.idPriceListRv
        priceListAdapter = PriceListAdapter(this,requireContext())
        recyclerView?.adapter = priceListAdapter

        priceListViewModel?.getAllPriceList(sharedPreferences?.getUserId())

        priceListViewModel?.priceList?.observe(viewLifecycleOwner){
            it?.let{
                listPrice = it.toMutableList()
                priceListAdapter?.setItemsInList(it.toMutableList())
            }
        }

        // добавление элемента в прокручиваемый список
        binding?.idPriceListButtonAdd?.setOnClickListener {
            if(repository?.checkNetworkState() == true){
                val newItem = PriceListEntity(name = "", price = "", desc = "", idUser = sharedPreferences?.getUserId())
                savePriceInRoom(newItem)
            }
        }

        // обработка ответа от Room при сохранении
        priceListViewModel?.isSuccessfulInsertInRoom?.observe(viewLifecycleOwner){
            if(repository?.checkNetworkState() == true){
                savePriceInFirestore(it)
                listPrice.add(it)
                priceListAdapter?.addItemInList(it)
            }
        }

        // обработка ответа от Firestore при сохранении
        priceListViewModel?.isSuccessfulInsertInFirestore?.observe(viewLifecycleOwner){
            if(!it) {
                repository?.showToast("ошибка, попробуйте еще раз", requireContext())
            }
        }

        // обработка ответа от Firestore при обновлении
        priceListViewModel?.isSuccessfulUpdateInFirestore?.observe(viewLifecycleOwner){
            if(it){
                repository?.showToast("данные успешно обновлены", requireContext())
            }else{
                repository?.showToast("ошибка при обновлении", requireContext())
            }
        }

        // обработка ответа от Firestore при удалении
        priceListViewModel?.isSuccessfulDeleteInFirestore?.observe(viewLifecycleOwner){
            if(it){
                repository?.showToast("данные успешно удалены", requireContext())
            }else{
                repository?.showToast("ошибка при удалении", requireContext())
            }
        }

        // выход из приложения
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            repository?.exitTheApplication()
        }

    }

    // очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // функция сохранения элемента в Room
    private fun savePriceInRoom(item: PriceListEntity) {
        priceListViewModel?.insertItemPriceListInRoom(item)
    }

    // функция сохранения элемента в Firestore
    private fun savePriceInFirestore(item: PriceListEntity){
        priceListViewModel?.insertItemPriceListInFirestore(item,sharedPreferences?.getUserId())
    }

    // функция обновления старой позиции в прайс-листе
    override fun updatePrice(item: PriceListEntity,indexItem: Int) {
        priceListViewModel?.updateItemPriceListInRoom(item)
        priceListViewModel?.updateItemPriceListInFirestore(item,sharedPreferences?.getUserId())
        listPrice[indexItem] = item
        priceListAdapter?.updateItemInList(item, indexItem)
    }

    // функция удаления старой позиции из прайс-листа
    private fun deletePrice(item: PriceListEntity,indexItem: Int) {
        listPrice.removeAt(indexItem)
        priceListAdapter?.deleteItemInList(indexItem)
        priceListViewModel?.deleteItemPriceListInRoom(item)
        priceListViewModel?.deleteItemPriceListInFirestore(item,sharedPreferences?.getUserId())
    }

    // функция показа диалога о подтверждении удаления
    override fun showDialog(indexItem:Int) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {

            setTitle("Удаление")
            setMessage("Вы уверены, что хотите удалить этот элемент?")

            setPositiveButton("Да") { dialogInterface: DialogInterface, _: Int ->
                deletePrice(listPrice[indexItem],indexItem)
                dialogInterface.dismiss()
            }
            setNegativeButton("Нет") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
        }

        alertDialogBuilder.create().show()
    }

    // функция показа всплывающего сообщения
    override fun showToast(message: String?) {
        repository?.showToast(message,requireContext())
    }

}