package com.example.testyourself.data.repository

import android.util.Log
import com.example.testyourself.data.models.*
import com.example.testyourself.data.network.api.ExamRetrofitInstance
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query

class ApiRepository() {
    suspend fun getAllExam() : Response<List<Exam>> {
        return ExamRetrofitInstance.api.getAllExam()
    }

    suspend fun getAllLesson() : Response<List<Lesson>> {
        return ExamRetrofitInstance.api.getAllLesson()
    }

    suspend fun getAllExamTests(@Path("pk") thisExamId: Int) : Response<List<Test>> {
        return ExamRetrofitInstance.api.getAllExamTests(thisExamId)
    }

    suspend fun getAllExamResult(@Query("pk") thisExamId: String) : Response<List<ExamResult>> {
        return ExamRetrofitInstance.api.getExamResultGET(thisExamId)
    }

    suspend fun getUserProfile(@Path("pk") studentId: Int) : Response<UserProfile> {
        return ExamRetrofitInstance.api.getUserProfile(studentId)
    }

//    suspend fun postUserProfile(@Path("pk") studentId: Int,
//                                @Body userDetail:UserProfile,
//                                ) : Response<UserProfile> {
//        Log.e("postisledi",userDetail.lastName.toString())
//        return ExamRetrofitInstance.api.postUserProfilePOST(studentId,userDetail
//            )
//    }

    suspend fun postUserProfile(@Path("pk") studentId: Int,
                                id:Int,
                                student:Int,
                                firstName:String,
                                lastName:String,
                                age:Int
    ) : Response<UserProfile> {
        return ExamRetrofitInstance.api.postUserProfilePOST(studentId,
            id, student ,firstName, lastName,age)
    }

    suspend fun postStudent(@Body student:Student) : Response<Student> {
        return ExamRetrofitInstance.api.postStudent(student)
    }

    suspend fun getAllExamTest_Exam(@Path("pk") studentId: Int) :Response<List<Test>> {
        return ExamRetrofitInstance.api.getOnlyExamTest(studentId)
    }

    suspend fun postExamResult(student: Int,
                               exam:Int,
                               testNumer:Int,
                               answer:String,
                               answerBoolean:Boolean?
                               ) :Response<ExamResult> {
        return ExamRetrofitInstance.api.postExamResult(
            student,exam,testNumer,answer, answerBoolean
        )
    }


}