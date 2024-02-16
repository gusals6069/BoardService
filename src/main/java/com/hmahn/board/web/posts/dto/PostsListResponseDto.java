package com.hmahn.board.web.posts.dto;

import com.hmahn.board.domain.posts.Posts;
import com.hmahn.board.domain.user.User;
import lombok.Getter;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String category;
    private String author;
    private User user;
    private String modifiedDate;

    public PostsListResponseDto(Posts entity) {
        this.id             = entity.getId();
        this.title          = entity.getTitle();
        this.category       = entity.getCategory();
        this.author         = entity.getAuthor();
        this.user           = entity.getUser();
        this.modifiedDate   = entity.getModifiedDate();
    }
}
