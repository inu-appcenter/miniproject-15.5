package com.example.todolist.Data.DataClass

import com.google.gson.annotations.SerializedName

data class TodoGroup(
    @SerializedName("groupNum")
    val groupNum : Int,
    @SerializedName("groupName")
    val groupName : String,
    @SerializedName("isImportant")
    val isImportant : Boolean
)
