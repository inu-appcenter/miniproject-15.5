package com.example.todolist.ui.screens.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todolist.Data.showToast
import com.example.todolist.Screens
import com.example.todolist.ui.components.AddTodoGroupDialog
import com.example.todolist.ui.components.TodoGroupItem
import com.example.todolist.ui.components.TopBar
import com.example.todolist.viewModel.TodoViewModel

@Composable
fun TodoGroupPage(
    navController: NavController,
    todoViewModel : TodoViewModel = hiltViewModel()
){
    val TAG = "TodoGroupPage"

    val todoGroups = todoViewModel.todoGroups.collectAsState()

    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp.dp
    val height = configuration.screenHeightDp.dp
    val context = LocalContext.current

    var showDialog by remember{mutableStateOf(false)}

    var title by remember{mutableStateOf("")}

    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(textDecoration = TextDecoration.Underline)
        ){
            append("Group을 추가하고 싶으시면 클릭해주세요")
        }
    }

    val errMsg = todoViewModel.errMsg.collectAsState()

    if (showDialog){
        AddTodoGroupDialog(
            onDismissRequest = {
                showDialog = false
            },
            addGroup = {
                todoViewModel.addTodoGroup(title)
            },
            width = (width / 5) * 4,
            height = (height / 3),
            value = title,
            onValueChanged = {
                title = it
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            title = "Todo Group Page",
            navIcon = Icons.Filled.Home,
            onNavClick = {
                navController.navigate(Screens.HomePage.name)
            },
            actionIcon = Icons.Filled.List,
        )

        Spacer(Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(space = 10.dp)
            ){
                items(todoGroups.value){
                    if (it.title != "No Group"){
                        TodoGroupItem(
                            todoGroup = it,
                            onDeleteClick = {
                                todoViewModel.delTodoGroup(it)
                            },
                            onDoneClick = {
                                todoViewModel.updateTodoGroup(it)
                            }
                        )
                    }
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showDialog = true
                    },
                text = annotatedText,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        }
    }
    LaunchedEffect(errMsg.value){
        if(errMsg.value != null){
            showToast(
                context = context,
                msg = errMsg.value!!
            )
        }
    }
}