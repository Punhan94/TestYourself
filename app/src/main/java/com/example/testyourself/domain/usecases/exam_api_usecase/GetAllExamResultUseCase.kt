package com.example.testyourself.domain.usecases.exam_api_usecase
import com.example.testyourself.domain.models.ExamResult
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response
import retrofit2.http.Path

class GetAllExamResultUseCase(val examApiRepo: ExamApiRepository) {
    suspend fun getAllExamResult(thisExamId: Int) : Response<List<ExamResult>> {
        return examApiRepo.getAllExamResult(thisExamId)
    }
}