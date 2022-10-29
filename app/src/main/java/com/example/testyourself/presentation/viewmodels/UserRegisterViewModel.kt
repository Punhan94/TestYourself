package com.example.testyourself.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.testyourself.data.repository.FirebaseInstanceRepository

class UserRegisterViewModel:ViewModel() {

     var repository:FirebaseInstanceRepository=FirebaseInstanceRepository()


    fun createUser(email: String, password: String, jobId: Int) {
        repository.createUserFirebase(email, password, jobId)
    }

    fun loginUserFirebase(email:String, password:String){
        repository.loginUserFirebase(email, password)
    }

}