package com.example.todolist.Module

import com.example.todolist.Data.TodoDto.DelTodoRespDto
import com.example.todolist.Data.TodoDto.GetTodoGroupsRespDto
import com.example.todolist.Data.TodoDto.TodoGroupReqDto
import com.example.todolist.Data.TodoDto.TodoGroupRespDto
import com.example.todolist.Data.TodoDto.TodoGroupsRespDto
import com.example.todolist.Data.TodoDto.TodoReqDto
import com.example.todolist.Data.TodoDto.TodoRespDto
import com.example.todolist.Data.TodoDto.TodosRespDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApi {

    /**TODO 개별 API*/
    //Todo 생성
    @POST("/todos")
    fun addTodo(@Header("X-AUTH-TOKEN") token : String, @Body addTodoReqDto : TodoReqDto) : Call<TodoRespDto>

    //Todo 제목 검색
    @GET("/todos/{keyword}")
    fun searchTodoByTitle(@Header("X-AUTH-TOKEN") token : String, @Path("keyword") keyword : String)
    : Call<TodosRespDto>

    //Todo 수정
    @PUT("/todos/{todonum}")
    fun updateTodo(@Header("X-AUTH-TOKEN") token : String, @Path("todonum") todoNum : Int, @Body updateTodoReqDto : TodoReqDto) : Call<TodoRespDto>

    //Todo 삭제
    @DELETE("/todos/{todonum}")
    fun deleteTodo(@Header("X-AUTH-TOKEN") token : String, @Path("todonum") todoNum : Int) : Call<DelTodoRespDto>

    //Todo 그룹을 가지고 있지 않은 Todo 전체 조회
    @GET("/todos/todolist")
    fun getTodosNoGroup(@Header("X-AUTH-TOKEN") token : String) : Call<TodosRespDto>

    /**TODO 그룹 API*/
    //Todo 그룹 조회
    @GET("/todogroup/todogroups")
    fun getTodoGroup(@Header("X-AUTH-TOKEN") token : String) : Call<GetTodoGroupsRespDto>

    //Todo 그룹 생성
    @POST("/todogroup")
    fun addTodoGroup(@Header("X-AUTH-TOKEN") token : String, @Body todoGroupReqDto : TodoGroupReqDto) : Call<TodoGroupRespDto>

    //Todo 그룹 수정
    @PUT("/todogroup/{todogroupnum}")
    fun updateTodoGroup(@Header("X-AUTH-TOKEN") token : String, @Path("todogroupnum") todoGroupNum : Int, @Body todoGroupReqDto : TodoGroupReqDto) : Call<TodoGroupRespDto>

    //Todo 그룹 삭제 (내부에 속한 Todo들은 모두 그룹이 NULL로 변환)
    @DELETE("/todogroup/{todogroupnum}")
    fun deleteTodoGroup(@Header("X-AUTH-TOKEN") token : String, @Path("todogroupnum") todoGroupNum : Int) : Call<TodoGroupRespDto>

    //Todo 그룹을 가지고 있는 Todo 전체 조회
    @GET("/todogroup/todolist")
    fun getAllTodosInGroup(@Header("X-AUTH-TOKEN") token : String) : Call<TodoGroupsRespDto>

}