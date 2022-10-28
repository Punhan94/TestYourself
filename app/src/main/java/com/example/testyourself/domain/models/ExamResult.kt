package com.example.testyourself.domain.models

import java.io.Serializable


data class ExamResult(
    var id: Int? = null,
    var answer: String? = null,
    var answerBoolean: Boolean?,
    var created_date: String? = null,
    var update_date: String? = null,
    var student: Int,
    var exam: Int,
    var testNumer: Int
):Serializable
