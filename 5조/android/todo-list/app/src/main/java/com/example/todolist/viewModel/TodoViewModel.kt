package com.example.todolist.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.Data.Calendar.MyDate
import com.example.todolist.Data.DataClass.TodoGroupInTodo
import com.example.todolist.Data.LoginDto.LoginRequestDto
import com.example.todolist.Data.LoginDto.SignUpReqDto
import com.example.todolist.Data.Todo
import com.example.todolist.Data.TodoDto.TodoReqDto
import com.example.todolist.Data.readAuthToken
import com.example.todolist.Data.saveAuthToken
import com.example.todolist.Module.MyCalendar
import com.example.todolist.Module.TodoRepository
import com.example.todolist.Module.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository : UserRepository,
    private val todoRepository: TodoRepository,
    @ApplicationContext private val context : Context
) : ViewModel() {
    val TAG = "TodoViewModel"

    //달력 모듈
    val calendar = MyCalendar()

    //dayList
    private val _days = MutableStateFlow(listOf<MyDate>())
    val days : StateFlow<List<MyDate>>
        get() = _days

    //이번 주 포함 투두 개수 리스트
    private val _todosByDateList = MutableStateFlow(listOf<Int>())
    val todosByDateList : StateFlow<List<Int>>
        get() = _todosByDateList

    //error msg 관리
    private val _errMsg = MutableStateFlow<String?>(null)
    val errMsg : StateFlow<String?>
        get() = _errMsg

    //error msg 관리
    private val _okMsg = MutableStateFlow<String?>(null)
    val okMsg : StateFlow<String?>
        get() = _okMsg

    //현재 유저의 Todo List
    private val _todos = MutableStateFlow(mutableListOf<Todo>())
    val todos : StateFlow<List<Todo>>
        get() = _todos

    //날짜로 관리되는 Todo List
    private val _todosByDate = MutableStateFlow(mutableListOf<Todo>())
    val todosByDate : StateFlow<List<Todo>>
        get() = _todosByDate

    //현재 유저의 Todo Group List
    private val _todoGroups = MutableStateFlow(listOf<TodoGroupInTodo>())
    val todoGroups : StateFlow<List<TodoGroupInTodo>>
        get() = _todoGroups

    fun signUp(
        id : String,
        passwd : String,
        email : String,
        name : String,
        phoneNum : String,
    ) {
        runBlocking{
            viewModelScope.launch(Dispatchers.IO){
                val newReqDto = SignUpReqDto(
                    id = id,
                    passwd = passwd,
                    clientEmail = email,
                    name = name,
                    phoneNum = phoneNum
                )
                val result = repository.signUp(newReqDto)
                
                if (result != "회원가입 성공" && result != "OK"){
                    _errMsg.tryEmit(result)
                } else {
                    _okMsg.tryEmit(result)
                }
            }
        }
    }

    //로그아웃
    fun logout(){
        saveAuthToken(context, null)
    }

    fun getToday() = calendar.getToday()

    //로그인 테스트
    suspend fun login(userId : String, userPasswd : String) : String =
        viewModelScope.async(Dispatchers.IO){
            val reqDto = LoginRequestDto(
                clientId = userId,
                clientPassword = userPasswd
            )
            val result = repository.login(reqDto)

            if (result == "로그인 실패"){
                _errMsg.tryEmit("로그인 실패")
            } else {
                saveAuthToken(context, result)
                _todoGroups.tryEmit(todoRepository.getGroups(result))
            }

            val tmpList = arrayListOf<Todo>()
            todoGroups.value.forEach{
                tmpList.addAll(it.todoList)
            }

            _todos.tryEmit(tmpList)


            val tmp = mutableListOf<MyDate>()
            calendar.dayList.forEach{
                tmp.add(it)
            }
            _days.tryEmit(tmp)

            //초기 todos
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val todayFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
            val stdToday = todayFormat.parse(getToday()[0]) ?: throw NullPointerException("Today is NULL on ${TAG} in fetchTodos()")

            val tmp_list = mutableListOf<Todo>()
            todos.value.forEach{
                val deadDate = dateFormat.parse(it.deadDate) ?: throw NullPointerException("Today is NULL on ${TAG} in fetchTodos()")
                val startDate = dateFormat.parse(it.startDate) ?: throw NullPointerException("Today is NULL on ${TAG} in fetchTodos()")
                if (stdToday in startDate..deadDate){
                    tmp_list.add(it)
                }
            }
            _todosByDate.tryEmit(tmp_list)
            return@async "Done"
        }.await()


    //id를 통해서 Todo 객체 가져오기
    fun getTodoById(id : String) : Todo {

        var result : Todo? = null

        todos.value.forEach{
            if (id.toInt() == it.todoNum){
                result = it
                return@forEach
            }
        }
        return result ?: throw NullPointerException("Error : Todo is NULL in getTodo() / ${TAG}")
    }

    //다음 주로 이동
    fun setNextWeek(){
        calendar.setNextWeek()

        val tmp = mutableListOf<MyDate>()
        calendar.dayList.forEach{
            tmp.add(it)
        }

        _days.tryEmit(tmp)
    }

    //전 주로 이동
    fun setPrevWeek(){
        calendar.setPrevWeek()

        val tmp = mutableListOf<MyDate>()
        calendar.dayList.forEach{
            tmp.add(it)
        }

        _days.tryEmit(tmp)
    }

    //selectedDate를 반환
    fun getSelectedDay() : MyDate{
        return calendar.selectedMyDate
    }

    //오늘을 포함하는 이번 주의 todo 개수
    fun getTodoNumByDate(){
        val todoByDateList = mutableListOf<Int>()
        calendar.dayList.forEach{ date ->
            var cnt = 0
            todos.value.forEach{ todo ->
                val formatDate = (date.year).toString() + "-" + (date.month).toString() + "-" + (date.day).toString()
                val newStartDate = todo.startDate
                val newEndDate = todo.deadDate

                if (formatDate in newStartDate..newEndDate){
                    cnt += 1
                }
            }
            todoByDateList.add(cnt)
        }
        _todosByDateList.tryEmit(todoByDateList)
    }

    //selectedDate를 설정
    fun setSelectedDay(date : MyDate){
        calendar.setDate(date)
    }

    //errMsg 초기화
    fun resultReset(){
        _errMsg.value = null
        _okMsg.value = null
    }

    //todo추가
    fun addTodo(
        startDate : String,
        endDate : String,
        description : String,
        title : String,
        location : String,
        todoGroup : TodoGroupInTodo
    ){
        val newStartDate = startDate.substring(0,4) + "-" + startDate.substring(6,8) + "-" + startDate.substring(10,12)
        val newEndDate = endDate.substring(0,4) + "-" + endDate.substring(6,8) + "-" + endDate.substring(10,12)
        runBlocking{
            viewModelScope.launch(Dispatchers.IO){
                val newReq = TodoReqDto(
                    endDate = newEndDate,
                    isFinished = false,
                    startDate = newStartDate,
                    desc = description,
                    groupNum = if (todoGroup.groupNum == -1) null else todoGroup.groupNum,
                    location = location,
                    title = title
                )
                val token = readAuthToken(context) ?: throw NullPointerException("Token Data is NULL on ${TAG} in AddTodo()")
                //data 추가
                val result = todoRepository.addTodo(token, newReq)
                if (result == "추가 성공"){
                    _okMsg.tryEmit("추가 성공")
                } else {
                    _okMsg.tryEmit(result)
                }
                //data fetch
                _todoGroups.tryEmit(todoRepository.getGroups(token))

                val tmpList = arrayListOf<Todo>()
                todoGroups.value.forEach{
                    tmpList.addAll(it.todoList)
                }

                _todos.tryEmit(tmpList)
            }
        }

        val today = calendar.getToday()[0]
        fetchTodos(today.substring(0,4) + "-" + today.substring(6,8) + "-" + today.substring(10,12))
    }

    //데이터 갱신
    fun fetchTodos(date : String){
        runBlocking {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val stdToday = dateFormat.parse(date) ?: throw NullPointerException("Today is NULL on ${TAG} in fatchTodos()")

            val result = mutableListOf<Todo>()
            todos.value.forEach{
                val deadDate = dateFormat.parse(it.deadDate) ?: throw NullPointerException("Today is NULL on ${TAG} in fatchTodos()")
                val startDate = dateFormat.parse(it.startDate) ?: throw NullPointerException("Today is NULL on ${TAG} in fetchTodos()")
                if (stdToday in startDate..deadDate){
                    result.add(it)
                }
            }
            _todosByDate.tryEmit(result)
        }
    }

    //todo 삭제
    fun deleteTodo(
        todoNum : Int
    ){
        val token = readAuthToken(context) ?: throw NullPointerException("Token is NULL on ${TAG} in deleteTodo")
        runBlocking{
            viewModelScope.launch(Dispatchers.IO){
                val result = todoRepository.delTodo(token = token, todoNum = todoNum)
                if (result != "삭제 성공") {
                    _errMsg.tryEmit(result)
                } else {
                    _okMsg.tryEmit("삭제 성공")
                }


                //data fetch
                _todoGroups.tryEmit(todoRepository.getGroups(token))

                val tmpList = arrayListOf<Todo>()
                todoGroups.value.forEach{
                    tmpList.addAll(it.todoList)
                }

                _todos.tryEmit(tmpList)
            }
        }

        val today = calendar.getToday()[0]
        fetchTodos(today.substring(0,4) + "-" + today.substring(6,8) + "-" + today.substring(10,12))
    }

    //todo group 추가
    fun addTodoGroup(
        title : String
    ){
        val token = readAuthToken(context) ?: throw NullPointerException("Token is NULL on ${TAG} in addTodoGroup")
        runBlocking{
            viewModelScope.launch(Dispatchers.IO){
                val resultMsg = todoRepository.addTodoGroup(token, title)
                if (resultMsg != "추가 성공") {
                    _errMsg.value = resultMsg
                }
                _todoGroups.tryEmit(todoRepository.getGroups(token = token))
            }
        }
    }

    //todo group 삭제
    fun delTodoGroup(todoGroup : TodoGroupInTodo){
        val token = readAuthToken(context) ?: throw NullPointerException("Token is NULL on ${TAG} in delTodoGroup")
        if (todoGroup.todoList[0].groupNum == -1){
            _errMsg.value == "No Group은 삭제 불가능합니다."
        } else {
            runBlocking{
                viewModelScope.launch(Dispatchers.IO){
                    /** 정아한테 groupNum을 받는 Api를 통해서 데이터를 변경해줘야 함 */
                    val resultMsg = todoRepository.delTodoGroup(token = token, todoGroupNum = todoGroup.todoList[0].groupNum)

                    if (resultMsg != "삭제 성공") {
                        _errMsg.value = resultMsg
                    }
                    _todoGroups.tryEmit(todoRepository.getGroups(token = token))
                }
            }
        }
    }

    //todo group update
    fun updateTodoGroup(todoGroup : TodoGroupInTodo){
        val token = readAuthToken(context) ?: throw NullPointerException("Token is NULL on ${TAG} in updateTodoGroup")
        if (todoGroup.groupNum == -1){
            _errMsg.value == "No Group은 수정 불가능합니다."
        } else {
            runBlocking{
                viewModelScope.launch(Dispatchers.IO){
                    /** 정아한테 groupNum을 받는 Api를 통해서 데이터를 변경해줘야 함 */
                    val resultMsg = todoRepository.updateTodoGroup(
                        token = token,
                        todoGroupNum = todoGroup.groupNum,
                        title = todoGroup.title,
                        isImportant = todoGroup.isImportant
                    )

                    if (resultMsg != "수정 성공") {
                        _errMsg.value = resultMsg
                    }

                    _todoGroups.tryEmit(todoRepository.getGroups(token = token))
                }
            }
        }
    }
}