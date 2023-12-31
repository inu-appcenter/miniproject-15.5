package com.example.todolist.Data.TodoDto

import com.google.gson.annotations.SerializedName

data class DelTodoRespDto(
    @SerializedName("data")
    val delTodoNum : Int
)
