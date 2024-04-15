package com.my.knowledge.model.database.firebase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.my.knowledge.model.constant.OperationStatus
import com.my.knowledge.model.constant.Teachers
import com.my.knowledge.model.constant.Teachers_and_Students
import java.io.ByteArrayOutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirestoreProfileManager {

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    // функция получения картинки из Storage
    suspend fun getImage(url : String) : Bitmap? {
        return suspendCoroutine { continuation ->

            val oneMegabyte = 1024 * 1024.toLong()
            storage.reference.child("images/$url").getBytes(oneMegabyte)
                .addOnSuccessListener { bytes ->
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    continuation.resume(bitmap)
                }
                .addOnFailureListener {
                    continuation.resume(null)
                }

        }
    }

    // функция обновления имени и фамилии из профиля в Firestore
    suspend fun updateName(firstName : String , lastName : String , userId : String) : Boolean {
        return suspendCoroutine { continuation ->

            val data = hashMapOf<String,Any>("first_name" to firstName,"last_name" to lastName)
            firestore.collection(Teachers).document(userId).update(data)
                .addOnSuccessListener {
                    firestore.collection(Teachers_and_Students).document(userId).set(data,
                        SetOptions.merge())
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

    // функция обновления данных из профиля в Firestore
    suspend fun updateData(dataFromProfile : String , typeDataFromProfile : String , userId : String) : Boolean {
        return suspendCoroutine { continuation ->

            val data = hashMapOf<String,Any>(typeDataFromProfile to dataFromProfile)

            firestore.collection(Teachers).document(userId).update(data)
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

    // функция загрузки аватарки из профиля в Storage
    suspend fun uploadPhoto(bitmap:Bitmap):String{
        return suspendCoroutine { continuation ->

            val imageReference = FirebaseStorage.getInstance().reference.child("images/${System.currentTimeMillis()}.jpg")
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

    // функция обновления url адреса аватарки из профиля в Firestore
    fun updateUrlPhoto(url:String,userId: String){

        val data = mapOf("url_photo" to url)
        firestore.collection(Teachers_and_Students).document(userId).update(data)
        firestore.collection(Teachers).document(userId).update(data)

    }


}