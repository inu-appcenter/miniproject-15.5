package com.example.todolist.Data.TodoDto

import com.example.todolist.Data.DataClass.TodoGroup
import com.google.gson.annotations.SerializedName

data class GetTodoGroupsRespDto(
    @SerializedName("commonResponse")
    val commonResponse : String,
    @SerializedName("message")
    val message : String,
    @SerializedName("data")
    val data : List<TodoGroup>
)
