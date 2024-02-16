package com.hmahn.board.web.posts.dto;

import com.hmahn.board.domain.posts.Posts;

import com.hmahn.board.domain.user.Role;
import com.hmahn.board.domain.user.User;
import com.hmahn.board.web.user.dto.UserSessionDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    @NotBlank(message = "{valid.blank.category}")
    private String category;
    @NotBlank(message = "{valid.blank.title}")
    private String title;
    @NotBlank(message = "{valid.blank.content}")
    private String content;
    private String author;
    private User user;

    @Builder
    public PostsSaveRequestDto(String title, String content, String category, User user){
        this.title      = title;
        this.content    = content;
        this.category   = category;
        this.author     = user.getUsername();
        this.user       = user;
    }

    public PostsSaveRequestDto setUser(User user){
        this.author = user.getUsername();
        this.user   = user;

        return this;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .category(category)
                .author(author)
                .user(user)
                .build();
    }
}
