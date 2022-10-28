package com.example.testyourself.domain.usecases.exam_api_usecase

import com.example.testyourself.data.network.api.ExamRetrofitInstance
import com.example.testyourself.domain.models.Lesson
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response

class GetAllLessonUseCase(val examApiRepo: ExamApiRepository) {
    suspend fun getAllLesson() : Response<List<Lesson>> {
        return examApiRepo.getAllLesson()
    }
}