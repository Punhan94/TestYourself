package com.example.testyourself.domain.usecases.exam_api_usecase
import com.example.testyourself.domain.models.UserProfile
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response
import retrofit2.http.Path

class GetUserProfileUseCase(val examApiRepo: ExamApiRepository) {
    suspend fun invoke(studentId: Int) : Response<UserProfile> {
        return examApiRepo.getUserProfile(studentId)
    }
}