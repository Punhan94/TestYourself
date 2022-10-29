package com.example.testyourself.domain.usecases.exam_api_usecase

import com.example.testyourself.domain.models.Group
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response

class GetAllGroupUseCase(val examApiRepo: ExamApiRepository) {

    suspend fun invoke(): Response<List<Group>>{
        return  examApiRepo.getAllGroup()
    }
}