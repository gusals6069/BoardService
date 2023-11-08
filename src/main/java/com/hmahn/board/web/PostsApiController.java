package com.hmahn.board.web;

import com.hmahn.board.service.posts.PostsService;
import com.hmahn.board.web.dto.PostsListResponseDto;
import com.hmahn.board.web.dto.PostsResponseDto;
import com.hmahn.board.web.dto.PostsSaveRequestDto;
import com.hmahn.board.web.dto.PostsUpdateRequestDto;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/posts")
    public ResponseEntity<?> save(@RequestBody @Valid PostsSaveRequestDto requestDto, BindingResult bindingResult) {
        HashMap<String, Object> result = new HashMap<>();

        // BindingResult 클래스는 @Valid에 대한 결과값을 가지고 있다.
        if(bindingResult.hasErrors()) {
            List<HashMap<String, Object>> errors = new ArrayList<HashMap<String, Object>>();

            bindingResult.getAllErrors().forEach(error -> {
                FieldError fieldError = (FieldError) error;

                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("field", fieldError.getField());
                map.put("message", error.getDefaultMessage());

                errors.add(map);
            });

            result.put("type", "valid");
            result.put("data",  errors);

            // 에러 발생시 반환
            // .body("에러발생")이면 에러 발생시 String 에러발생 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(result.toString());
        }
        try{
            postsService.save(requestDto);
        }catch(Exception e){
            result.put("type", "error");
            result.put("data", null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());
        }
        return ResponseEntity.ok(requestDto);
    }

    @PutMapping("/api/posts/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid PostsUpdateRequestDto requestDto, BindingResult bindingResult) {
        HashMap<String, Object> result = new HashMap<>();

        // BindingResult 클래스는 @Valid에 대한 결과값을 가지고 있다.
        if(bindingResult.hasErrors()) {
            List<HashMap<String, Object>> errors = new ArrayList<HashMap<String, Object>>();

            bindingResult.getAllErrors().forEach(error -> {
                FieldError fieldError = (FieldError) error;

                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("field", fieldError.getField());
                map.put("message", error.getDefaultMessage());

                errors.add(map);
            });

            result.put("type", "valid");
            result.put("data",  errors);

            // 에러 발생시 반환
            // .body("에러발생")이면 에러 발생시 String 에러발생 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(result.toString());
        }
        try{
            postsService.update(id, requestDto);
        }catch(Exception e){
            result.put("type", "error");
            result.put("data", null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());
        }
        return ResponseEntity.ok(requestDto);
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, BindingResult bindingResult) {
        HashMap<String, Object> result = new HashMap<>();

        try{
            postsService.delete(id);
        }catch(Exception e){
            result.put("type", "error");
            result.put("data", null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());
        }
        return ResponseEntity.ok(id);
    }

    @GetMapping("/api/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @GetMapping("/api/posts/list")
    public List<PostsListResponseDto> findAll() {
        return postsService.findAllDesc();
    }

}
