package com.example.testyourself.domain.usecases.exam_api_usecase
import com.example.testyourself.domain.models.Student
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response

class GetEmailForStudentUseCase(val examApiRepo: ExamApiRepository) {
    suspend fun getEmailForStudent(studentName:String): Response<List<Student>> {
        return examApiRepo.getEmailForStudent(studentName)
    }
}