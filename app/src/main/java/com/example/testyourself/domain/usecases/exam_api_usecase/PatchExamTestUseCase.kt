package com.example.testyourself.domain.usecases.exam_api_usecase

import com.example.testyourself.data.network.api.ExamRetrofitInstance
import com.example.testyourself.domain.models.Test
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response

class PatchExamTestUseCase(val examApiRepo: ExamApiRepository) {
    suspend fun invoke(
        id: Int,
        testQuestion: String,
        testTrueAnswer: String,
        testFalseAnswer1: String,
        testFalseAnswer2: String,
        testFalseAnswer3: String,
        oneTestSecond: Int,
        exam: Int
    ): Response<Test> {
        return examApiRepo.patchExamTests(
            id,testQuestion, testTrueAnswer, testFalseAnswer1,
            testFalseAnswer2, testFalseAnswer3, oneTestSecond, exam
        )
    }
}