package com.example.myapplication_todo.DTO

data class TodoItem(
    val title: String,
    val content: String,
    var isChecked: Boolean = false
)
