package com.example.testyourself.domain.usecases.exam_api_usecase

import com.example.testyourself.data.network.api.ExamRetrofitInstance
import com.example.testyourself.domain.models.UserProfile
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response

class PatchUserProfileUseCase(val examApiRepo: ExamApiRepository) {
    suspend fun invoke(studentId: Int,
                       id:Int,
                       student:Int,
                       firstName:String,
                       lastName:String,
                       age:Int
    ) : Response<UserProfile> {
        return examApiRepo.patchUserProfile(studentId,
            id, student ,firstName, lastName,age)
    }
}