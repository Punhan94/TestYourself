package com.example.testyourself.presentation.viewmodels
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
    repository: ExamApiRepository
):ViewModel() {
    val exam : MutableLiveData<List<Exam>> = MutableLiveData()
    var arg : Int ? = null
    private val getAllExamUseCase: GetAllExamUseCase = GetAllExamUseCase(repository)

    init {
        getExam()
    }

    private fun getExam() = viewModelScope.launch {
        getDataFromAPIExam()
    }

    private suspend fun getDataFromAPIExam() {
        val newList = mutableListOf<Exam>()
        val exams = getAllExamUseCase.invoke()
        if (exams.isSuccessful) {
            exams.body()?.let { myExams->
                for (i in myExams){
                    if (i.lesson == arg){ newList.add(i) }
                    else{ exam.postValue(myExams) }
                }
            }
            exam.postValue(newList)
        }

    }
}