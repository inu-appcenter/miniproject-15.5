package com.example.todolist.Module

import com.example.todolist.Data.LoginDto.LoginRequestDto
import com.example.todolist.Data.LoginDto.SignUpReqDto
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val userApi : UserApi
) {
    val TAG = "UserRepository"

    //회원가입
    fun signUp(signUpReqDto: SignUpReqDto) : String {
        try{
            val response = userApi.addUser(signUpReqDto).execute()
            
            return if(response.isSuccessful) {
                val result = response.body() ?: throw NullPointerException("응답 데이터가 없습니다.")
                when(response.code()){
                    200 -> result.message
                    201 -> "회원가입 성공"
                    400 -> "유효성 검사 실패"
                    409 -> "아이디가 중복되었습니다"
                    else -> throw IllegalArgumentException("${response.code()} : 오류")
                }
            } else {
                throw Exception("서버 오류 : ${response.code()}")
            }
        } catch(e : Exception){
            throw IllegalArgumentException("Error : ${e.message}")
        }
    }

    //로그인
    fun login(loginRequestDto: LoginRequestDto) : String {
        try {
            val response = userApi.login(loginRequestDto).execute()

            val result = response.body()
            return if (result == null){
                "데이터가 없습니다."
            } else {
                if (response.isSuccessful) {
                    when (response.code()) {
                        200 -> result.data
                        else -> "로그인 실패"
                    }
                } else {
                    "로그인 실패"
                }
            }
        } catch(e : Exception){
            throw IllegalArgumentException("서버 오류 : ${e.message}")
        }
    }
}