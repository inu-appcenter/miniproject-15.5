package com.example.todolist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingDialog(
    onDismissRequest : () -> Unit,
    width : Dp,
    height : Dp,
){
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.LightGray, shape = RoundedCornerShape(size = 5.dp))
                .height(height)
                .width(width)
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Loading",
                fontSize = 14.sp
            )
            Spacer(Modifier.height(5.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Color.DarkGray,
            )
        }
    }
}