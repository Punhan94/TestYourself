package com.example.testyourself.data.repository

interface FirebaseInstanceRepositoryInterface {
    fun createUserFirebase(email:String,password:String)
    fun loginUserFirebase(email:String,password:String)
}