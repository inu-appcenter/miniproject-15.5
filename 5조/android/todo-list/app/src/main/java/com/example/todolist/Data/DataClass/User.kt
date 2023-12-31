package com.example.todolist.Data.LoginDto

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("clientEmail")
    val clientEmail: String,
    @SerializedName("clientId")
    val clientId: String,
    @SerializedName("clientName")
    val clientName: String,
    @SerializedName("clientPassword")
    val clientPassword: String,
    @SerializedName("clientPhoneNum")
    val clientPhoneNum: String,
    @SerializedName("clientRole")
    val clientRole: List<String> = listOf("ROLE_USER")
)