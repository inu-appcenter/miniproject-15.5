package com.example.actions.Controller;

import com.example.actions.DTO.CommonResponseDTO;
import com.example.actions.DTO.User.AddUserReqDTO;
import com.example.actions.DTO.User.ChangeUserReqDTO;
import com.example.actions.DTO.User.DeleteUserReqDTO;
import com.example.actions.DTO.User.ReadUserResDTO;
import com.example.actions.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

@RestController
@Api(tags = "User")
public class UserController {

    private final UserService userService;
    private final Long userId = 1l;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }


    @PostMapping("/users")
    @Operation(summary = "유저 회원가입", description = "1. 모든 값은 비어있거나 null이면 유효성 검사에 실패합니다.<br>2. email값은 중복이 허용되지 않습니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "유저 생성성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<CommonResponseDTO> addUser(@RequestBody @Valid AddUserReqDTO addUserReqDTO){
        userService.addUser(addUserReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponseDTO.of("CREATED","회원가입 완료",null));
    }

    @GetMapping("/users")
    @Operation(summary = "유저 정보조회", description = "1. userId값은 jwt토큰에서 추출합니다. 아직 구현이 되지 않아 임의의 값(1)으로 처리중이며 userId값은 입력하지 않아도 됩니다. <br>2.헤더에 인증토큰을 보내야 하며 로그인 성공 시 발급됩니다. 아직 로그인 기능이 구현되지 않았습니다.<br>3. 모든 값은 비어있거나 null이면 유효성 검사에 실패합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "유저 조회성공",response = ReadUserResDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<CommonResponseDTO> readUser(ServletRequest request){
        /*
            토큰에서 userId값 추출 로직
        */
        ReadUserResDTO readUserResDTO = userService.readUserInfo(userId);

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDTO.of("OK","유저정보 조회성공",readUserResDTO));
    }

    @PatchMapping("/users")
    @Operation(summary = "유저 정보변경", description = "1. userId값은 jwt토큰에서 추출합니다. 아직 구현이 되지 않아 임의의 값(1)으로 처리중이며 userId값은 입력하지 않아도 됩니다.<br>2.헤더에 인증토큰을 보내야 하며 로그인 성공 시 발급됩니다. 아직 로그인 기능이 구현되지 않았습니다.<br>3. 모든 값은 비어있거나 null이면 유효성 검사에 실패합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "유저 정보변경 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<CommonResponseDTO> changeUserInfo(@RequestBody @Valid ChangeUserReqDTO changeUserReqDTO, ServletRequest request){
        /*
            토큰에서 userId값 추출 로직
        */
        changeUserReqDTO.setUserId(userId);
        userService.changeUserInfo(changeUserReqDTO);

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDTO.of("OK","유저정보 변경성공",null));
    }
    @DeleteMapping("/users")
    @Operation(summary = "유저 삭제" , description = "1. userId값은 jwt토큰에서 추출합니다. 아직 구현이 되지 않아 임의의 값(1)으로 처리중이며 userId값은 입력하지 않아도 됩니다.<br>2.헤더에 인증토큰을 보내야 하며 로그인 성공 시 발급됩니다. 아직 로그인 기능이 구현되지 않았습니다.<br>3. 모든 값은 비어있거나 null이면 유효성 검사에 실패합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "유저 삭제성공"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다.")})
    public ResponseEntity<CommonResponseDTO> deleteUser(@RequestBody @Valid DeleteUserReqDTO deleteUserReqDTO, ServletRequest request){
        /*
            토큰에서 userId값 추출 로직
        */
        deleteUserReqDTO.setUserId(userId);
        userService.deleteUser(deleteUserReqDTO);

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDTO.of("OK","유저삭제 성공",null));
    }
}
