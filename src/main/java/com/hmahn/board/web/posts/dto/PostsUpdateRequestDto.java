package com.hmahn.board.web.posts.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    @NotBlank(message = "{valid.blank.category}")
    private String category;
    @NotBlank(message = "{valid.blank.title}")
    private String title;
    @NotBlank(message = "{valid.blank.content}")
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String content, String category){
        this.title = title;
        this.content = content;
        this.category = category;
    }
}