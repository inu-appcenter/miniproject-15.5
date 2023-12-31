package com.example.todolist.Data.LoginDto

import com.google.gson.annotations.SerializedName

data class SignUpReqDto(
    @SerializedName("clientEmail")
    val clientEmail : String,
    @SerializedName("clientId")
    val id : String,
    @SerializedName("clientName")
    val name : String,
    @SerializedName("clientPassword")
    val passwd : String,
    @SerializedName("clientPhoneNum")
    val phoneNum : String,
    @SerializedName("clientRole")
    val role : List<String> = listOf("ROLE_USER")
)
