package com.example.study_choi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItemTopAppBar() {
    Column {
        androidx.compose.material3.TopAppBar (
            title = { Text("Todo List") },
            navigationIcon = {
                IconButton(onClick = {  }) {
                    Icon(Icons.Filled.ArrowBack, "Menu")
                }
            },
            actions = {
                IconButton(onClick = {  }) {
                    Icon(Icons.Filled.MoreVert, "todo")
                }
            }
        )
    }
}

@Composable
fun TodoItem(navController: NavHostController) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TodoItemTopAppBar()

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "To do title", fontSize = 20.sp)

        Row(horizontalArrangement = Arrangement.End) {
            Text(text = "end date", fontSize = 10.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "To do description", fontSize = 16.sp)
    }
}

