package com.example.testyourself.domain.models

data class Group(
    var id:Int?=null,
    var groupName : String,
    var teacher : Int,
    var students : Int,
    var created_date: String?=null,
    var update_date: String?=null
)
