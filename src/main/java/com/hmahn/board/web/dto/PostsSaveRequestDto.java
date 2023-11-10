package com.hmahn.board.web.dto;

import com.hmahn.board.domain.posts.Posts;

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
    @NotBlank(message = "{valid.blank.author}")
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String category, String author){
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .category(category)
                .author(author)
                .build();
    }
}
