package com.hmahn.board.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    @NotBlank(message = "분류값을 선택해주시기 바랍니다.")
    private String category;
    @NotBlank(message = "제목을 입력해주시기 바랍니다.")
    private String title;
    @NotBlank(message = "내용을 입력해주시기 바랍니다.")
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String content, String category){
        this.title = title;
        this.content = content;
        this.category = category;
    }
}