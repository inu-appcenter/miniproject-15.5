package com.example.todolist.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todolist.Data.showToast
import com.example.todolist.Screens
import com.example.todolist.ui.theme.MainColor
import com.example.todolist.ui.theme.SubColor1
import com.example.todolist.viewModel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserPage(
    navController : NavController,
    todoViewModel : TodoViewModel = hiltViewModel()
){
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    //추가할 id
    var newId by remember{mutableStateOf("")}
    //추가할 passwd
    var newPasswd by remember{mutableStateOf("")}
    //추가할 email
    var newEmail by remember{mutableStateOf("")}
    //추가할 이름
    var newName by remember{mutableStateOf("")}
    //추가할 전화번호
    var newPhoneNum by remember{mutableStateOf("")}

    //중복 여부
    var isDuplicated by remember{mutableStateOf(true)}

    //변수 초기화
    val resetData = {
        newId = ""
        newPasswd = ""
        newEmail = ""
    }

    val errMsg = todoViewModel.errMsg.collectAsState()
    val okMsg = todoViewModel.okMsg.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(start = 20.dp, end = 20.dp, top = 10.dp),
    ){
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "회원가입",
            textAlign = TextAlign.Center,
            fontSize = 30.sp
        )

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "아이디",
                fontSize = 18.sp,
                fontWeight = FontWeight(600)
            )

            Spacer(Modifier.width(10.dp))

            BasicTextField(
                modifier = Modifier
                    .weight(2.5f),
                value = newId,
                onValueChange = {
                    newId = it
                },
                decorationBox = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        if (newId.isEmpty()){
                            Text(
                                text = "아이디를 입력해주세요",
                                fontSize = 18.sp,
                                color = LightGray,
                            )
                        }
                        it()
                    }
                },
                textStyle = TextStyle(
                    color = Black,
                    background = White,
                ),
                singleLine = true,
            )
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SubColor1,
                    contentColor = Red
                ),
                contentPadding = PaddingValues(horizontal = 0.dp),
                enabled = isDuplicated,
                onClick = {
                    /** 정아가 틀을 만들어야 가능 */
                    //아이디가 비어있지 않는다면
                    if (newId == ""){
                        showToast(context, "공백은 아이디로 사용할 수 없습니다.")
                    } else {
                        //중복 데이터가 없으면?
                        if(true){
                            isDuplicated = false
                            showToast(context,"사용 가능한 아이디 입니다.")
                        }
                        //중복 데이터가 있다면
                        else {
                            isDuplicated = true
                            showToast(context,"이미 있는 아이디 입니다.")
                        }
                    }
                }
            ) {
                Text(
                    text = "중복확인",
                    fontSize = 10.sp,
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "비밀번호",
                fontSize = 18.sp,
                fontWeight = FontWeight(600)
            )

            Spacer(Modifier.width(10.dp))

            BasicTextField(
                modifier = Modifier
                    .weight(3.5f),
                value = newPasswd,
                onValueChange = {
                    newPasswd = it
                },
                decorationBox = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        if (newPasswd.isEmpty()){
                            Text(
                                text = "비밀번호를 입력해주세요",
                                fontSize = 18.sp,
                                color = LightGray,
                            )
                        }
                        it()
                    }
                },
                textStyle = TextStyle(
                    color = Black,
                    background = White,
                ),
                singleLine = true,
            )
        }

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "이메일",
                fontSize = 18.sp,
                fontWeight = FontWeight(600)
            )

            Spacer(Modifier.width(10.dp))

            BasicTextField(
                modifier = Modifier
                    .weight(3.5f),
                value = newEmail,
                onValueChange = {
                    newEmail = it
                },
                decorationBox = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        if (newEmail.isEmpty()){
                            Text(
                                text = "이메일을 입력해주세요",
                                fontSize = 18.sp,
                                color = LightGray,
                            )
                        }
                        it()
                    }
                },
                textStyle = TextStyle(
                    color = Black,
                    background = White,
                ),
                singleLine = true,
            )
        }

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "이름",
                fontSize = 18.sp,
                fontWeight = FontWeight(600)
            )

            Spacer(Modifier.width(10.dp))

            BasicTextField(
                modifier = Modifier
                    .weight(3.5f),
                value = newName,
                onValueChange = {
                    newName = it
                },
                decorationBox = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        if (newName.isEmpty()){
                            Text(
                                text = "이름을 입력해주세요",
                                fontSize = 18.sp,
                                color = LightGray,
                            )
                        }
                        it()
                    }
                },
                textStyle = TextStyle(
                    color = Black,
                    background = White,
                ),
                singleLine = true,
            )
        }

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "전화번호",
                fontSize = 18.sp,
                fontWeight = FontWeight(600)
            )

            Spacer(Modifier.width(10.dp))

            BasicTextField(
                modifier = Modifier
                    .weight(3.5f),
                value = newPhoneNum,
                onValueChange = {
                    newPhoneNum = it
                },
                decorationBox = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        if (newPhoneNum.isEmpty()){
                            Text(
                                text = "전화번호를 입력해주세요",
                                fontSize = 18.sp,
                                color = LightGray,
                            )
                        }
                        it()
                    }
                },
                textStyle = TextStyle(
                    color = Black,
                    background = White,
                ),
                singleLine = true,
            )
        }

        Spacer(Modifier.height(60.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainColor
                ),
                onClick = {
                    resetData()
                    navController.navigate(Screens.LoginPage.name)
                }
            ) {
                Text(
                    text = "취소",
                    fontSize = 20.sp,
                    color = Black,
                )
            }

            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainColor
                ),
                onClick = {
                    //중복 검사 통과
                    if (!isDuplicated){
                        //비밀번호, 이메일이 비어있지 않도록
                        if (newPasswd == ""){
                            showToast(context, "공백은 비밀번호가 될 수 없습니다.")
                        } else if (newEmail == ""){
                            showToast(context, "공백은 이메일이 될 수 없습니다.")
                        } else {
                            todoViewModel.signUp(
                                id = newId,
                                passwd = newPasswd,
                                email = newEmail,
                                phoneNum = newPhoneNum,
                                name = newName
                            )
                        }
                    } else {
                        showToast(context, "중복 검사를 통과해주세요.")
                    }
                }
            ) {
                Text(
                    text = "추가",
                    fontSize = 20.sp,
                    color = Black,
                )
            }
        }

        Spacer(Modifier.weight(1f))

        LaunchedEffect(errMsg){
            if (errMsg.value != null){
                showToast(context, errMsg.value!!)
            }
        }
        LaunchedEffect(okMsg){
            if (okMsg.value != null){
                showToast(context, okMsg.value!!)
            }
        }
    }
}