package com.example.testyourself.data.models

import com.google.gson.annotations.SerializedName

data class Lesson(
    @SerializedName("id")
    var lessonId :Int,
    @SerializedName("lessonName")
    var lessonsName:String,
    var lessonImage:String,
    var created_date: String?=null,
    var update_date: String?=null
)
