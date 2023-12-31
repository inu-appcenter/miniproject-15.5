package com.example.todolist.Data.TodoDto

import com.google.gson.annotations.SerializedName

data class TodoReqDto(
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("isFinished")
    val isFinished: Boolean,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("todoDescription")
    val desc: String,
    @SerializedName("todoGroupNum")
    val groupNum: Int?,
    @SerializedName("todoLocation")
    val location: String,
    @SerializedName("todoTitle")
    val title: String
)