package com.example.testyourself.data.repository

import com.example.testyourself.data.network.api.ExamRetrofitInstance
import com.example.testyourself.domain.models.*
import com.example.testyourself.domain.repositories.ExamApiRepository
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class ExamApiRepositoryImpl @Inject constructor() : ExamApiRepository {
    override suspend fun getAllExam() : Response<List<Exam>> {
        return ExamRetrofitInstance.api.getAllExam()
    }

    override suspend fun getAllGroup():Response<List<Group>>{
        return ExamRetrofitInstance.api.getAllGroup()
    }

    override suspend fun getAllLesson() : Response<List<Lesson>> {
        return ExamRetrofitInstance.api.getAllLesson()
    }

    override suspend fun getAllExamTests(@Path("pk") thisExamId: Int) : Response<List<Test>> {
        return ExamRetrofitInstance.api.getAllExamTests(thisExamId)
    }

    override suspend fun getAllExamResult(@Path("pk") thisExamId: Int) : Response<List<ExamResult>> {
        return ExamRetrofitInstance.api.getExamResult(thisExamId)
    }

    override suspend fun postNewTest(test: Test): Response<Test> {
        return ExamRetrofitInstance.api.postNewTest(test)
    }

    override suspend fun patchExamTests(
        id: Int,
        testQuestion: String,
        testTrueAnswer: String,
        testFalseAnswer1: String,
        testFalseAnswer2: String,
        testFalseAnswer3: String,
        oneTestSecond: Int,
        exam: Int
    ): Response<Test> {
        return ExamRetrofitInstance.api.patchExamTests(
            id,testQuestion, testTrueAnswer, testFalseAnswer1,
            testFalseAnswer2, testFalseAnswer3, oneTestSecond, exam
        )
    }

    override suspend fun getUserProfile(@Path("pk") studentId: Int) : Response<UserProfile> {
        return ExamRetrofitInstance.api.getUserProfile(studentId)
    }

    override suspend fun getAllStudent():Response<List<Student>>{
        return ExamRetrofitInstance.api.getAllStudent()
    }

    override suspend fun getAllUserProfile():Response<List<UserProfile>>{
        return ExamRetrofitInstance.api.getAllUserProfile()
    }

    override suspend fun postNewUserProfile(@Body userProfile: UserProfile) : Response<UserProfile> {
        return ExamRetrofitInstance.api.postNewUserProfile(userProfile)
    }

    override suspend fun getEmailForStudent(@Query("studentName") studentName:String):Response<List<Student>>{
        return ExamRetrofitInstance.api.getEmailForStudent(studentName)
    }

    override suspend fun patchUserProfile(@Path("pk") studentId: Int,
                                 id:Int,
                                 student:Int,
                                 firstName:String,
                                 lastName:String,
                                 age:Int
    ) : Response<UserProfile> {
        return ExamRetrofitInstance.api.patchUserProfile(studentId,
            id, student ,firstName, lastName,age)
    }

    override suspend fun postStudent(@Body student: Student) : Response<Student> {
        return ExamRetrofitInstance.api.postStudent(student)
    }

    override suspend fun getAllThisExamTests(@Path("pk") studentId: Int) :Response<List<Test>> {
        return ExamRetrofitInstance.api.getExamTestPK(studentId)
    }

    override suspend fun postExamResult(@Body examResult: ExamResult): Response<ExamResult>{
        return ExamRetrofitInstance.api.postExamResult(examResult)
    }

}