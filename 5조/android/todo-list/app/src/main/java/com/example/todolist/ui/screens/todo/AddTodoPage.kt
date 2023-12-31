package com.example.todolist.ui.screens.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todolist.Data.DataClass.TodoGroupInTodo
import com.example.todolist.Data.showToast
import com.example.todolist.Screens
import com.example.todolist.ui.components.CustomDatePicker
import com.example.todolist.ui.components.TimePickerDialog
import com.example.todolist.ui.components.TopBar
import com.example.todolist.viewModel.TodoViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

@Composable
fun AddTodoPage(
    navController: NavController,
    todoViewModel : TodoViewModel = hiltViewModel()
){
    val TAG = "AddTodoPage"

    var content by remember{mutableStateOf("")}
    var title by remember{mutableStateOf("")}
    var loc by remember{mutableStateOf("")}

    val today = todoViewModel.getToday()

    var startDate by remember{mutableStateOf(today[0])}
    var endDate by remember{mutableStateOf(today[0])}

    val hours = mutableListOf(-1,-1,-1,-1,-1,-1)
    hours.addAll((0..12).toList())
    hours.addAll(listOf(-1,-1,-1,-1,-1,-1))
    val minutes = mutableListOf(-1,-1,-1,-1,-1,-1)
    minutes.addAll((0..55 step(5)).toList())
    minutes.addAll(listOf(-1,-1,-1,-1,-1,-1))

    var selectedStartHour by remember{
        var value = mutableStateOf(todoViewModel.calendar.getToday()[1].toInt())
        if (value.value > 12) {
            value.value -= 12
        }
        value
    }
    var selectedStartMinutes by remember{
        val tmp = mutableStateOf(todoViewModel.calendar.getToday()[2].toInt())
        tmp.value = tmp.value - (tmp.value % 5)
        tmp
    }
    var selectedStartAmPm by remember{mutableStateOf(if(todoViewModel.calendar.getToday()[1].toInt() > 12) "오후" else "오전")}

    var selectedEndHour by remember{
        var value = mutableStateOf(todoViewModel.calendar.getToday()[1].toInt())
        if (value.value > 12) {
            value.value -= 12
        }
        value
    }
    var selectedEndMinutes by remember{
        val tmp = mutableStateOf(todoViewModel.calendar.getToday()[2].toInt())
        tmp.value = tmp.value - (tmp.value % 5)
        tmp
    }
    var selectedEndAmPm by remember{mutableStateOf(if(todoViewModel.calendar.getToday()[1].toInt() > 12) "오후" else "오전")}

    val scrollState = rememberScrollState()

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    var isShowTimePicker by remember{mutableStateOf(false)}
    var type by remember{mutableStateOf("")}

    val format = "yyyy년 MM월 dd일"
    val localeKorea = Locale("ko", "KR")
    val dateFormat = SimpleDateFormat(format, localeKorea)

    val todoGroups = todoViewModel.todoGroups.collectAsState()

    var selectedGroup = remember{
        val group = mutableStateOf(
            if (todoGroups.value.isEmpty()){
                TodoGroupInTodo(-1, "No Group", false, listOf())
            } else {
                todoGroups.value[0]
            }
        )
        group
    }

    var isExpanded by remember{mutableStateOf(false)}
    val onDropdownMenu = {
        isExpanded = !isExpanded
    }

    val errMsg = todoViewModel.errMsg.collectAsState()
    val okMsg = todoViewModel.okMsg.collectAsState()

    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if (isShowTimePicker){
            TimePickerDialog(
                modifier = Modifier
                    .width((screenWidth * 4) / 5)
                    .height(screenHeight / 2)
                    .background(color = Color.White, shape = RoundedCornerShape(25.dp)),
                onDismissRequest = {
                    isShowTimePicker = false
                },
                onUpdateDate = {
                    if (type == "start"){
                        selectedStartHour = it.first
                        selectedStartMinutes = it.second
                        selectedStartAmPm = it.third
                    } else if (type == "end"){
                        selectedEndHour = it.first
                        selectedEndMinutes = it.second
                        selectedEndAmPm = it.third
                    }
                },
                selectedHour = if(type == "start") selectedStartHour else selectedEndHour,
                selectedMinute = if(type == "start") selectedStartMinutes else selectedEndMinutes,
                am_pm = if(type == "start") selectedStartAmPm else selectedEndAmPm,
                hours = hours,
                minutes = minutes,
                onHourChanged = {
                    if (type == "start"){
                        selectedStartHour = it
                    } else {
                        selectedEndHour = it
                    }
                },
                onMinuteChanged = {
                    if (type == "start"){
                        selectedStartMinutes = it
                    } else {
                        selectedEndMinutes = it
                    }
                },
                onAmPmChanged = {
                    if (type == "start"){
                        selectedStartAmPm = if(selectedStartAmPm == "오전") "오후" else "오전"
                    } else {
                        selectedEndAmPm = if(selectedEndAmPm == "오전") "오후" else "오전"
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ){
            //TopBar
            TopBar(
                title = "Todo 등록하기",
                navIcon = Icons.Filled.KeyboardArrowLeft,
                actionIcon = null,
                onNavClick = {
                    navController.navigateUp()
                },
                onActionClick = {}
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(horizontal = 10.dp, vertical = 15.dp)
            ) {

                Spacer(Modifier.height(15.dp))

                //todo title 입력
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                ){
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Icon(
                            imageVector = Icons.Filled.Create,
                            contentDescription = "Title"
                        )
                    }
                    Column(
                        modifier = Modifier.weight(5f)
                    ){
                        OutlinedTextField(
                            value = title,
                            onValueChange = {
                                title = it
                            },
                            placeholder = {
                                Text(
                                    text = "제목을 입력해주세요",
                                    color = Color.LightGray
                                )
                            },
                            maxLines = 1,
                            singleLine = true
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                //todo group 받기
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                ){
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "Title"
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(5f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ){
                        if (todoGroups.value.isEmpty()) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "그룹 없음",
                                fontSize = 20.sp,
                                color = Black,
                                textAlign = TextAlign.Center
                            )
                        } else {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onDropdownMenu()
                                    },
                                text = selectedGroup.value.title,
                                fontSize = 18.sp,
                                color = Black,
                                textAlign = TextAlign.Center
                            )

                            DropdownMenu(
                                modifier = Modifier.width(((screenWidth-20.dp) / 6) * 5),
                                expanded = isExpanded,
                                onDismissRequest = onDropdownMenu,
                            ) {
                                todoGroups.value.forEach{
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                selectedGroup.value = it
                                                onDropdownMenu()
                                            },
                                        text = it.title,
                                        fontSize = 20.sp,
                                        color = Black,
                                        textAlign = TextAlign.Center
                                    )

                                    if (it != todoGroups.value[todoGroups.value.lastIndex]){
                                        Divider(modifier = Modifier.fillMaxWidth(), thickness = 0.5.dp, color = Color.LightGray)
                                    }
                                }
                            }
                        }

                    }
                }

                Spacer(Modifier.height(10.dp))

                //todo description 입력
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenWidth / 3)
                ){
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Content"
                        )
                    }
                    Column(
                        modifier = Modifier.weight(5f)
                    ){
                        OutlinedTextField(
                            modifier = Modifier.fillMaxHeight(),
                            value = content,
                            onValueChange = {
                                content = it
                            },
                            placeholder = {
                                Text(
                                    text = "할 일을 입력해주세요",
                                    color = Color.LightGray
                                )
                            }
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                //todo location 입력
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                ){
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Title"
                        )
                    }
                    Column(
                        modifier = Modifier.weight(5f)
                    ){
                        OutlinedTextField(
                            value = loc,
                            onValueChange = {
                                loc = it
                            },
                            placeholder = {
                                Text(
                                    text = "위치를 입력해주세요",
                                    color = Color.LightGray
                                )
                            },
                            maxLines = 1,
                            singleLine = true
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                //todo startDate와 endDate 입력
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                ){
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Title"
                        )
                    }
                    Row(
                        modifier = Modifier.weight(5f)
                    ){
                        //startDate 입력
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center
                        ){
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "시작 시간",
                                fontSize = 15.sp,
                                color = Black,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(Modifier.height(15.dp))
                            
                            //날짜 선택
                            CustomDatePicker(
                                modifier = Modifier,
                                date = startDate, //viewmodel에서 오늘 날짜를 기본으로 설정
                                onDateChanged = {
                                    startDate = dateFormat.format(it)
                                }
                            )

                            Spacer(Modifier.height(10.dp))

                            //시간 선택
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        type = "start"
                                        isShowTimePicker = true
                                    },
                                text = "${selectedStartAmPm} ${selectedStartHour}시 ${selectedStartMinutes}분",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        //endDate 입력
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center
                        ){
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "종료 시간",
                                fontSize = 15.sp,
                                color = Black,
                                textAlign = TextAlign.Center
                            )

                            Spacer(Modifier.height(15.dp))
                            
                            //날짜 선택
                            CustomDatePicker(
                                modifier = Modifier,
                                date = endDate, //viewmodel에서 오늘 날짜를 기본으로 설정
                                onDateChanged = {
                                    val tmp = dateFormat.format(it)
                                    val tmpStart = LocalDate.of(startDate.substring(0,4).toInt(),startDate.substring(6,8).toInt(),startDate.substring(10,12).toInt())
                                    val tmpEnd = LocalDate.of(tmp.substring(0,4).toInt(),tmp.substring(6,8).toInt(),tmp.substring(10,12).toInt())
                                    endDate = if (tmpStart.isBefore(tmpEnd)){
                                        tmp
                                    } else {
                                        startDate
                                    }
                                }
                            )

                            Spacer(Modifier.height(10.dp))

                            //시간 선택
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        type = "end"
                                        isShowTimePicker = true
                                    },
                                text = "${selectedEndAmPm} ${selectedEndHour}시 ${selectedEndMinutes}분",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 40.dp),
                    horizontalArrangement = Arrangement.Center
                ){
                    Button(
                        onClick = {
                            //추가
                            todoViewModel.addTodo(
                                startDate = startDate,
                                endDate = endDate,
                                description = content,
                                location = loc,
                                title = title,
                                todoGroup = selectedGroup.value
                            )

                            //Home으로 이동
                            navController.navigate(Screens.HomePage.name)
                        }
                    ){
                        Text(
                            modifier = Modifier,
                            text = "추가",
                            fontSize = 26.sp
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(errMsg.value, okMsg.value){
        if (errMsg.value != null) {
            showToast(context, errMsg.value!!)
        } else if (okMsg.value != null) {
            showToast(context, okMsg.value!!)
        }
        todoViewModel.resultReset()
    }
}