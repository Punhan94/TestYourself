package com.example.testyourself.domain.repositories
import com.example.testyourself.domain.models.*
import retrofit2.Response

interface ExamApiRepository {

    suspend fun getAllExam() : Response<List<Exam>>

    suspend fun getAllGroup():Response<List<Group>>

    suspend fun getAllLesson() : Response<List<Lesson>>

    suspend fun getAllExamTests( thisExamId: Int) : Response<List<Test>>

    suspend fun getAllExamResult( thisExamId: Int) : Response<List<ExamResult>>

    suspend fun postNewTest(test: Test) : Response<Test>


    suspend fun patchExamTests(id: Int,
                               testQuestion:String,
                               testTrueAnswer:String,
                               testFalseAnswer1:String,
                               testFalseAnswer2:String,
                               testFalseAnswer3:String,
                               oneTestSecond:Int,
                               exam:Int,
    ) : Response<Test>

    suspend fun getUserProfile(studentId: Int) : Response<UserProfile>

    suspend fun getAllStudent():Response<List<Student>>

    suspend fun getAllUserProfile():Response<List<UserProfile>>

    suspend fun postNewUserProfile(userProfile: UserProfile) : Response<UserProfile>

    suspend fun getEmailForStudent(studentName:String):Response<List<Student>>

    suspend fun patchUserProfile(studentId: Int,
                                 id:Int,
                                 student:Int,
                                 firstName:String,
                                 lastName:String,
                                 age:Int
    ) : Response<UserProfile>

    suspend fun postStudent(student: Student) : Response<Student>

    suspend fun getAllThisExamTests(studentId: Int) :Response<List<Test>>

    suspend fun postExamResult(examResult: ExamResult): Response<ExamResult>
}