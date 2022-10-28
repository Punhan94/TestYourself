package com.example.testyourself.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyourself.domain.models.ExamResult
import com.example.testyourself.domain.models.UserProfile
import com.example.testyourself.domain.repositories.ExamApiRepository
import com.example.testyourself.domain.usecases.exam_api_usecase.GetAllExamResultUseCase
import com.example.testyourself.domain.usecases.exam_api_usecase.GetUserProfileUseCase
import com.example.testyourself.domain.usecases.exam_api_usecase.PatchUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val repository: ExamApiRepository
):ViewModel() {

    var userProfile :MutableLiveData<UserProfile> = MutableLiveData()
    var userAllAnswer:MutableLiveData<List<ExamResult>> = MutableLiveData()
    private val getAllExamResultUseCase: GetAllExamResultUseCase = GetAllExamResultUseCase(repository)
    private val getUserProfileUseCase: GetUserProfileUseCase = GetUserProfileUseCase(repository)
    private val patchUserProfileUseCase: PatchUserProfileUseCase = PatchUserProfileUseCase(repository)



    fun getAboutUser (studentId:Int) = viewModelScope.launch {
        getAboutUserProfile(studentId)
        getuserAllAnswer(studentId)
    }

    fun userProfilePATCH(userProfile: UserProfile, studentId:Int) = viewModelScope.launch{
        patchUserVM(userProfile,studentId)
    }

    private suspend fun getAboutUserProfile(studentId:Int){
        val getUserProfile = getUserProfileUseCase.getUserProfile(studentId)
        if (getUserProfile.isSuccessful) {
            userProfile.postValue(getUserProfile.body())

        }
    }

    private suspend fun getuserAllAnswer(studentId: Int){
        val allExamResult = getAllExamResultUseCase.getAllExamResult(studentId)
        if (allExamResult.isSuccessful) {
            userAllAnswer.postValue(allExamResult.body())

        }
    }


    private suspend fun patchUserVM(userProfile: UserProfile, studentId:Int){
        val a = patchUserProfileUseCase.patchUserProfile(studentId,
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