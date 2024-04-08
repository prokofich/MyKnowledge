package com.my.knowledge.view.fragments.teacherFragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.my.knowledge.databinding.FragmentAccountTeacherBinding
import com.my.knowledge.model.constant.UserType
import com.my.knowledge.model.database.Room.entity.MyAccountEntity
import com.my.knowledge.model.database.sharedpreferences.SharedPreferences
import com.my.knowledge.model.repository.Repository
import com.my.knowledge.viewmodel.teacherviewmodel.AccountTeacherViewModel

class AccountTeacherFragment : Fragment() {

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var binding: FragmentAccountTeacherBinding? = null
    private var repository: Repository? = null
    private var sharedPreferences: SharedPreferences? = null
    private var accountTeacherViewModel: AccountTeacherViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountTeacherBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = Repository()
        sharedPreferences = SharedPreferences(requireContext())
        accountTeacherViewModel = ViewModelProvider(this)[AccountTeacherViewModel::class.java]

        accountTeacherViewModel?.getInfoMyAccount(sharedPreferences?.getUserId().toString())

        if(repository?.checkNetworkState() == true){
            accountTeacherViewModel?.getImageFromStorageByUrl(sharedPreferences?.getUrlPhotoFromProfile())
        }

        accountTeacherViewModel?.photoFromStorage?.observe(viewLifecycleOwner){

            showImageForProfile(it)

        }

        // показ данных профиля
        accountTeacherViewModel?.infoMyAccount?.observe(viewLifecycleOwner){

            showMyInfoTeacher(it)

        }

        // показ ответа после редактирования профиля
        accountTeacherViewModel?.isSuccessful?.observe(viewLifecycleOwner){

            if(it){
                repository?.showToast("данные успешно сохранены",requireContext())
            }else{
                repository?.showToast("ошибка,данные не сохранены",requireContext())
            }

        }


        accountTeacherViewModel?.urlPhoto?.observe(viewLifecycleOwner){

            sharedPreferences?.setUrlPhotoFromProfile(it)
            accountTeacherViewModel?.updateUrlPhotoFromProfile(it,sharedPreferences?.getUserId())

        }

        // открытие галереи для выбора аватарки
        binding?.idAccountIvPhoto?.setOnClickListener {
            if(binding?.idAccountTeacherTvRedact?.text == "сохранить"){
                chooseImage()
            }else{
                repository?.showToast("нажмите - редактировать - ",requireContext())
            }
        }

        // разрешение на редактирование имени и фамилии
        binding?.idAccountTeacherTvRedact?.setOnClickListener {
            if(repository?.checkNetworkState() == true){

                if(binding?.idAccountTeacherTvRedact?.text == "редактировать"){

                    openOrClosedEditTextForName(true) // открытие для редактирования
                    binding?.idAccountTeacherTvRedact?.text = "сохранить"

                }else{

                    openOrClosedEditTextForName(false) // закрытие перед сохранением
                    binding?.idAccountTeacherTvRedact?.text = "редактировать"
                    updateDataFromMyProfileInLocalDatabase()

                    // сохранение в Firestore
                    accountTeacherViewModel?.setFirstAndLastName(binding?.idAccountTvFirstName?.text.toString(),
                        binding?.idAccountTvLastName?.text.toString(), sharedPreferences?.getUserId())

                }

            }else{
                repository?.showToast("проверьте интернет соединение",requireContext())
            }
        }

        // изменение описания учителя
        binding?.idAccountTeacherTvRedact1?.setOnClickListener {
            if(repository?.checkNetworkState() == true){

                if(binding?.idAccountTeacherTvRedact1?.text == "изменить"){

                    openOrClosedEditText1(true) // открытие для редактирования
                    binding?.idAccountTeacherTvRedact1?.text = "сохранить"

                }else{

                    openOrClosedEditText1(false)
                    binding?.idAccountTeacherTvRedact1?.text = "изменить"
                    updateDataFromMyProfileInLocalDatabase()
                    updateDataFromMyProfileInFirestore(binding?.idAccountTeacherTvMyDesc?.text.toString(),"myDescription")

                }

            }else{
                repository?.showToast("проверьте интернет соединение",requireContext())
            }
        }

