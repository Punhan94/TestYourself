package com.example.testyourself.domain.usecases.exam_api_usecase

import com.example.testyourself.data.network.api.ExamRetrofitInstance
import com.example.testyourself.domain.models.Test
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response
import retrofit2.http.Path

class GetAllExamTestsUseCase(val examApiRepo: ExamApiRepository) {
    suspend fun invoke(thisExamId: Int) : Response<List<Test>> {
        return examApiRepo.getAllExamTests(thisExamId)
    }
}