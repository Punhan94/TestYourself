package com.example.testyourself.domain.usecases.exam_api_usecase
import com.example.testyourself.domain.models.Test
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response

class PostNewTestUseCase(val examApiRepo: ExamApiRepository) {
    suspend fun postNewTest(test: Test): Response<Test> {
        return examApiRepo.postNewTest(test)
    }
}