package com.my.knowledge.model.database.firebase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.my.knowledge.model.constant.OperationStatus
import com.my.knowledge.model.constant.Students
import com.my.knowledge.model.constant.Teachers
import com.my.knowledge.model.constant.Teachers_and_Students
import com.my.knowledge.model.constant.Teachers_price_list
import com.my.knowledge.model.constant.UserType
import com.my.knowledge.model.database.Room.entity.MyAccountEntity
import com.my.knowledge.model.database.Room.entity.PriceListEntity
import com.my.knowledge.model.modelData.ModelResponseLogin
import java.io.ByteArrayOutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Firestore {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    // проверка для безлогинового входа
    fun checkOpenAccount() : Boolean {
        val firestoreAuth = FirebaseAuth.getInstance()
        return firestoreAuth.currentUser!=null
    }

    // функция регистрации пользователя
    suspend fun createAccount(email : String, password : String) : String {
        return suspendCoroutine { continuation ->

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = task.result?.user?.uid
                        if(userId != null) continuation.resume(userId) else continuation.resume(OperationStatus.Correct.status)
                    } else {
                        continuation.resume(OperationStatus.Error.status)
                    }
                }
        }
    }

    // функция получения картинки из Storage
    suspend fun getImageFromStorageByUrl(url : String) : Bitmap? {
        return suspendCoroutine { continuation ->

            val storageReference = storage.reference.child("images/"+url)

            val oneMegabyte = 1024 * 1024.toLong()
            storageReference.getBytes(oneMegabyte)
                .addOnSuccessListener { bytes ->
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    continuation.resume(bitmap)
                }
                .addOnFailureListener {
                    continuation.resume(null)
                }

        }
    }

    // функция сохранения имени и фамилии
    suspend fun setFirstAnsLastName(firstName : String , lastName : String , userId : String) : Boolean {
        return suspendCoroutine { continuation ->

            val data = hashMapOf("first_name" to firstName,"last_name" to lastName)
            firestore.collection(Teachers).document(userId).set(data, SetOptions.merge())
                .addOnSuccessListener {
                    firestore.collection(Teachers_and_Students).document(userId).set(data,SetOptions.merge())
                        .addOnSuccessListener {
                            continuation.resume(true)
                        }
                        .addOnFailureListener {
                            continuation.resume(false)
                        }
                }
                .addOnFailureListener {
                    continuation.resume(false)
                }

        }
    }

    suspend fun setDataFromMyProfile(dataFromProfile : String , typeDataFromProfile : String , userId : String) : Boolean {
        return suspendCoroutine { continuation ->

            val data = hashMapOf(typeDataFromProfile to dataFromProfile)

            firestore.collection(Teachers).document(userId).set(data, SetOptions.merge())
                .addOnSuccessListener {
                    firestore.collection(Teachers_and_Students).document(userId).set(data, SetOptions.merge())
                        .addOnSuccessListener {
                            continuation.resume(true)
                        }
                        .addOnFailureListener {
                            continuation.resume(false)
                        }
                }
                .addOnFailureListener {
                    continuation.resume(false)
                }
        }

    }

    // функция отправки данных из прайс листа
    suspend fun setDataInPriceList(item : PriceListEntity , userId : String) : Boolean {
        return suspendCoroutine { continuation ->

            firestore.collection(Teachers_price_list).document(userId)
                .collection(item.id.toString()).document(item.id.toString()).set(item.toHashMap(), SetOptions.merge())
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener {
                    continuation.resume(false)
                }
        }
    }

    // функция обновления данных из прайс-листа
    suspend fun updateDataInPriceList(item : PriceListEntity, userId : String) : Boolean {
        return suspendCoroutine { continuation ->

            firestore.collection(Teachers_price_list).document(userId)
                .collection(item.id.toString()).document(item.id.toString()).update(item.toHashMap())
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener {
                    continuation.resume(false)
                }

        }
    }

    // функция удаления данных из прайс-листа
    suspend fun deleteDataInPriceList(item: PriceListEntity,userId: String) : Boolean {
        return suspendCoroutine { continuation ->

            firestore.collection(Teachers_price_list).document(userId)
                .collection(item.id.toString()).document(item.id.toString()).delete()
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener {
                    continuation.resume(false)
                }

        }
    }

    // функция отправки первичных данных после успешной регистрации
    fun setPrimaryDataAfterRegistration(item : MyAccountEntity){

        firestore.collection(Teachers_and_Students).document(item.idUser).set(item.toHashMap())

        if(item.status == UserType.Teacher.user){
            firestore.collection(Teachers).document(item.idUser).set(item.toHashMap())
        }else{
            firestore.collection(Students).document(item.idUser).set(item.toHashMap())
        }

    }

    // функция входа в аккаунт
    suspend fun loginInAccount(email: String,password: String):ModelResponseLogin{
        return suspendCoroutine { continuation ->

            val firestoreAuth = FirebaseAuth.getInstance()
            firestoreAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){

                        val userId = task.result?.user?.uid
                        if(userId != null){
                            val firestore = FirebaseFirestore.getInstance()
                            firestore.collection(Teachers_and_Students).document(userId)
                                .get()
                                .addOnSuccessListener {
                                    val typeUser = it.data?.get("status").toString()
                                    if(typeUser != ""){
                                        continuation.resume(ModelResponseLogin(typeUser,userId))
                                    }else{
                                        continuation.resume(ModelResponseLogin(OperationStatus.Error.status, OperationStatus.Error.status))
                                    }
                                }
                                .addOnFailureListener {
                                    continuation.resume(ModelResponseLogin(OperationStatus.Error.status, OperationStatus.Error.status))
                                }
                        }

                    }else{
                        continuation.resume(ModelResponseLogin(OperationStatus.Error.status, OperationStatus.Error.status))
                    }
                }

        }
    }

    // функция обновления url адреса
    fun updateUrlPhotoFromProfile(url:String,userId: String){

        val data = mapOf("url_photo" to url)
        firestore.collection(Teachers_and_Students).document(userId).update(data) // отправка данных в общую базу
        firestore.collection(Teachers).document(userId).update(data)

    }



    suspend fun uploadPhotoForProfileTeacher(bitmap:Bitmap):String{
        return suspendCoroutine { continuation ->

            val storageReference = FirebaseStorage.getInstance().reference
            val imageReference = storageReference.child("images/${System.currentTimeMillis()}.jpg")
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            val uploadTask = imageReference.putBytes(data)

            uploadTask.addOnFailureListener {
                continuation.resume(OperationStatus.Error.status)
            }.addOnSuccessListener {
                val downloadUrl = imageReference.name
                continuation.resume(downloadUrl)
            }

        }
    }

}

