package com.example.todolist.Data

import com.example.todolist.Data.LoginDto.User
import com.google.gson.annotations.SerializedName

data class SignUpRespDto(
    @SerializedName("commonResponse")
    val result : String,
    @SerializedName("data")
    val data : User,
    @SerializedName("message")
    val message : String
)
