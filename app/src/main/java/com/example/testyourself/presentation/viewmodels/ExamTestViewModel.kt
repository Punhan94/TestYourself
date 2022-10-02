package com.example.testyourself.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyourself.data.models.ExamResult
import com.example.testyourself.data.models.Test
import com.example.testyourself.data.repository.ApiRepository
import kotlinx.coroutines.launch

class ExamTestViewModel(
    val repository: ApiRepository
):ViewModel() {
    val tests: MutableLiveData<List<Test>> = MutableLiveData()

    fun getTest(myId:Int)= viewModelScope.launch {
        Log.e("getTest",myId.toString())
        getDataFromAPITest(myId)
    }

    fun postResult(examResult: ExamResult) = viewModelScope.launch {
        postDataFromApi(examResult.student,examResult.exam,examResult.testNumer,
        examResult.answer ?: "",examResult.answerBoolean)
    }

    private suspend fun getDataFromAPITest(examId:Int){
        Log.e("getDataFromAPITest",examId.toString())
        if (repository.getAllExamTests(examId).isSuccessful) {
            tests.postValue(repository.getAllExamTests(examId).body())
        }
    }

    private suspend fun postDataFromApi(student: Int,
                                        exam:Int,
                                        testNum:Int,
                                        answer:String,
                                        answerBoolean:Boolean?){
        repository.postExamResult(
            student, exam, testNum, answer, answerBoolean
        )
    }
}