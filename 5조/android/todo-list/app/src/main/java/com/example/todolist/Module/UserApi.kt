package com.example.todolist.Module

import com.example.todolist.Data.LoginDto.CommonUserRespDto
import com.example.todolist.Data.LoginDto.GetAllRespDto
import com.example.todolist.Data.LoginDto.LoginRequestDto
import com.example.todolist.Data.LoginDto.SignUpReqDto
import com.example.todolist.Data.LoginDto.UpdateUserReqDto
import com.example.todolist.Data.SignUpRespDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {

    //유저 전체 조회
    @GET("/clients")
    fun getAllUsers() : Call<GetAllRespDto>

    //유저 회원 정보 수정
    @PUT("/clients")
    fun updateUser(@Header("X-AUTH-TOKEN") token : String, @Body signUpReqDto : UpdateUserReqDto) : Call<SignUpRespDto>

    //회원 가입
    @POST("/clients/sign-up")
    fun addUser(@Body requestClientDto : SignUpReqDto) : Call<SignUpRespDto>

    //회원 탈퇴
    @DELETE("/clients")
    fun deleteUser(@Header("X-AUTH-TOKEN") token : String) : Call<CommonUserRespDto>

    //로그인
    @POST("/clients/sign-in")
    fun login(@Body requestDto : LoginRequestDto) : Call<CommonUserRespDto>
}