package com.example.todolist.Data.TodoDto

import com.google.gson.annotations.SerializedName

data class TodoGroupReqDto(
    @SerializedName("groupName")
    val groupName : String,
    @SerializedName("isImportant")
    val isImportant : Boolean
)
