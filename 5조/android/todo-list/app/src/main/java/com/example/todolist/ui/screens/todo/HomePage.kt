package com.example.todolist.ui.screens.todo

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todolist.Data.DataClass.TodoGroup
import com.example.todolist.Data.showToast
import com.example.todolist.Screens
import com.example.todolist.ui.components.LoadingDialog
import com.example.todolist.ui.components.MenuFAB
import com.example.todolist.ui.components.TodoGroupHeader
import com.example.todolist.ui.components.TodoItem
import com.example.todolist.ui.components.TopBar
import com.example.todolist.ui.components.WeekCalendar
import com.example.todolist.viewModel.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun HomePage(
    navController: NavController,
    todoViewModel : TodoViewModel = hiltViewModel()
){
    val TAG = "HomePage"

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    //화면 변수
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp.dp
    val height = configuration.screenHeightDp.dp

    //todos
    val todos = todoViewModel.todosByDate.collectAsState()

    //뒤로가기 클릭 여부
    var backPressed by remember{mutableStateOf(false)}

    val days = todoViewModel.days.collectAsState()
    var selectedDay = remember{mutableStateOf(todoViewModel.getSelectedDay())}

    val errorState = todoViewModel.errMsg.collectAsState()

    val todoNumByDateList = todoViewModel.todosByDateList.collectAsState()

    var showLoading by remember{mutableStateOf(false)}

    if (showLoading){
        LoadingDialog(
            onDismissRequest = { showLoading = false },
            width = width / 4,
            height = height / 10
        )
    }

    //back event
    BackHandler {
        //뒤로가기 두 번에 종료
        if (backPressed){
            (context as? Activity)?.finish()
        } else {
            backPressed = true
            showToast(context,"한 번 더 누르면 앱이 종료됩니다.")

            coroutineScope.launch(Dispatchers.Main){
                delay(2000)
                backPressed = false
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ){
            TopBar(
                title = "Todo-List",
                navIcon = null,
                actionIcon = Icons.Filled.Notifications,
                actionDes = "Menu",
                actionSize = 30.dp,
                onActionClick = {
                    /** Dropdown Menu 열기 */
                }
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "${selectedDay.value.year}년 ${selectedDay.value.month}월 ${selectedDay.value.day}일",
                    fontSize = 24.sp,
                    fontWeight = FontWeight(500)
                )

                Row{
                    // << 버튼
                    IconButton(
                        onClick = {
                            todoViewModel.setPrevWeek()
                            todoViewModel.getTodoNumByDate()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "전 주"
                        )
                    }
                    
                    Spacer(Modifier.width(10.dp))
                    
                    // >> 버튼
                    IconButton(
                        onClick = {
                            todoViewModel.setNextWeek()
                            todoViewModel.getTodoNumByDate()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = "다음 주"
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                /** 날짜를 선택할 수 있는 Spinner */
                WeekCalendar(
                    value = selectedDay.value,
                    onValueChanged = {
                        selectedDay.value = it
                        todoViewModel.setSelectedDay(it)
                    },
                    items = days.value,
                    onSlideNext = {
                        todoViewModel.setPrevWeek()
                        todoViewModel.getTodoNumByDate()
                    },
                    onSlidePrev = {
                        todoViewModel.setNextWeek()
                        todoViewModel.getTodoNumByDate()
                    },
                    todoNumList = todoNumByDateList.value
                )
            }

            Spacer(Modifier.height(10.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                /** 날짜에 맞는 TodoList */
                var groupHeaderNum = 0
                items(todos.value){todo ->

                    //그룹 헤더를 보여주는 View
                    if (groupHeaderNum != todo.groupNum){
                        /** Api 확인해서 title을 가지고 todoGroup 정보를 가져올 수 있는지 확인 */
                        val todoGroup = TodoGroup(groupNum = 0, groupName = "test", isImportant = false)
                        TodoGroupHeader(
                            modifier = Modifier,
                            todoGroup = todoGroup
                        )
                        Spacer(Modifier.height(5.dp))
                        groupHeaderNum = todo.groupNum
                    }
                    //내부 아이템
                    TodoItem(
                        todo = todo,
                        onClick = {
                            navController.navigate("${Screens.DescriptionPage.name}/${todo.todoNum}")
                        },
                        onDeleteClick = {
                            showLoading = true
                            runBlocking{
                                todoViewModel.deleteTodo(todo.todoNum)
                                todoViewModel.getTodoNumByDate()
                                showLoading = false
                            }
                        }
                    )
                }
            }
        }
        //floating action button
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 20.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ){
            MenuFAB(
                insertTodo = {
                    navController.navigate(Screens.AddTodoPage.name)
                },
                insertTodoGroup = {
                    navController.navigate(Screens.TodoGroupPage.name)
                },
            )
        }
    }
    LaunchedEffect(errorState.value){
        if (errorState.value != null){
            showToast(context, "${errorState.value}\n다시 로그인 해주세요")
            todoViewModel.resultReset()
            navController.navigate(Screens.LoginPage.name)
        }
    }
    LaunchedEffect(selectedDay.value){
        val dateString = "${selectedDay.value.year}-${selectedDay.value.month}-${selectedDay.value.day}"
        todoViewModel.fetchTodos(dateString)
    }
}