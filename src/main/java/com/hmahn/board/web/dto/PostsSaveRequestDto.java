package com.hmahn.board.web.dto;

import com.hmahn.board.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    @NotBlank(message = "분류값을 선택해주시기 바랍니다.")
    private String category;
    @NotBlank(message = "제목을 입력해주시기 바랍니다.")
    private String title;
    @NotBlank(message = "내용을 입력해주시기 바랍니다.")
    private String content;
    @NotBlank(message = "작성자명을 입력해주시기 바랍니다.")
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
