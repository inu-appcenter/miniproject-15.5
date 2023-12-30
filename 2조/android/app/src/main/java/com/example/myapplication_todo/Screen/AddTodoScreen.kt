@file:Suppress("DEPRECATION")

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.RowScopeInstance.align
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication_todo.DTO.TodoItem
import com.example.myapplication_todo.Screen.Appbar
import com.example.myapplication_todo.Screen.LoginScreen
import com.example.myapplication_todo.Screen.MyDatePickerDialog
import com.example.myapplication_todo.Screen.Screens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoScreen(
    navController: NavController
) {

    var title by remember { mutableStateOf("") }
    var context by remember { mutableStateOf("") }

    val todoItems = remember { mutableStateListOf<TodoItem>() }

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }


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
                        menuIcon = null
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .border(color = Color.Gray, width = 1.dp),
                            value = title,
                            onValueChange = { title = it },
                            placeholder = {
                                Text(text = "제목을 입력해 주세요")
                            }
                        )

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .border(color = Color.Gray, width = 1.dp),
                            value = context,
                            onValueChange = { context = it },
                            placeholder = {
                                Text(text = "내용을 입력해 주세요")
                            }
                        )

                        Spacer(Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {

                            if (showDatePicker) {
                                MyDatePickerDialog(
                                    navController = navController,
                                    onDateSelected = { date ->
                                        selectedDate = date
                                        showDatePicker = false
                                    },
                                    onDismiss = { showDatePicker = false }
                                )
                            } else {
                                // 달력 버튼
                                Button(onClick = { showDatePicker = true }) {
                                    Text(text = "날짜 선택")
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }

                        }

                        Text(
                            text = "Selected Date : $selectedDate",
                            modifier = Modifier
                                .padding(20.dp),
                            color = Color.Black
                        )

                        Button(onClick = { /*TODO*/
                            navController.navigate(Screens.MyTodoScreen.name)
                        }) {
                            Text(text = "완료")
                        }
                    }
                }
            }


            /*

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = { /*TODO*/
            navController.navigate(Screens.MyDatePickerDialog.name)},
            contentPadding = PaddingValues(12.dp),
            modifier = Modifier.wrapContentWidth()
            ) {
                Text(text = "날짜 선택")
            }

            Spacer(modifier = Modifier.width(20.dp))

            Button(
                onClick = { /*TODO*/
                    val newTodo = TodoItem(title = title, content = context)
                    todoItems.add(newTodo)

                    navController.navigate(Screens.MyTodoScreen.name) },
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(text = "완료")

            }


            Spacer(modifier = Modifier.width(20.dp))

        }

         */

        }
    )
}

@Preview
@Composable
fun prevAdd() {
    AddTodoScreen(navController = rememberNavController())
}