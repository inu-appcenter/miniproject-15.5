package com.example.todolist.Data.LoginDto

import com.google.gson.annotations.SerializedName

data class UpdateUserReqDto(
    @SerializedName("clientEmail")
    val email : String,
    @SerializedName("clientPassword")
    val passwd : String,
    @SerializedName("clientPhoneNum")
    val phoneNum : String
)
