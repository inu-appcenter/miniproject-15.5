package com.example.todolist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavController
import com.example.todolist.Screens
import com.example.todolist.ui.screens.drawerMenu.menus.MenuDrawer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    navController: NavController,
    doLogout : () -> Unit,
    drawerState : DrawerState,
    closeDrawer : () -> Unit,
    content : @Composable () -> Unit,
    screenWidth : Dp
){
    var selectedMenu by remember{ mutableStateOf(Icons.Filled.Home) }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Column(
                    modifier = Modifier.fillMaxHeight()
                        .width(screenWidth)
                        .background(color = Color.White)
                ){
                    MenuDrawer(
                        selectedMenu = selectedMenu,
                        onMenuChange = {
                            selectedMenu = it
                        },
                        closeDrawer = closeDrawer,
                        doLogout = doLogout
                    )
                }
            },
            content = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    content()
                }
            }
        )
        LaunchedEffect(selectedMenu){
            when(selectedMenu){
                Icons.Filled.Person -> navController.navigate(Screens.MyPage.name)
                Icons.Filled.Settings -> navController.navigate(Screens.SettingPage.name)
            }
        }
    }
}