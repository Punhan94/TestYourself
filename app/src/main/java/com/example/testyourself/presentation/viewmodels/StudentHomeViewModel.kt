package com.example.testyourself.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyourself.data.models.UserProfile
import com.example.testyourself.data.repository.ApiRepository
import kotlinx.coroutines.launch

class StudentHomeViewModel(
    private val repository: ApiRepository
):ViewModel() {
    var userProfile : MutableLiveData<UserProfile> = MutableLiveData()



    fun getAboutUser (studentId:Int) = viewModelScope.launch {
        getAboutUserProfile(studentId)
    }

    private suspend fun getAboutUserProfile(studentId:Int){
        if (repository.getUserProfile(studentId).isSuccessful) {
            userProfile.postValue(repository.getUserProfile(studentId).body())
            Log.e("detail",userProfile.toString())
        }
    }

}