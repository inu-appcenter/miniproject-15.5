package com.example.todolist.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimePickerDialog(
    modifier : Modifier,
    onDismissRequest : () -> Unit,
    onUpdateDate : (Triple<Int, Int, String>) -> Unit,
    selectedHour : Int,
    selectedMinute : Int,
    am_pm : String,
    am_pms : List<String> = listOf("오전","오후"),
    hours : List<Int>,
    minutes : List<Int>,
    onHourChanged : (Int) -> Unit,
    onMinuteChanged : (Int) -> Unit,
    onAmPmChanged : () -> Unit,
){
    val TAG = "TimePickerDialog"

    val amPmListState = rememberLazyListState()
    val hourListState = rememberLazyListState()
    val minuteListState = rememberLazyListState()

    Dialog(
        onDismissRequest = onDismissRequest
    ){
        Column(
            modifier = modifier
                .padding(horizontal = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){

                //오전 오후
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ){
                    Spacer(Modifier.height(15.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                            .clickable {
                                onAmPmChanged()
                            },
                        text = am_pm,
                        textAlign = TextAlign.Center
                    )
                }

                //시간 선택
                LazyColumn(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    state = hourListState
                ){
                    items(hours){
                        if (it != -1){
                            val isSelected = selectedHour == it
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(25.dp)
                                    .border(
                                        width = 0.5.dp,
                                        color = if (isSelected) LightGray else Unspecified,
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .background(
                                        color = if (isSelected) LightGray else Unspecified,
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .clickable {
                                        onHourChanged(it)
                                    },
                                text = "${it}",
                                textAlign = TextAlign.Center,
                            )
                        }
                        else {
                            Spacer(Modifier.height(25.dp))
                        }
                    }
                }

                //분 선택
                LazyColumn(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    state = minuteListState
                ){
                    items(minutes){
                        if (it != -1){
                            val isSelected = selectedMinute == it
                            Text(
                                modifier = Modifier
                                    .height(25.dp)
                                    .fillMaxWidth()
                                    .border(
                                        width = 0.5.dp,
                                        color = if (isSelected) LightGray else Unspecified,
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .background(
                                        color = if (isSelected) LightGray else Unspecified,
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .clickable {
                                        onMinuteChanged(it)
                                    },
                                text = "${it}",
                                textAlign = TextAlign.Center
                            )
                        }
                        else {
                            Spacer(Modifier.height(25.dp))
                        }
                    }
                }
            }
            Spacer(Modifier.weight(1f))

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
            Row(
                modifier = Modifier.height(40.dp)
            ){
                Text(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(5.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            //취소 이벤트
                            onDismissRequest()
                        },
                    text = "취소",
                    textAlign = TextAlign.Center
                )

                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )

                Text(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(5.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            //확인 이벤트
                            onUpdateDate(Triple(selectedHour, selectedMinute, am_pm))
                            onDismissRequest()
                        },
                    text = "확인",
                    textAlign = TextAlign.Center
                )
            }
        }
        LaunchedEffect(selectedHour){
            val itemHeight = 30.dp.value
            val totalHour = hourListState.layoutInfo.viewportSize.height - itemHeight
            val adjustedHour = hours.indexOf(selectedHour)
            if (adjustedHour != -1){
                hourListState.scrollToItem(
                    index = adjustedHour,
                    scrollOffset = -(totalHour/2).toInt()
                )
            }
        }
        LaunchedEffect(selectedMinute){
            val itemHeight = 30.dp.value
            val totalMinute = minuteListState.layoutInfo.viewportSize.height - itemHeight
            val adjustedMinute = minutes.indexOf(selectedMinute)
            if (adjustedMinute != -1){
                minuteListState.scrollToItem(
                    index = adjustedMinute,
                    scrollOffset = -(totalMinute/2).toInt()
                )
            }
        }
        LaunchedEffect(am_pm){
            amPmListState.scrollToItem(am_pms.indexOf(am_pm))
        }
    }
}