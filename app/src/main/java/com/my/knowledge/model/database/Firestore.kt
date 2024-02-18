package com.my.knowledge.model.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.my.knowledge.model.constant.ERROR
import com.my.knowledge.model.modelData.ModelUser
import com.my.knowledge.model.constant.Students
import com.my.knowledge.model.constant.TEACHER
import com.my.knowledge.model.constant.Teachers
import com.my.knowledge.model.modelData.ModelTeacher
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Firestore {

    // функция регистрации пользователя
    suspend fun createAccount(email:String,password:String):String{
        return suspendCoroutine { continuation ->
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = task.result?.user?.uid
                        if(userId != null) continuation.resume(userId) else continuation.resume(
                            ERROR
                        )
                    } else {
                        continuation.resume(ERROR) // регистрация не удалась
                    }
                }
        }
    }

    // функция отправки первичных данных после успешной регистрации
    suspend fun setPrimaryDataAfterRegistration(modelUser: ModelUser){
        val firestore = FirebaseFirestore.getInstance()

        val primaryData = hashMapOf(
            "first_name" to modelUser.firstName, "last_name" to modelUser.lastName,
            "status" to modelUser.status, "user_id" to modelUser.userId,
            "myDescription" to "","experience" to "","education" to "","myRating" to ""
        )

        if(modelUser.status == TEACHER){
            firestore.collection(Teachers).document(modelUser.userId).set(primaryData)
        }else{
            firestore.collection(Students).document(modelUser.userId).set(primaryData)
        }
    }

    // функция входа в аккаунт
    suspend fun loginInAccount(email: String,password: String):Boolean{
        return suspendCoroutine { continuation ->
            val firestoreAuth = FirebaseAuth.getInstance()
            firestoreAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        continuation.resume(true) // успешный вход в аккаунт
                    }else{
                        continuation.resume(false) // ошибка при входе в аккаунт
                    }
                }
        }
    }

    // функция получения моих данных как учителя
    suspend fun getMyInfoTeacher(idTeacher:String):ModelTeacher{
        return suspendCoroutine { continuation ->

            val firestore = FirebaseFirestore.getInstance()
            firestore.collection(Teachers).document(idTeacher)
                .get()
                .addOnSuccessListener { task ->


                        var modelTeacher = task.toObject(ModelTeacher::class.java)

                        if(modelTeacher!=null){
                            continuation.resume(modelTeacher)
                        }else{
                            continuation.resume(ModelTeacher("","","","","","","",""))
                        }


                }
        }
    }

}

