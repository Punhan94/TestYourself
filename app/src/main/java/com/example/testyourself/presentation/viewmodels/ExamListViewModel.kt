package com.example.testyourself.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyourself.data.models.Exam
import com.example.testyourself.data.repository.ApiRepository
import kotlinx.coroutines.launch

class ExamListViewModel(
    private val repository: ApiRepository
):ViewModel() {
    val exam : MutableLiveData<List<Exam>> = MutableLiveData()
    var arg : Int ? = null


    init {
        getExam()
    }

    fun getExam() = viewModelScope.launch {
        getDataFromAPIExam()
    }

    private suspend fun getDataFromAPIExam() {
        val newList = mutableListOf<Exam>()
        val exams = repository.getAllExam()
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