package com.example.testyourself.domain.usecases.firebase_usecases

import com.example.testyourself.domain.repositories.FirebaseRepository

class CreateUserUseCase(val firebaseRepo: FirebaseRepository) {

    fun invoke(email:String, password:String, jobId:Int){
        firebaseRepo.createUserFirebase(email,password,jobId)
    }
}