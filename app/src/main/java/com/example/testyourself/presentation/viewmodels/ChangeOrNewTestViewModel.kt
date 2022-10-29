package com.example.testyourself.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyourself.domain.models.Test
import com.example.testyourself.domain.repositories.ExamApiRepository
import com.example.testyourself.domain.usecases.exam_api_usecase.GetAllExamTestsUseCase
import com.example.testyourself.domain.usecases.exam_api_usecase.PatchExamTestUseCase
import com.example.testyourself.domain.usecases.exam_api_usecase.PostNewTestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeOrNewTestViewModel @Inject constructor(
    repository: ExamApiRepository
):ViewModel() {
    val tests: MutableLiveData<List<Test>> = MutableLiveData()
    private val getAllExamTestsUseCase: GetAllExamTestsUseCase = GetAllExamTestsUseCase(repository)
    private val patchExamTestsUseCase: PatchExamTestUseCase = PatchExamTestUseCase(repository)
    private val postNewTestUseCase: PostNewTestUseCase = PostNewTestUseCase(repository)

    fun getTest(myId:Int)= viewModelScope.launch {
        getDataFromAPITest(myId)
    }

    fun patchOrPost(showTest:Int,testId:Int,thisTest: Test) = viewModelScope.launch{
        if (showTest>=0){ patchThisTest(testId,thisTest) }
        else{ postNewTest(thisTest) }
    }

    private suspend fun postNewTest(thisTest: Test){
        postNewTestUseCase.invoke(thisTest)
    }

    private suspend fun patchThisTest(testId: Int,thisTest:Test){
        patchExamTestsUseCase.invoke(
            testId,thisTest.testQuestion,thisTest.testTrueAnswer,thisTest.testFalseAnswer1,
            thisTest.testFalseAnswer2, thisTest.testFalseAnswer3,
            thisTest.oneTestSecond ?: 30, thisTest.exam
        )
    }

    private suspend fun getDataFromAPITest(examId:Int){
        val allExamTests = getAllExamTestsUseCase.invoke(examId)
        if (allExamTests.isSuccessful) {
            tests.postValue(allExamTests.body())
        }
    }


}