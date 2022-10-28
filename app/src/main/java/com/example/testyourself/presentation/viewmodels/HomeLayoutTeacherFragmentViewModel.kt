package com.example.testyourself.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyourself.domain.models.Exam
import com.example.testyourself.domain.models.Group
import com.example.testyourself.domain.repositories.ExamApiRepository
import com.example.testyourself.domain.usecases.exam_api_usecase.GetAllExamUseCase
import com.example.testyourself.domain.usecases.exam_api_usecase.GetAllGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeLayoutTeacherFragmentViewModel @Inject constructor(
    private val repository: ExamApiRepository
):ViewModel() {
    val exams : MutableLiveData<List<Exam>> = MutableLiveData()
    val groups:MutableLiveData<List<Group>> = MutableLiveData()
    private val getAllGroupUseCase: GetAllGroupUseCase = GetAllGroupUseCase(repository)
    private val getAllExamUseCase: GetAllExamUseCase = GetAllExamUseCase(repository)

    init {
        getAllTeacherData()
    }

    fun getAllTeacherData() = viewModelScope.launch {
        getDataFromAPIExams()
        getDataFromAPIGroup()
    }

    private suspend fun getDataFromAPIExams() {
        val allExam = getAllExamUseCase.getAllExam()
        if (allExam.isSuccessful) {
            exams.postValue(allExam.body())
        }
    }

    private suspend fun getDataFromAPIGroup() {
        val allGroup = getAllGroupUseCase.getAllGroup()
        if (allGroup.isSuccessful) {
            groups.postValue(allGroup.body())
        }
    }


}