package com.example.todolist.Data.LoginDto

import com.google.gson.annotations.SerializedName

data class CommonUserRespDto(
    @SerializedName("commonResponse")
    val commonResponse: String,
    @SerializedName("data")
    val data: String,
    @SerializedName("message")
    val message: String
)