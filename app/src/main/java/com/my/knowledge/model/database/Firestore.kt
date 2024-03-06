package com.my.knowledge.model.database

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.toObject
import com.my.knowledge.model.constant.ERROR
import com.my.knowledge.model.constant.STUDENT
import com.my.knowledge.model.modelData.ModelUser
import com.my.knowledge.model.constant.Students
import com.my.knowledge.model.constant.TEACHER
import com.my.knowledge.model.constant.Teachers
import com.my.knowledge.model.constant.Teachers_and_Students
import com.my.knowledge.model.modelData.ModelTeacher
import io.grpc.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Firestore {

    // проверка для безлогинового входа
    fun checkOpenAccount():Boolean{
        val firestoreAuth = FirebaseAuth.getInstance()
        return firestoreAuth.currentUser!=null
    }

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

    // функция сохранения имени и фамилии
    suspend fun setFirstAnsLastName(firstName:String,lastName:String,userId:String):Boolean{
        return suspendCoroutine { continuation ->

            val firestore = FirebaseFirestore.getInstance()
            var data = hashMapOf("first_name" to firstName,"last_name" to lastName)
            firestore.collection(Teachers).document(idUser).set(data)
                .addOnSuccessListener {
                    firestore.collection(Teachers_and_Students).document(idUser).set(data)
                        .addOnSuccessListener {
                            continuation.resume(true) // данные успешно сохранены
                        }
                        .addOnFailureListener {
                            continuation.resume(false) // ошибка при сохранении данных
                        }
                }
                .addOnFailureListener {
                    continuation.resume(false) // ошибка при сохранении данных
                }

        }
    }

    suspend fun setDataFromMyProfile(dataFromProfile:String,typeDataFromProfile:String,userId:String):Boolean{
        return suspendCoroutine { continuation ->

            val firestore = FirebaseFirestore.getInstance()
            var data = hashMapOf(typeDataFromProfile to dataFromProfile)
            firestore.collection(Teachers).document(userId).set(data)
                .addOnSuccessListener {
                    firestore.collection(Teachers_and_Students).document(userId).set(data)
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

    // функция отправки первичных данных после успешной регистрации
    suspend fun setPrimaryDataAfterRegistration(modelUser: ModelUser){
        val firestore = FirebaseFirestore.getInstance()

        val primaryData = hashMapOf(
            "first_name" to modelUser.firstName, "last_name" to modelUser.lastName,
            "status" to modelUser.status, "user_id" to modelUser.userId,
            "myDescription" to "","experience" to "","education" to "","myRating" to ""
        )

        firestore.collection(Teachers_and_Students).document(modelUser.userId).set(primaryData) // отправка данных в общую базу

        if(modelUser.status == TEACHER){
            firestore.collection(Teachers).document(modelUser.userId).set(primaryData) // отправка данных в базу учителей
        }else{
            firestore.collection(Students).document(modelUser.userId).set(primaryData) // отправка данных в базу учеников
        }

    }

    // функция входа в аккаунт
    suspend fun loginInAccount(email: String,password: String):String{
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
                                        continuation.resume(typeUser) // успешный вход в аккаунт учителя или студента
                                    }else{
                                        continuation.resume(ERROR) // ошибка входа
                                    }
                                }
                                .addOnFailureListener {
                                    continuation.resume(ERROR) // ошибка входа
                                }
                        }

                    }else{
                        continuation.resume(ERROR) // ошибка входа
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
                .addOnSuccessListener{

                    var teacher = ModelTeacher()
                    teacher.education = it.data?.get("education").toString()
                    teacher.experience = it.data?.get("experience").toString()
                    teacher.last_name = it.data?.get("last_name").toString()
                    teacher.first_name = it.data?.get("first_name").toString()
                    teacher.myDescription = it.data?.get("myDescription").toString()
                    teacher.myRating = it.data?.get("myRating").toString()
                    teacher.status = it.data?.get("status").toString()
                    teacher.user_id = it.data?.get("user_id").toString()

                    if(it.data?.get("first_name") != ""){
                        continuation.resume(teacher)
                    }

                }

        }
    }

}