        // изменение опыта работы
        binding?.idAccountTeacherTvRedact2?.setOnClickListener {
            if(repository?.checkNetworkState() == true){

                if(binding?.idAccountTeacherTvRedact2?.text == "изменить"){

                    openOrClosedEditText2(true) // открытие для редактирования
                    binding?.idAccountTeacherTvRedact2?.text = "сохранить"

                }else{

                    openOrClosedEditText2(false)
                    binding?.idAccountTeacherTvRedact2?.text = "изменить"
                    updateDataFromMyProfileInLocalDatabase()
                    updateDataFromMyProfileInFirestore(binding?.idAccountTeacherTvOpitRaboti?.text.toString(),"experience")

                }

            }else{
                repository?.showToast("проверьте интернет соединение",requireContext())
            }
        }

        // изменение образования
        binding?.idAccountTeacherTvRedact3?.setOnClickListener {
            if(repository?.checkNetworkState() == true){

                if(binding?.idAccountTeacherTvRedact3?.text == "изменить"){

                    openOrClosedEditText3(true) // открытие для редактирования
                    binding?.idAccountTeacherTvRedact3?.text = "сохранить"

                }else{

                    openOrClosedEditText3(false)
                    binding?.idAccountTeacherTvRedact3?.text = "изменить"
                    updateDataFromMyProfileInLocalDatabase()
                    updateDataFromMyProfileInFirestore(binding?.idAccountTeacherTvEducation?.text.toString(),"education")

                }

            }else{
                repository?.showToast("проверьте интернет соединение",requireContext())
            }
        }

        // выход из приложения
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            repository?.exitTheApplication()
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            filePath = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, filePath)
            binding?.idAccountIvPhoto?.load(bitmap)
            accountTeacherViewModel?.uploadPhotoForProfile(bitmap)

        }

    }

    // очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // функция показа основной информации в профиле учителя
    private fun showMyInfoTeacher(data:MyAccountEntity?){
        binding?.idAccountTvLastName?.setText(data?.lastName)
        binding?.idAccountTvFirstName?.setText(data?.firstName)
        binding?.idAccountTeacherTvMyDesc?.setText(data?.description)
        binding?.idAccountTeacherTvOpitRaboti?.setText(data?.experience)
        binding?.idAccountTeacherTvEducation?.setText(data?.education)
    }

    // функция показа аватарки
    private fun showImageForProfile(bitmap: Bitmap?){
        binding?.idAccountIvPhoto?.load(bitmap)
    }

    // функция открытия/закрытия для редактирования поля ввода
    private fun openOrClosedEditTextForName(isOpen:Boolean){
        binding?.idAccountTvFirstName?.isEnabled = isOpen
        binding?.idAccountTvLastName?.isEnabled = isOpen
    }

    // функция открытия/закрытия для редактирования поля ввода
    private fun openOrClosedEditText1(isOpen: Boolean){
        binding?.idAccountTeacherTvMyDesc?.isEnabled = isOpen
    }

    // функция открытия/закрытия для редактирования поля ввода
    private fun openOrClosedEditText2(isOpen: Boolean){
        binding?.idAccountTeacherTvOpitRaboti?.isEnabled = isOpen
    }

    // функция открытия/закрытия для редактирования поля ввода
    private fun openOrClosedEditText3(isOpen: Boolean){
        binding?.idAccountTeacherTvEducation?.isEnabled = isOpen
    }

    // функция обновления данных из моего профиля в LocalDatabase
    private fun updateDataFromMyProfileInLocalDatabase(){
        accountTeacherViewModel?.updateInfoMyAccount(MyAccountEntity(SharedPreferences(requireContext()).getUserId(),
            binding?.idAccountTvFirstName?.text.toString(),binding?.idAccountTvLastName?.text.toString(),
            UserType.Teacher.user, binding?.idAccountTeacherTvMyDesc?.text.toString(),
            binding?.idAccountTeacherTvOpitRaboti?.text.toString(), binding?.idAccountTeacherTvEducation?.text.toString(),
            sharedPreferences?.getUrlPhotoFromProfile().toString()
        ))
    }

    // функция обновления данных из моего профиля в Firestore
    private fun updateDataFromMyProfileInFirestore(data:String,typeData:String){
        accountTeacherViewModel?.setDataFromMyProfile(data,typeData, sharedPreferences?.getUserId())
    }

    // функция выбора фотографии из галереи
    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

}