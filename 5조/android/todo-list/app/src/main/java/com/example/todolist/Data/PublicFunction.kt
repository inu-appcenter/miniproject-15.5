package com.example.todolist.Data

import android.content.Context
import android.widget.Toast

//Message 출력
fun showToast(
    context : Context,
    msg : String
){
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}