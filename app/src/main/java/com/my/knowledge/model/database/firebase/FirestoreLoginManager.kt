package com.my.knowledge.model.database.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.my.knowledge.model.constant.OperationStatus
import com.my.knowledge.model.constant.Teachers_and_Students
import com.my.knowledge.model.modelData.ModelResponseLogin
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirestoreLoginManager {

    private val firestoreAuth = FirebaseAuth.getInstance()

    // функция входа в аккаунт
    suspend fun inputInAccount(email : String , password : String) : ModelResponseLogin {
        return suspendCoroutine { continuation ->

            firestoreAuth.signInWithEmailAndPassword(email , password)
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
                                        continuation.resume(ModelResponseLogin(typeUser , userId))
                                    }else{
                                        continuation.resume(ModelResponseLogin(OperationStatus.Error.status , OperationStatus.Error.status))
                                    }
                                }
                                .addOnFailureListener {
                                    continuation.resume(ModelResponseLogin(OperationStatus.Error.status , OperationStatus.Error.status))
                                }
                        }

                    }else{
                        continuation.resume(ModelResponseLogin(OperationStatus.Error.status , OperationStatus.Error.status))
                    }
                }

        }
    }

    // проверка для безлогинового входа
    fun checkOpenAccount() : Boolean = firestoreAuth.currentUser!=null

    // функция выхода из аккаунта Firebase
    fun exitAccount() = firestoreAuth.signOut()


}