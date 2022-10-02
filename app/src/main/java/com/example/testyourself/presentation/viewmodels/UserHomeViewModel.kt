package com.example.testyourself.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyourself.data.models.Exam
import com.example.testyourself.data.models.Lesson
import com.example.testyourself.data.models.Test
import com.example.testyourself.data.models.UserProfile
import com.example.testyourself.data.network.api.ExamRetrofitInstance
import com.example.testyourself.data.repository.ApiRepository
import com.example.testyourself.utils.Resource
import kotlinx.coroutines.launch

class UserHomeViewModel(
    private val repository: ApiRepository
): ViewModel() {
    //private lateinit var firebaseFirestore: FirebaseFirestore
    val lesson :MutableLiveData<List<Lesson>> = MutableLiveData()

    init {
        getLesson()
    }

    fun getLesson() = viewModelScope.launch {
        getDataFromAPILesson()
    }

    private suspend fun getDataFromAPILesson() {
        if (repository.getAllLesson().isSuccessful) {
            lesson.postValue(repository.getAllLesson().body())
        }
    }


//    fun lessonFirebaseCall() {
//        firebaseFirestore = FirebaseFirestore.getInstance()
//        firebaseFirestore.collection("lessons")
//            .get()
//            .addOnSuccessListener { result ->
//                val resultList = arrayListOf<Lesson>()
//                for (lesson in result) {
//                    val a = Lesson(lesson.data.keys.toString(),lesson.data.values.toString())
//                    resultList.add(a)
//                }
//                lesson.value = resultList
//            }
//            .addOnFailureListener { exception ->
//            }
//    }
}