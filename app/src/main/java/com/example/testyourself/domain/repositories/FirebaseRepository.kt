package com.example.testyourself.domain.repositories

interface FirebaseRepository {

    fun createUserFirebase(email:String,password:String,jobId:Int)

    fun loginUserFirebase(email:String,password:String)

    fun firebaseFirestoreJobCheck(email: String)

    fun createUserJob(email:String,job: String)
}