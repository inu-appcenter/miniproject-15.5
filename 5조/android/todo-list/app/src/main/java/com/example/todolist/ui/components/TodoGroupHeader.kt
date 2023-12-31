package com.example.todolist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.Data.DataClass.TodoGroup
import com.example.todolist.ui.theme.TodoListTheme

@Composable
fun TodoGroupHeader(
    modifier : Modifier,
    todoGroup : TodoGroup
){
    Card(
        modifier = modifier,
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = todoGroup.groupName,
                color = Black,
                fontSize = 22.sp,
            )
        }
    }
}

@Preview
@Composable
fun TestTodoGroupHeader(){
    TodoListTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            contentAlignment = Alignment.Center
        ){
            TodoGroupHeader(
                modifier = Modifier.width(100.dp),
                todoGroup = TodoGroup(
                    groupNum = 0,
                    groupName = "일반",
                    isImportant = false
                )
            )
        }
    }
}