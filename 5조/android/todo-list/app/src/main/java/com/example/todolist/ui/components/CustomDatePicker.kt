package com.example.todolist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    modifier : Modifier = Modifier,
    date : String,
    onDateChanged : (Long) -> Unit = {}
){
    val format = "yyyy년 MM월 dd일"
    var isClicked by remember{mutableStateOf(false)}
    val formatter = DateTimeFormatter.ofPattern(format)
    val initTimeStr = LocalDate.parse(date, formatter)
    val initTime = initTimeStr.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    val datePickerState = rememberDatePickerState(initTime)

    if(isClicked){
        DatePickerDialog(
            onDismissRequest = {
                isClicked = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        isClicked = false
                        onDateChanged(datePickerState.selectedDateMillis ?: 0)
                    }
                ) {
                    Text(
                        text = "확인"
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        isClicked = false
                    }
                ){
                    Text(
                        text = "취소"
                    )
                }
            },
            content = {
                DatePicker(state = datePickerState)
            }
        )
    }

    Row(
        modifier = modifier
            .clickable{
                isClicked = true
            }
    ){
        Text(
            text = date
        )
    }
}

@Preview
@Composable()
fun TestDatePicker(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        CustomDatePicker(
            date = "2023년 11월 29일"
        )
    }
}