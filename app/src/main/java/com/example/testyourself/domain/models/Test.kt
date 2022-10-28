package com.example.testyourself.domain.models

import com.google.gson.annotations.SerializedName

data class Test(
    @SerializedName("id")
    var testId : Int?=null,
    var testQuestion:String,
    var testImage:String?=null,
    var testTrueAnswer:String,
    var testFalseAnswer1:String,
    var testFalseAnswer2:String,
    var testFalseAnswer3:String,
    var testWriteAnswer:String?=null,
    var oneTestSecond:Int?=null,
    var exam:Int,
    var created_date: String?=null,
    var update_date: String?=null
)
