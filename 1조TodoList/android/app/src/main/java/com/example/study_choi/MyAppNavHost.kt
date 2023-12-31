package com.example.study_choi

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyAppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AllUI.LogIn.name
    ) {
        composable(AllUI.LogIn.name) {
             LogIn(
                navController = navController
             )
        }

        composable(AllUI.Home.name) {
            Home(
                navController = navController
            )
        }

        composable(AllUI.SignUp.name) {
            SignUp(
                navController = navController
            )
        }

        composable(AllUI.TodoItem.name) {
            TodoItem(
                navController = navController
            )
        }

        composable(AllUI.AddTodoItem.name) {
            AddTodoItem(

            )
        }
    }
}


