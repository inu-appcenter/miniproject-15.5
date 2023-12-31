package com.example.todolist.Data.TodoDto

import com.google.gson.annotations.SerializedName

data class TodoRespDto(
    @SerializedName("commonResponse")
    val commonResponse : String,
    @SerializedName("data")
    val data : String?,
    @SerializedName("message")
    val message : String
)
