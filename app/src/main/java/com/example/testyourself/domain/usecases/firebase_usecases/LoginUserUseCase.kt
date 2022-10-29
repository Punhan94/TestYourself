package com.example.testyourself.domain.usecases.firebase_usecases

import com.example.testyourself.domain.repositories.FirebaseRepository

class LoginUserUseCase(val firebaseRepo: FirebaseRepository) {

    fun loginUserFirebase(email:String,password:String){
        firebaseRepo.loginUserFirebase(email,password)
    }
}