package com.example.testyourself.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserProfile(
    @SerializedName("id")
    var id: Int?=null,
    @SerializedName("student")
    var student: Int?=null,
    @SerializedName("image")
    var image: String?=null,
    @SerializedName("firstName")
    var firstName: String?=null,
    @SerializedName("lastName")
    var lastName: String?=null,
    @SerializedName("age")
    var age: Int?=null,
    @SerializedName("created_date")
    var created_date: String?=null,
    @SerializedName("update_date")
    var update_date: String?=null
): Serializable
