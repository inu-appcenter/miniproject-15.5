package com.example.todolist.ui.screens.drawerMenu.menus

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuDrawer(
    selectedMenu : ImageVector = Icons.Filled.Home,
    onMenuChange : (ImageVector) -> Unit,
    closeDrawer : () -> Unit,
    doLogout : () -> Unit,
){

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color = Color.Transparent, shape = CircleShape),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile Icons"
                )
            }
            Spacer(Modifier.height(25.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "환영합니다.",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
            )
            NavigationDrawerItem(
                label = { Text(text = "My Page") },
                selected = selectedMenu == Icons.Filled.Person,
                onClick = {
                    onMenuChange(Icons.Filled.Person)
                    closeDrawer()
                },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "My Page"
                    )
                }
            )
            NavigationDrawerItem(
                label = { Text(text = "Settings") },
                selected = selectedMenu == Icons.Filled.Settings,
                onClick = {
                    onMenuChange(Icons.Filled.Settings)
                    closeDrawer()
                },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings"
                    )
                }
            )
            Spacer(Modifier.height(20.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        doLogout()
                    },
                text = "로그아웃",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
            )
        }
    }
}