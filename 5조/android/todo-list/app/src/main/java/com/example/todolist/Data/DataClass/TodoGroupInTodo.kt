package com.example.todolist.Data.DataClass

import com.example.todolist.Data.Todo
import com.google.gson.annotations.SerializedName

data class TodoGroupInTodo(
    @SerializedName("groupNum")
    val groupNum : Int,
    @SerializedName("groupName")
    val title : String,
    @SerializedName("isImportant")
    val isImportant : Boolean,
    @SerializedName("todoList")
    val todoList : List<Todo>
)
