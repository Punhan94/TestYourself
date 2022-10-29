package com.example.testyourself.utils

object Constant {
        const val BASE_URL = "https://testyourself.homes/api/"
        const val ALL_LESSON = "lesson/"
        const val ALL_GROUP = "groupstudent/"
        const val ALL_EXAM = "exam/"
        const val ALL_EXAM_TESTS = "examtest/{pk}/"
        const val ONLY_TEST_PK = "onlytest/{pk}/"
        const val ONLY_TEST = "onlytest/"
        const val EXAM_TEST_PK = "onlytest/exam/{pk}/"
        const val STUDENT = "student/"
        const val STUDENT_PK = "student/{pk}/"
        const val USER_PROFILE_PK = "userprofile/{pk}/"
        const val USER_PROFILE = "userprofile/"
        const val EXAM_RESULT_PK = "examresult/{pk}/"
        const val EXAM_RESULT_POST = "examresult/"
        const val TRUE_ANSWER_COUNT = "Ümumi düzgün cavab : "
        const val FALSE_ANSWER_COUNT = "Ümumi səhv cavab : "
        const val NULL_ANSWER_COUNT = "Ümumi boş cavab : "
        const val EXAM_END = "İmtahan bitdi"
        const val SHOW_RESULT = "Nəticələri görməyə hazırsınız?"
        const val OK = "hə"
        const val NO = "yox"
        const val TEST_NUM = "Test No:"
        const val EMAIL_VALIDATE = "Emaili düzgün yazın"
        const val PASSWORD_REPEAT_PROBLEM ="Şifrə ilə təkrar şifrə uyğun deyil"
        const val PASSWORD_VALIDATE = "Şifrədə ən azı 7 simvol olmalıdır"
        const val FORM_PROBLEM = "Formu düzgün doldurun"
        const val EMAIL_PATTERN =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        var STUDENT_ID:Int?=null
}