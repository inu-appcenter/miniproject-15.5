package com.example.todolist.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todolist.ui.theme.TodoListTheme

@Composable
fun FindIdPage(
    navController : NavController
){
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = White)
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
    ){
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "아이디 찾기",
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight(600),
        )

        Spacer(Modifier.weight(1f))



        Spacer(Modifier.weight(1f))
    }
}

@Preview
@Composable
fun TestFindIdPage(){
    TodoListTheme {
        FindIdPage(navController = rememberNavController())
    }
}