package com.example.testyourself.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyourself.data.models.ExamResult
import com.example.testyourself.data.models.UserProfile
import com.example.testyourself.data.repository.ApiRepository
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val repository: ApiRepository
):ViewModel() {

    var userProfile :MutableLiveData<UserProfile> = MutableLiveData()
    var userAllAnswer:MutableLiveData<List<ExamResult>> = MutableLiveData()

    fun getAboutUser (userEmail:String,studentId:Int) = viewModelScope.launch {
        getAboutUserProfile(studentId)
        getuserAllAnswer(userEmail)
    }

    fun userProfilePost(userProfile:UserProfile,studentId:Int) = viewModelScope.launch{
        postUserProfile(userProfile,studentId)
    }

    private suspend fun getAboutUserProfile(studentId:Int){
        if (repository.getUserProfile(studentId).isSuccessful) {
            userProfile.postValue(repository.getUserProfile(studentId).body())
            Log.e("detail",userProfile.toString())
        }
    }

    private suspend fun getuserAllAnswer(userEmail:String){
        if (repository.getAllExamResult(userEmail).isSuccessful) {
            userAllAnswer.postValue(repository.getAllExamResult(userEmail).body())
            Log.e("allAnswer",userAllAnswer.toString())

        }
    }

//    private suspend fun postUserProfile(userProfile:UserProfile,studentId:Int){
//        repository.postUserProfile(studentId,userProfile)
//    }

    private suspend fun postUserProfile(userProfile:UserProfile,studentId:Int){
        val a = repository.postUserProfile(studentId,
            id = userProfile.id!!.toInt(),
            student = userProfile.student!!.toInt(),
            firstName = userProfile.firstName.toString(),
            lastName = userProfile.lastName.toString(),
            age = userProfile.age?.toInt() ?: 10
        )
        Log.e("response",a.message())
        Log.e("succes",a.isSuccessful.toString())
        Log.e("error",a.errorBody().toString())

    }



}