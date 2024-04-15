package com.my.knowledge.model.database.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.my.knowledge.model.constant.Teachers_table_list
import com.my.knowledge.model.database.Room.entity.TimeTableEntity
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirestoreTableManager {

    private val firestore = FirebaseFirestore.getInstance()

    // функция сохранения данных расписания в Firestore
    suspend fun save(item : TimeTableEntity , userId : String , day : String) : Boolean {
        return suspendCoroutine { continuation ->

            firestore.collection(Teachers_table_list).document(userId)
                .collection(day).document(item.id.toString()).set(item.toHashMap(), SetOptions.merge())
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener {
                    continuation.resume(false)
                }

        }
    }

    // функция обновления данных расписания в Firestore
    suspend fun update(item : TimeTableEntity, userId : String, day : String) : Boolean {
        return suspendCoroutine { continuation ->

            firestore.collection(Teachers_table_list).document(userId)
                .collection(day).document(item.id.toString()).update(item.toHashMap())
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener {
                    continuation.resume(false)
                }

        }
    }

    // функция удаления данных расписания в Firestore
    suspend fun delete(item : TimeTableEntity , userId : String , day : String) : Boolean {
        return suspendCoroutine { continuation ->

            firestore.collection(Teachers_table_list).document(userId)
                .collection(day).document(item.id.toString()).delete()
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener {
                    continuation.resume(false)
                }

        }
    }

}