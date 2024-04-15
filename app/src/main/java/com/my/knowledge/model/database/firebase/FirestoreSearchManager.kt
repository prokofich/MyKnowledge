package com.my.knowledge.model.database.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.my.knowledge.model.constant.Students
import com.my.knowledge.model.constant.Teachers
import com.my.knowledge.model.constant.Teachers_price_list
import com.my.knowledge.model.constant.price_list
import com.my.knowledge.model.modelData.ModelStudent
import com.my.knowledge.model.modelData.ModelTeacher
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirestoreSearchManager {

    private val firestore = FirebaseFirestore.getInstance()

    // асинхронная функция получения всех учеников
    suspend fun getStudents() : MutableList<ModelStudent>{
        return suspendCoroutine { continuation ->

            val modelList = mutableListOf<ModelStudent>()

            firestore.collection(Students).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val model = document.toObject(ModelStudent::class.java)
                        modelList.add(model)
                    }
                    continuation.resume(modelList)
                }
                .addOnFailureListener {
                    continuation.resume(modelList)
                }

        }
    }

    // асинхронная функция получения всех учителей
    suspend fun getTeachers() : MutableList<ModelTeacher>{
        return suspendCoroutine { continuation ->

            val modelList = mutableListOf<ModelTeacher>()

            firestore.collection(Teachers).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val model = document.toObject(ModelTeacher::class.java)
                        modelList.add(model)
                    }
                    continuation.resume(modelList)
                }
                .addOnFailureListener {
                    continuation.resume(modelList)
                }

        }
    }

    suspend fun getPredmetsById(idUser:String) : MutableList<String>{
        return suspendCoroutine { continuation ->

            val listName = mutableListOf<String>()

            firestore.collection(Teachers_price_list).document(idUser)
                .collection(price_list).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val name = document.getString("name")
                        name?.let {
                            listName.add(name)
                        }
                    }
                    continuation.resume(listName)
                }
                .addOnFailureListener {
                    continuation.resume(listName)
                }

        }
    }

}