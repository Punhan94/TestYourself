package com.example.testyourself.data.repository

import com.example.testyourself.domain.repositories.FirebaseRepository
import com.example.testyourself.presentation.viewmodels.ObservableData.createUserJobLiveData
import com.example.testyourself.presentation.viewmodels.ObservableData.createUserLiveData
import com.example.testyourself.presentation.viewmodels.ObservableData.loginUserJobCheckLiveData
import com.example.testyourself.presentation.viewmodels.ObservableData.loginUserLiveData
import com.example.testyourself.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirebaseInstanceRepository() : FirebaseRepository {
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var authFirebase: FirebaseAuth
    private var firebaseFirestore = FirebaseFirestore.getInstance()

    init {
        authFirebase = FirebaseAuth.getInstance()
    }

    override fun createUserFirebase(email: String, password: String, jobId: Int) {
        coroutineScope.launch {
            authFirebase.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener {
                    createUserLiveData.postValue(Resource.Error("Error"))
                }
                .addOnSuccessListener {
                    createUserLiveData.postValue(Resource.Success("Success"))
                    createUserJob(email, if (jobId == 0) "student" else "teacher")
                }
        }
    }

    override fun loginUserFirebase(email: String, password: String) {
        coroutineScope.launch {
            authFirebase.signInWithEmailAndPassword(email, password)
                .addOnFailureListener {
                    loginUserLiveData.postValue(Resource.Error("Error"))
                }
                .addOnSuccessListener {
                    loginUserLiveData.postValue(Resource.Success("Success"))
                    firebaseFirestoreJobCheck(email)
                }
        }
    }

    override fun firebaseFirestoreJobCheck(email: String) {
        loginUserJobCheckLiveData?.postValue(Resource.Loading())
        coroutineScope.launch {
            firebaseFirestore.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val job = document.data.get(email)
                       job?.let {  loginUserJobCheckLiveData?.postValue(Resource.Success(job.toString()))}
                    }
                }
                .addOnFailureListener {
                    loginUserJobCheckLiveData?.postValue(Resource.Error("Error"))
                }
        }
    }

    override fun createUserJob(email: String, job: String) {
        createUserJobLiveData?.postValue(Resource.Loading())
        val myData = mutableMapOf<String, String>()
        myData[email] = job
        coroutineScope.launch {
            firebaseFirestore.collection("users").add(myData)
                .addOnSuccessListener {
                createUserJobLiveData?.postValue(Resource.Success(job))
            }
                .addOnFailureListener {
                    createUserJobLiveData?.postValue(Resource.Error("Error"))
                }
        }
    }


}