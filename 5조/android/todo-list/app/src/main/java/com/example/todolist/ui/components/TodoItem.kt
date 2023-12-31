package com.example.todolist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.Data.Todo

@Composable
fun TodoItem(
    modifier : Modifier = Modifier,
    todo : Todo,
    onClick : () -> Unit,
    onDeleteClick : () -> Unit,
){
    Row(
        modifier = modifier
            .padding(10.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ){
        //title
        Text(
            modifier = Modifier.weight(4f),
            text = todo.title,
            fontSize = 26.sp,
            color = Black,
            fontWeight = FontWeight(500)
        )

        Text(
            modifier = Modifier.weight(3f),
            text = "${todo.startDate} ~\n${todo.deadDate}",
            fontSize = 16.sp,
            color = Black,
            fontWeight = FontWeight(400)
        )

        Spacer(Modifier.width(10.dp))

        IconButton(
            modifier = Modifier.size(26.dp),
            onClick = {
                onDeleteClick()
            }
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.Delete,
                contentDescription = "delete button",
                tint = Red,
            )
        }
    }
}