package com.example.testyourself.data.models

import java.io.Serializable

data class Student(
    var id:Int?=null,
    var studentName:String?=null,
    var studentGroup:Int?=null
):Serializable
