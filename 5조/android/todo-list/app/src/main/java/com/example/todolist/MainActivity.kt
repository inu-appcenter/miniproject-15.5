package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.Data.saveAuthToken
import com.example.todolist.ui.screens.drawerMenu.MyPage
import com.example.todolist.ui.screens.drawerMenu.SettingPage
import com.example.todolist.ui.screens.login.AddUserPage
import com.example.todolist.ui.screens.login.FindIdPage
import com.example.todolist.ui.screens.login.FindPasswdPage
import com.example.todolist.ui.screens.todo.AddTodoPage
import com.example.todolist.ui.screens.todo.DescriptionPage
import com.example.todolist.ui.screens.todo.HomePage
import com.example.todolist.ui.screens.todo.TodoGroupPage
import com.example.todolist.ui.theme.TodoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screens.LoginPage.name
                    ){
                        composable(Screens.AddUserPage.name){
                            AddUserPage(navController)
                        }
                        composable(Screens.FindIdPage.name){
                            FindIdPage(navController)
                        }
                        composable(Screens.FindPasswdPage.name){
                            FindPasswdPage(navController)
                        }
                        composable(Screens.LoginPage.name){
                            LoginPage(navController)
                        }
                        composable(Screens.HomePage.name){
                            val parentEntry = remember(it){
                                navController.getBackStackEntry(Screens.LoginPage.name)
                            }
                            HomePage(
                                navController = navController,
                                todoViewModel = hiltViewModel(parentEntry)
                            )
                        }
                        composable(Screens.SettingPage.name){
                            val parentEntry = remember(it){
                                navController.getBackStackEntry(Screens.LoginPage.name)
                            }
                            SettingPage(
                                navController = navController,
                                todoViewModel = hiltViewModel(parentEntry)
                            )
                        }
                        composable(Screens.MyPage.name){
                            val parentEntry = remember(it){
                                navController.getBackStackEntry(Screens.LoginPage.name)
                            }
                            MyPage(
                                navController = navController,
                                todoViewModel = hiltViewModel(parentEntry)
                            )
                        }
                        composable(Screens.AddTodoPage.name){
                            val parentEntry = remember(it){
                                navController.getBackStackEntry(Screens.LoginPage.name)
                            }
                            AddTodoPage(
                                navController = navController,
                                todoViewModel = hiltViewModel(parentEntry)
                            )
                        }
                        composable("${Screens.DescriptionPage.name}/{id}"){
                            val parentEntry = remember(it){
                                navController.getBackStackEntry(Screens.LoginPage.name)
                            }
                            DescriptionPage(
                                navController = navController,
                                todoViewModel = hiltViewModel(parentEntry),
                                id = it.arguments?.getString("id") ?: throw NullPointerException("Id is NULL")
                            )
                        }
                        composable(Screens.TodoGroupPage.name){
                            val parentEntry = remember(it){
                                navController.getBackStackEntry(Screens.LoginPage.name)
                            }
                            TodoGroupPage(
                                navController = navController,
                                todoViewModel = hiltViewModel(parentEntry)
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        saveAuthToken(applicationContext, null)
    }
}