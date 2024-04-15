package com.my.knowledge.model.database.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.my.knowledge.model.constant.Teachers_price_list
import com.my.knowledge.model.constant.price_list
import com.my.knowledge.model.database.Room.entity.PriceListEntity
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirestorePriceListManager {

    private val firestore = FirebaseFirestore.getInstance()

    // функция отправки данных прайс листа в Firestore
    suspend fun save(item : PriceListEntity , userId : String) : Boolean {
        return suspendCoroutine { continuation ->

            firestore.collection(Teachers_price_list).document(userId)
                .collection(price_list).document(item.id.toString()).set(item.toHashMap(), SetOptions.merge())
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener {
                    continuation.resume(false)
                }
        }
    }

    // функция обновления данных прайс-листа в Firestore
    suspend fun update(item : PriceListEntity, userId : String) : Boolean {
        return suspendCoroutine { continuation ->

            firestore.collection(Teachers_price_list).document(userId)
                .collection(price_list).document(item.id.toString()).update(item.toHashMap())
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener {
                    continuation.resume(false)
                }

        }
    }

    // функция удаления данных прайс-листа в Firestore
    suspend fun delete(item : PriceListEntity, userId : String) : Boolean {
        return suspendCoroutine { continuation ->

            firestore.collection(Teachers_price_list).document(userId)
                .collection(price_list).document(item.id.toString()).delete()
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener {
                    continuation.resume(false)
                }

        }
    }

}