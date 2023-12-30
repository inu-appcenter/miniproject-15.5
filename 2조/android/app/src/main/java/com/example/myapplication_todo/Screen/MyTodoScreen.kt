package com.example.myapplication_todo.Screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication_todo.DTO.TodoItem
import com.example.myapplication_todo.ui.theme.MyApplication_TodoTheme

private val todoItems = mutableListOf<TodoItem>()


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTodoScreen(
    navController : NavController
) {
    if(todoItems.isEmpty()) {
        todoItems.add(TodoItem("할 일1", "세부내용"))
        todoItems.add(TodoItem("할 일2", "세부내용"))
        todoItems.add(TodoItem("할 일3", "세부내용"))
    }


    Scaffold(
        content = {
            Box(
                modifier = Modifier.background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                ) {
                    Appbar(
                        title = "투두리스트",
                        navIcon = Icons.Filled.ArrowBack,
                        onNavClick = {
                            navController.navigateUp()
                        },
                        menuIcon = Icons.Filled.Add,
                        onMenuClick = {
                            navController.navigate(Screens.AddTodoScreen.name)
                        }
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(10.dp)
                    ) {
                        items(todoItems) { model ->
                            ListItem(model = model)

                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ListItem(model: TodoItem) {

    var isChecked = remember{ mutableStateOf(false) }
    val buttonColor = remember { mutableStateOf(Color.DarkGray) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        val paddingModifier = Modifier.padding(10.dp)
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            modifier = paddingModifier) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = isChecked.value,
                    onCheckedChange = { newCheckedState ->
                        isChecked.value = newCheckedState
                        model.isChecked = newCheckedState
                    }
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = model.title,
                    fontSize = 24.sp,
                    fontWeight = if (model.isChecked) FontWeight.Bold else FontWeight.SemiBold,
                    color = Color.Black,
                    textDecoration = if (model.isChecked) TextDecoration.LineThrough else TextDecoration.None
                )

                Spacer(modifier = Modifier.weight(1f))


                Box() {
                    Button(
                        onClick = { buttonColor.value = Color.Gray },
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(buttonColor.value)
                    ) {
                        Text(
                            text = "좋아요",
                            color = Color.White,
                            fontSize = 15.sp
                        )

                    }
                }

            }
        }
    }
}



@Preview
@Composable
fun PrevTodo() {
    MyApplication_TodoTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            MyTodoScreen(navController = rememberNavController())

        }
    }
}