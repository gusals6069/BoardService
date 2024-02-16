package com.hmahn.board.web.exception.dto;

import com.hmahn.board.domain.exception.ErrorStatus;
import lombok.Getter;

@Getter
public class ExceptionDto {
    private ErrorStatus errorStatus;
    private String message;

    public ExceptionDto(ErrorStatus errorStatus){
        this.errorStatus = errorStatus;
        this.message = errorStatus.getMessage();
    }

    public ExceptionDto(ErrorStatus errorStatus, String message){
        this.errorStatus = errorStatus;
        this.message = message; // 메세지를 별도로 작성
    }
}
