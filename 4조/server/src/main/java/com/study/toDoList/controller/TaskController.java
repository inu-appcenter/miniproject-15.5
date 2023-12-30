package com.study.toDoList.controller;

import com.study.toDoList.config.TokenProvider;
import com.study.toDoList.dto.*;
import com.study.toDoList.service.TaskService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TokenProvider tokenProvider;

    @Parameter(name = "Authentication",description = "로그인 후 발급 받은 토큰",required = true,in = ParameterIn.HEADER)
    @Operation(summary = "할일추가",description = "url 헤더에 Authentication에 토큰을, 바디에 {title,description,endDate(yyyy-mm-dd)}을 json 형식으로 보내주세요.")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "할일추가성공"),
    })
    @PostMapping("")
    public ResponseEntity<?> save(@RequestHeader("Authentication") String token,@Valid @RequestBody TaskSaveDto taskSaveDto){
        Long id = Long.valueOf(tokenProvider.getUsername(token));
        Long taskId = taskService.save(id,taskSaveDto);
        log.info("할일 save 호출 id={}",id);
        return new ResponseEntity<>(new ResponseDto(taskId,"할일추가성공"), HttpStatus.CREATED);
    }
    @Parameter(name = "Authentication",description = "로그인 후 발급 받은 토큰",required = true,in = ParameterIn.HEADER)
    @Operation(summary = "할일수정",description = "url 경로변수에 할일아이디,바디에 {title,description,endDate}을 json 형식으로 보내주세요.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "할일수정성공")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid@RequestBody TaskUpdateDto taskUpdateDto, @RequestHeader("Authentication") String token){
        log.info("할일 update 호출 할일id:{}",id);
        Long loginId = Long.valueOf(tokenProvider.getUsername(token));
        taskService.update(id,taskUpdateDto,loginId);
        return new ResponseEntity<>(new ResponseDto(id,"할일수정성공"), HttpStatus.OK);
    }
    @Parameter(name = "Authentication",description = "로그인 후 발급 받은 토큰",required = true,in = ParameterIn.HEADER)
    @Operation(summary = "할일 가져오기",description = "url 경로변수에 할일아이디를 담아 보내주세요")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "할일가져오기성공")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id){
        log.info("할일 정보 호출 할일id:{}",id);
        return new ResponseEntity<>(taskService.getTask(id),HttpStatus.OK);
    }
    @Parameter(name = "Authentication",description = "로그인 후 발급 받은 토큰",required = true,in = ParameterIn.HEADER)
    @Operation(summary = "할일삭제",description = "url 경로변수에 할일아이디를 보내주세요.")
    @ApiResponses({
            @ApiResponse(responseCode = "204",description = "할일삭제성공")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,@RequestHeader("Authentication") String token){
        log.info("할일 delete 호출 할일id:{}",id);
        Long loginId = Long.valueOf(tokenProvider.getUsername(token));
        taskService.delete(id,loginId);
        return new ResponseEntity<>(new ResponseDto(id,"할일삭제성공"), HttpStatus.NO_CONTENT);
    }

    @Parameter(name = "Authentication",description = "로그인 후 발급 받은 토큰",required = true,in = ParameterIn.HEADER)
    @Operation(summary = "할일 완료여부 변경",description = "url 경로변수에 할일아이디를 보내주세요.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "할일 완료여부 변경 성공")
    })
    @GetMapping("/finish/{id}")
    public ResponseEntity<?> changeFinished(@PathVariable Long id, @RequestHeader("Authentication") String token){
        Long loginId = Long.valueOf(tokenProvider.getUsername(token));
        log.info("할일 완료여부 변경 호출 할일id:{}",id);
        return new ResponseEntity<>(taskService.changeIsFinished(id,loginId),HttpStatus.OK);
    }

}
