package com.my.knowledge.model.database.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.my.knowledge.model.constant.OperationStatus
import com.my.knowledge.model.constant.Students
import com.my.knowledge.model.constant.Teachers
import com.my.knowledge.model.constant.Teachers_and_Students
import com.my.knowledge.model.constant.UserType
import com.my.knowledge.model.database.Room.entity.MyAccountEntity
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirestoreRegistrationManager {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // функция регистрации пользователя
    suspend fun createAccount(email : String, password : String) : String {
        return suspendCoroutine { continuation ->

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = task.result?.user?.uid
                        if(userId != null) continuation.resume(userId) else continuation.resume(
                            OperationStatus.Correct.status)
                    } else {
                        continuation.resume(OperationStatus.Error.status)
                    }
                }
        }
    }

    // функция отправки первичных данных после успешной регистрации
    fun saveData(item : MyAccountEntity){

        firestore.collection(Teachers_and_Students).document(item.idUser).set(item.toHashMap())

        if(item.status == UserType.Teacher.user){
            firestore.collection(Teachers).document(item.idUser).set(item.toHashMap())
        }else{
            firestore.collection(Students).document(item.idUser).set(item.toHashMap())
        }

    }

}