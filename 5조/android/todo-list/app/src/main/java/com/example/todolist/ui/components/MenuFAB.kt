package com.example.todolist.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MenuFAB(
    floatingIcon : ImageVector = Icons.Filled.Menu,
    menus : List<ImageVector> = listOf(
        Icons.Filled.AddCircle,
        Icons.Filled.Send,
        Icons.Filled.KeyboardArrowUp
    ),
    insertTodo : () -> Unit,
    insertTodoGroup : () -> Unit,
){
    var expanded by remember{mutableStateOf(false)}

    val onClicks : List<() -> Unit> = listOf(
        insertTodo,
        insertTodoGroup,
        {}
    )

    val scaleValues = remember{mutableStateListOf(Animatable(0f))}
    scaleValues.clear()
    repeat(menus.size){
        scaleValues.add(Animatable(0f))
    }

    Box(
        modifier = Modifier
            .height((40 * (menus.size + 1)).dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomEnd
    ){
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(40.dp)
        ){
            if (expanded){
                LazyColumn(
                    modifier = Modifier
                        .height((40 * menus.size).dp)
                        .width(40.dp),
                    horizontalAlignment = Alignment.End,
                    reverseLayout = true
                ){
                    items(menus.size){ index ->
                        val icon = menus[index]
                        val scaleModifier = Modifier.scale(scaleValues[index].value)

                        IconButton(
                            modifier = Modifier.size(40.dp) then scaleModifier,
                            onClick = {
                                onClicks[index]()
                            }
                        ) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = icon,
                                contentDescription = "Sub Menu"
                            )
                        }
                    }
                }
            } else {
                Spacer(Modifier.height(120.dp))
            }
            IconButton(
                modifier = Modifier.size(40.dp),
                onClick = {
                    expanded = !expanded
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = floatingIcon,
                    contentDescription = "Menu Icon"
                )
            }
        }
        LaunchedEffect(expanded){
            if (expanded){
                menus.forEachIndexed{ index, _ ->
                    scaleValues[index].animateTo(
                        targetValue = 1f,
                        animationSpec = tween(
                            durationMillis = 100,
                            delayMillis = index * 100
                        )
                    )
                }
            } else {
                menus.forEachIndexed{ index, _ ->
                    scaleValues[index].animateTo(
                        targetValue = 0f,
                        animationSpec = tween(
                            durationMillis = 100,
                            delayMillis = index * 100
                        )
                    )
                }
            }
        }
    }
}