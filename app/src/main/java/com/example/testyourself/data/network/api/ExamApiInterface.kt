package com.example.testyourself.data.network.api

import com.example.testyourself.domain.models.*
import com.example.testyourself.utils.Constant
import retrofit2.Response
import retrofit2.http.*

interface ExamApiInterface {
    @GET(Constant.ALL_EXAM)
    suspend fun getAllExam() : Response<List<Exam>>

    @GET(Constant.ALL_GROUP)
    suspend fun getAllGroup() : Response<List<Group>>

    @GET(Constant.ALL_LESSON)
    suspend fun getAllLesson() : Response<List<Lesson>>

    @GET(Constant.ALL_EXAM_TESTS)
    suspend fun getAllExamTests(@Path("pk") thisExamId: Int) : Response<List<Test>>

    @GET(Constant.EXAM_TEST_PK)
    suspend fun getExamTestPK(@Path("pk") thisExamId: Int) : Response<List<Test>>

    @POST(Constant.ONLY_TEST)
    suspend fun postNewTest(@Body test: Test) : Response<Test>

    @FormUrlEncoded
    @PATCH(Constant.ONLY_TEST_PK)
    suspend fun patchExamTests(  @Path("pk") id: Int,
                                 @Field("testQuestion") testQuestion:String,
                                 @Field("testTrueAnswer") testTrueAnswer:String,
                                 @Field("testFalseAnswer1") testFalseAnswer1:String,
                                 @Field("testFalseAnswer2") testFalseAnswer2:String,
                                 @Field("testFalseAnswer3") testFalseAnswer3:String,
                                 @Field("oneTestSecond") oneTestSecond:Int,
                                 @Field("exam") exam:Int,
    ): Response<Test>

    @GET(Constant.USER_PROFILE_PK)
    suspend fun getUserProfile(@Path("pk") thisExamId: Int) : Response<UserProfile>

    @GET(Constant.STUDENT)
    suspend fun getEmailForStudent(@Query("studentName") studentName:String):Response<List<Student>>


    @POST(Constant.STUDENT)
    suspend fun postStudent(@Body student: Student):Response<Student>

    @POST(Constant.USER_PROFILE)
    suspend fun postNewUserProfile(@Body userProfile: UserProfile):Response<UserProfile>

    @GET(Constant.USER_PROFILE)
    suspend fun getAllUserProfile():Response<List<UserProfile>>

    @GET(Constant.STUDENT)
    suspend fun getAllStudent():Response<List<Student>>

    
    @FormUrlEncoded
    @PATCH(Constant.USER_PROFILE_PK)
    suspend fun patchUserProfile(@Path("pk") studentId: Int,
                                 @Field("id") id:Int,
                                 @Field("student") student:Int,
//                                    @Part MultipartBody.Part image,
                                 @Field("firstName") firstName:String,
                                 @Field("lastName") lastName:String,
                                 @Field("age") age:Int,
    ): Response<UserProfile>

    @GET(Constant.EXAM_RESULT_PK)
    suspend fun getExamResult(@Path("pk") studentId: Int) : Response<List<ExamResult>>

    @POST(Constant.EXAM_RESULT_POST)
    suspend fun postExamResult(@Body examResult: ExamResult) : Response<ExamResult>

}