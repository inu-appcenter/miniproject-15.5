package com.study.toDoList.exception.ex;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MyErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"유저 아이디값이 존재하지 않습니다."),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND,"할일 아이디값이 존재하지 않습니다."),
    USER_DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST,"동일한 이메일이 존재합니다."),
    USER_DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST,"동일한 닉네임이 존재합니다."),
    ID_NOT_FOUND(HttpStatus.UNAUTHORIZED,"아이디가 존재하지 않습니다."),
    PASSWORD_NOT_MATCHED(HttpStatus.UNAUTHORIZED,"비밀번호가 틀립니다."),
    UPDATE_NOT_PERMITTED(HttpStatus.FORBIDDEN,"다른 사람의 할일은 수정이 불가능합니다"),
    DELETE_NOT_PERMITTED(HttpStatus.FORBIDDEN,"다른 사람의 할일은 삭제가 불가능합니다");

    private final HttpStatus status;
    private final String message;
}
