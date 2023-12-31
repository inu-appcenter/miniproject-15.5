package com.example.todolist.Data.LoginDto

import com.google.gson.annotations.SerializedName

data class GetAllRespDto(
    @SerializedName("commonResponse")
    val commonResponse: String,
    @SerializedName("data")
    val data: List<User>,
    @SerializedName("message")
    val message: String
)