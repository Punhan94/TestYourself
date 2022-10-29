package com.example.testyourself.domain.usecases.exam_api_usecase
import com.example.testyourself.domain.models.UserProfile
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response

class GetAllUserProfileUseCase(val examApiRepo: ExamApiRepository) {
    suspend fun invoke(): Response<List<UserProfile>> {
        return examApiRepo.getAllUserProfile()
    }
}