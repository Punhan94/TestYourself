package com.example.testyourself.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyourself.domain.models.Student
import com.example.testyourself.domain.models.UserProfile
import com.example.testyourself.domain.repositories.ExamApiRepository
import com.example.testyourself.domain.usecases.exam_api_usecase.GetAllStudentUseCase
import com.example.testyourself.domain.usecases.exam_api_usecase.GetAllUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupFragmentViewModel @Inject constructor(
    repository: ExamApiRepository
): ViewModel() {
    private val getAllUserProfileUseCase: GetAllUserProfileUseCase = GetAllUserProfileUseCase(repository)
    private val getAllStudentUseCase: GetAllStudentUseCase = GetAllStudentUseCase(repository)
    val userProfile : MutableLiveData<List<UserProfile>> = MutableLiveData()
    private val students : MutableLiveData<List<Student>> = MutableLiveData()

    init {
        getAllStudent()
    }

    private fun getAllStudent () = viewModelScope.launch {
        getAllStudents()
    }

    private suspend fun getAllStudents(){
        val allUserProfile = getAllUserProfileUseCase.invoke()
        val allStudents = getAllStudentUseCase.invoke()
        if (allUserProfile.isSuccessful) {
            userProfile.postValue(allUserProfile.body())
        }
        if (allStudents.isSuccessful){
            students.postValue(allStudents.body())
        }

    }
}