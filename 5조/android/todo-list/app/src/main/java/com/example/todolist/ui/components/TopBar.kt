package com.example.todolist.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.ui.theme.TodoListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title : String,
    navIcon : ImageVector?,
    navDes : String = "Navigation",
    navSize : Dp = 30.dp,
    onNavClick : () -> Unit = {},
    actionIcon : ImageVector?,
    actionDes : String = "Action",
    actionSize : Dp = 30.dp,
    onActionClick : () -> Unit = {},
){
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                fontSize = 30.sp,
                fontWeight = FontWeight(600),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            if (navIcon != null){
                IconButton(
                    modifier = Modifier.size(navSize),
                    onClick = onNavClick
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = navIcon,
                        contentDescription =  navDes
                    )
                }
            }
            else {
                Spacer(Modifier.size(navSize))
            }
        },
        actions = {
            if (actionIcon != null){
                IconButton(
                    modifier = Modifier.size(actionSize),
                    onClick = onActionClick
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = actionIcon,
                        contentDescription = actionDes
                    )
                }
            }
            else {
                Spacer(Modifier.size(actionSize))
            }
        }
    )
}

@Preview
@Composable
fun TestTopAppBar(){
    TodoListTheme {
        Column(
            modifier = Modifier.fillMaxWidth()
        ){
            TopBar(
                title = "title",
                navIcon = Icons.Filled.Home,
                navDes = "Home",
                navSize = 26.dp,
                onNavClick = {},
                actionIcon = Icons.Filled.Menu,
                actionDes = "Menu",
                actionSize = 26.dp,
                onActionClick = {}
            )
        }
    }
}