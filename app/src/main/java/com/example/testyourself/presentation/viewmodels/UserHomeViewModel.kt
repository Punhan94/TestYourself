package com.example.testyourself.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyourself.domain.models.Lesson
import com.example.testyourself.domain.repositories.ExamApiRepository
import com.example.testyourself.domain.usecases.exam_api_usecase.GetAllLessonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserHomeViewModel @Inject constructor(
    private val repository: ExamApiRepository
): ViewModel() {

    val lesson :MutableLiveData<List<Lesson>> = MutableLiveData()
    private val getAllLessonUseCase: GetAllLessonUseCase = GetAllLessonUseCase(repository)


    init {
        getLesson()
    }

    fun getLesson() = viewModelScope.launch {
        getDataFromAPILesson()
    }

    private suspend fun getDataFromAPILesson() {
        val allLesson = getAllLessonUseCase.getAllLesson()
        if (allLesson.isSuccessful) {
            lesson.postValue(allLesson.body())
        }
    }

}