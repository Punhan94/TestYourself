package com.example.testyourself.domain.usecases.exam_api_usecase
import com.example.testyourself.domain.models.ExamResult
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response

class PostExamResultUseCase(val examApiRepo: ExamApiRepository) {
    suspend fun invoke(examResult: ExamResult): Response<ExamResult> {
        return examApiRepo.postExamResult(examResult)
    }
}