package com.hmahn.board.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ANONYMOUS("ROLE_ANONYMOUS", "익명 사용자"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;

    private final String name;
}