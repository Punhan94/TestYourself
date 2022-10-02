package com.example.testyourself.data.models

import com.google.firebase.firestore.auth.User
import com.google.gson.annotations.SerializedName
import com.google.type.DateTime

data class Exam(
    @SerializedName("id")
    var examId : Int,
    var examImage : String,
    var examName : String,
    var examClass : String?=null,
    var examDegree : String?=null,
    var examStartDate : DateTime?=null,
    var examFinishDate : DateTime?=null,
    var examMinute : Int?=null,
    var author : Int,
    var lesson : Int,
    var onlySeeGroup : String?=null,
    var created_date: String?=null,
    var update_date: String?=null
)
