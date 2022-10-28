package com.example.testyourself.domain.usecases.exam_api_usecase
import com.example.testyourself.domain.models.Student
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response

class GetAllStudentUseCase(val examApiRepo: ExamApiRepository) {
    suspend fun getAllStudent(): Response<List<Student>> {
        return examApiRepo.getAllStudent()
    }
}