package com.example.testyourself.data.network.api

import com.example.testyourself.data.models.*
import com.example.testyourself.utils.Constant
import retrofit2.Response
import retrofit2.http.*

interface ExamApiInterface {
    @GET(Constant.ALL_EXAM)
    suspend fun getAllExam() : Response<List<Exam>>

    @GET(Constant.ALL_LESSON)
    suspend fun getAllLesson() : Response<List<Lesson>>

    @GET(Constant.ALL_EXAM_TESTS)
    suspend fun getAllExamTests(@Path("pk") thisExamId: Int) : Response<List<Test>>

    @GET(Constant.ONLY_EXAM_TEST)
    suspend fun getOnlyExamTest( @Path("pk") thisExamId: Int) : Response<List<Test>>

    @GET(Constant.USER_PROFILE_PK)
    suspend fun getUserProfile(@Path("pk") thisExamId: Int) : Response<UserProfile>

    @POST(Constant.STUDENT)
    suspend fun postStudent(@Body student:Student):Response<Student>


//    @PATCH(Constant.USER_PROFILE_PK)
//    suspend fun postUserProfilePOST(@Path("pk") studentId: Int,@Body detailBody: UserProfile)
//    : Response<UserProfile>

    @FormUrlEncoded
    @PATCH(Constant.USER_PROFILE_PK)
    @Headers("Accept: application/json", "Content-type:application/json"    )
    suspend fun postUserProfilePOST(@Path("pk") studentId: Int,
                                    @Field("id") id:Int ,
                                    @Field("student") student:Int,
//                                    @Field("image") image:String,
                                    @Field("firstName") firstName:String,
                                    @Field("lastName") lastName:String,
                                    @Field("age") age:Int,
    ): Response<UserProfile>

    @GET(Constant.EXAM_RESULT)
    suspend fun getExamResultGET(@Query("pk") studentId: String) : Response<List<ExamResult>>

    @FormUrlEncoded
    @POST(Constant.EXAM_RESULT_POST)
    suspend fun postExamResult(
        @Field("student") student:Int,
        @Field("exam") exam:Int,
        @Field("testNumer") testNumer:Int,
        @Field("answer") answer:String,
        @Field("answerBoolean") answerBoolean:Boolean?=null,
        ) : Response<ExamResult>

}