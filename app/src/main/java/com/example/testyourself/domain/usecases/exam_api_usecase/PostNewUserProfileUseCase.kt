package com.example.testyourself.domain.usecases.exam_api_usecase
import com.example.testyourself.domain.models.UserProfile
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response

class PostNewUserProfileUseCase(val examApiRepo:ExamApiRepository) {
    suspend fun postNewUserProfile(userProfile: UserProfile) : Response<UserProfile> {
        return examApiRepo.postNewUserProfile(userProfile)
    }
}