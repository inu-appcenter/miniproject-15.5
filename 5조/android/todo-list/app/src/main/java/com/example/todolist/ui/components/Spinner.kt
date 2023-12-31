package com.example.todolist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.Data.DataClass.TodoGroupInTodo

@Composable
fun Spinner(
    modifier : Modifier = Modifier,
    value : TodoGroupInTodo,
    onValueChanged : (TodoGroupInTodo) -> Unit,
    items : List<TodoGroupInTodo>,
){
    //spinner 상태 변수
    var isExpanded by remember{mutableStateOf(false)}
    val onDropdownMenu = {
        isExpanded = !isExpanded
    }

    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .padding(horizontal = 5.dp)
            .clickable {
                onDropdownMenu()
            }
            .verticalScroll(scrollState),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){

    }
}