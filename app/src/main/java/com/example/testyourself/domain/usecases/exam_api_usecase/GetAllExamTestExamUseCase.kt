package com.example.testyourself.domain.usecases.exam_api_usecase

import com.example.testyourself.domain.models.Test
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response

class GetAllExamTestExamUseCase(val examApiRepo: ExamApiRepository) {
    suspend fun invoke(studentId: Int) : Response<List<Test>> {
        return examApiRepo.getAllThisExamTests(studentId)
    }
}