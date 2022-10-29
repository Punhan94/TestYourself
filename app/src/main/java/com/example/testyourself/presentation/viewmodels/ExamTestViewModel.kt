package com.example.testyourself.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyourself.domain.models.ExamResult
import com.example.testyourself.domain.models.Test
import com.example.testyourself.domain.repositories.ExamApiRepository
import com.example.testyourself.domain.usecases.exam_api_usecase.GetAllExamTestsUseCase
import com.example.testyourself.domain.usecases.exam_api_usecase.PostExamResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamTestViewModel @Inject constructor(
    val repository: ExamApiRepository
):ViewModel() {

    val tests: MutableLiveData<List<Test>> = MutableLiveData()
    private val getAllExamTestsUseCase: GetAllExamTestsUseCase = GetAllExamTestsUseCase(repository)
    private val postExamResultUseCase: PostExamResultUseCase = PostExamResultUseCase(repository)

    fun getTest(myId:Int)= viewModelScope.launch {
        getDataFromAPITest(myId)
    }

    fun postResult(examResult: ExamResult) = viewModelScope.launch {
        postDataFromApi(examResult)
    }


    private suspend fun getDataFromAPITest(examId:Int){
        val allExamTests = getAllExamTestsUseCase.invoke(examId)
        if (allExamTests.isSuccessful) {
            tests.postValue(allExamTests.body())
        }
    }

    private suspend fun postDataFromApi(
        examResult: ExamResult
    ){
        postExamResultUseCase.invoke(
            examResult
        )
    }


}