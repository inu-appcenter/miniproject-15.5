package com.study.toDoList.exception.ex;

import lombok.Getter;

@Getter
public class MyUnauthorizedException extends RuntimeException{
    private MyErrorCode errorCode;

    public MyUnauthorizedException(MyErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
