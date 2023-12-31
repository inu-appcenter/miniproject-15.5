package com.example.todolist.ui.screens.todo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todolist.ui.components.TopBar
import com.example.todolist.viewModel.TodoViewModel

@Composable
fun DescriptionPage(
    navController : NavController,
    id : String,
    todoViewModel : TodoViewModel
){
    val TAG = "DescriptionPage"

    Log.d(TAG, "id : ${id}")
    val todo = todoViewModel.getTodoById(id)

    //scroll 정보
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(scrollState)
    ){
        TopBar(
            title = "DescriptionPage",
            navIcon = Icons.Filled.Home,
            onNavClick = {
                navController.navigateUp()
            },
            actionIcon = Icons.Filled.Edit,
            onActionClick = { }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ){
            Spacer(Modifier.height(10.dp))

            Card {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = todo.title,
                    fontSize = 26.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(500)
                )
            }

            Spacer(Modifier.height(10.dp))

            Card{
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = todo.description,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }

            Spacer(Modifier.height(5.dp))

            Card{
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = todo.location,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }

            Spacer(Modifier.height(5.dp))

            Card{
                Row(
                    modifier = Modifier.fillMaxWidth(),

                ){
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .background(color = Color.LightGray, shape = RoundedCornerShape(25.dp)),
                        text = todo.startDate.substring(5),
                        color = Color.Black,
                        fontSize = 20.sp
                    )

                    Text(
                        modifier = Modifier.weight(1f),
                        text = "~",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 20.sp
                    )

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .background(color = Color.LightGray, shape = RoundedCornerShape(25.dp)),
                        text = todo.deadDate.substring(5),
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}