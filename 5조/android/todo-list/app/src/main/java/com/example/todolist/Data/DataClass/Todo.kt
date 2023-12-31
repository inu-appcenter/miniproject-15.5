package com.example.todolist.Data

import com.google.gson.annotations.SerializedName

data class Todo(
    @SerializedName("startDate")
    val startDate : String,
    @SerializedName("endDate")
    val deadDate : String,
    @SerializedName("isFinished")
    val isFinished : Boolean,
    @SerializedName("todoDescription")
    val description : String,
    @SerializedName("todoGroupNum")
    var groupNum : Int,
    @SerializedName("todoLocation")
    val location : String,
    @SerializedName("todoTitle")
    val title : String,
    @SerializedName("todoNum")
    val todoNum : Int
)