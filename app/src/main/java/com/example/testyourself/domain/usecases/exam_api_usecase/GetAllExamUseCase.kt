package com.example.testyourself.domain.usecases.exam_api_usecase

import com.example.testyourself.domain.models.Exam
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response

class GetAllExamUseCase(val examApiRepo: ExamApiRepository) {
    suspend fun getAllExam(): Response<List<Exam>> {
        return examApiRepo.getAllExam()
    }
}