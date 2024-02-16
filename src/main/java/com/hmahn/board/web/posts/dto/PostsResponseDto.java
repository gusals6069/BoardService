package com.hmahn.board.web.posts.dto;

import com.hmahn.board.domain.posts.Posts;
import com.hmahn.board.domain.user.User;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String category;
    private String author;
    private User user;

    public PostsResponseDto(Posts entity){
        this.id         = entity.getId();
        this.title      = entity.getTitle();
        this.content    = entity.getContent();
        this.category   = entity.getCategory();
        this.author     = entity.getAuthor();
        this.user       = entity.getUser();
    }
}
