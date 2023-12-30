package com.example.myapplication_todo.DTO

import com.google.gson.annotations.SerializedName

data class Todo(
    @SerializedName("startDate")
    val startDate : String,
    @SerializedName("endDate")
    val deadDate : String,
    @SerializedName("isFinished")
    val isFinished : Boolean,
    @SerializedName("Content")
    val content : String,
    @SerializedName("Title")
    val title : String,
    @SerializedName("todoNum")
    val todoNum : Int
)
