package com.hmahn.board.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus {
    PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 페이지를 찾을 수 없습니다."),
    ROLE_NOT_FOUND(HttpStatus.FORBIDDEN, "이용하시려면 로그인하셔야 합니다."),
    DATA_NOT_FOUND(HttpStatus.NO_CONTENT, "요청하신 데이터가 존재하지 않습니다."),
    REQUEST_CONFLICT(HttpStatus.CONFLICT, "서버 요청이 거부되었습니다."),
    REQUEST_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 생겼습니다.");

    private HttpStatus status;

    private String message;
}
