package com.example.todolist.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.Data.Calendar.MyDate
import kotlin.math.abs

@Composable
fun WeekCalendar(
    modifier : Modifier = Modifier,
    value : MyDate,
    onValueChanged : (MyDate) -> Unit,
    items : List<MyDate>,
    onSlideNext : () -> Unit,
    onSlidePrev : () -> Unit,
    todoNumList : List<Int>
){
    //day list
    val dayList = listOf("일","월","화","수","목","금","토")

    //offset
    var offsetX by remember{mutableStateOf(0f)}
    //방향
    var direction by remember{mutableStateOf(-1)}

    Row(
        modifier = modifier
            .pointerInput(Unit){
                detectDragGestures (
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val (x,y) = dragAmount
                        if (abs(x) > abs(y)) {
                            when{
                                //right
                                x > 0 -> {
                                    offsetX += x
                                    direction = 0
                                }
                                //left
                                x < 0 -> {
                                    offsetX += x
                                    direction = 1
                                }
                            }
                        }
                    },
                    onDragEnd = {
                        when(direction){
                            0 -> {
                                if(offsetX > 300){
                                    // right 이벤트
                                    onSlideNext()
                                }
                                offsetX = 0f
                            }
                            1 -> {
                                if (offsetX < -300){
                                    onSlidePrev()
                                }
                                offsetX = 0f
                            }
                        }
                    }
                )
            }
    ){
        for (i in 0..6){

            //선택 확인
            val isSelectedDay = value == items[i]

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onValueChanged(items[i])
                    }
            ){

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = dayList[i],
                    fontSize = 20.sp,
                    color = if (dayList[i] == "일") Red else if (dayList[i] == "토") Blue else Black,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(5.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)
                        .aspectRatio(1f),
                ){
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ){
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "${todoNumList[i]}",
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight(600)
                        )
                    }
                }

                Spacer(Modifier.height(5.dp))

                Text(
                    modifier = Modifier.fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = if(isSelectedDay) Black else Color.Unspecified,
                            shape = CircleShape
                        ),
                    text = "${items[i].day}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(500),
                    color = Black,
                    textAlign = TextAlign.Center
                )
            }

            if (i != 6){
                Spacer(Modifier.width(5.dp))
            }
        }
    }
}