package com.example.testyourself.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyourself.domain.models.Exam
import com.example.testyourself.domain.repositories.ExamApiRepository
import com.example.testyourself.domain.usecases.exam_api_usecase.GetAllExamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamListViewModel @Inject constructor(
    private val repository: ExamApiRepository
):ViewModel() {
    val exam : MutableLiveData<List<Exam>> = MutableLiveData()
    var arg : Int ? = null
    private val getAllExamUseCase: GetAllExamUseCase = GetAllExamUseCase(repository)

    init {
        getExam()
    }

    fun getExam() = viewModelScope.launch {
        getDataFromAPIExam()
    }

    private suspend fun getDataFromAPIExam() {
        val newList = mutableListOf<Exam>()
        val exams = getAllExamUseCase.invoke()
        if (exams.isSuccessful) {
            exams.body()?.let { myexams->
                for (i in myexams){
                    Log.e("yoxlama2",i.toString())
                    Log.e("yoxlama",arg.toString())
                    if (i.lesson == arg){
                        newList.add(i)
                    } else{
                        exam.postValue(myexams)
                    }
                }
            }
            exam.postValue(newList)
        }

    }
}