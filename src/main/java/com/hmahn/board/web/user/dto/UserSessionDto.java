package com.hmahn.board.web.user.dto;

import com.hmahn.board.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserSessionDto implements Serializable { // Serializable [직렬화] : 세션에 저장하기 위해 필요
    private Long id;
    private String username;
    private String email;
    private String picture;

    public UserSessionDto(User user) {
        this.id       = user.getId();
        this.username = user.getUsername();
        this.email    = user.getEmail();
        this.picture  = user.getPicture();
    }
}
