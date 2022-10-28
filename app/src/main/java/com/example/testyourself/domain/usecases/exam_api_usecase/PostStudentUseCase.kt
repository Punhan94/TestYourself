package com.example.testyourself.domain.usecases.exam_api_usecase
import com.example.testyourself.domain.models.Student
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response


class PostStudentUseCase(val examApiRepo: ExamApiRepository) {
    suspend fun postStudent( student: Student) : Response<Student> {
        return examApiRepo.postStudent(student)
    }
}