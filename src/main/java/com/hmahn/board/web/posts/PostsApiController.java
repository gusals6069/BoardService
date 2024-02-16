package com.hmahn.board.web.posts;

import com.hmahn.board.annotation.LoginUser;
import com.hmahn.board.domain.user.Role;
import com.hmahn.board.domain.user.User;
import com.hmahn.board.service.posts.PostsService;
import com.hmahn.board.service.user.UserService;
import com.hmahn.board.web.posts.dto.PostsResponseDto;
import com.hmahn.board.web.posts.dto.PostsSaveRequestDto;
import com.hmahn.board.web.posts.dto.PostsUpdateRequestDto;

import com.hmahn.board.web.user.dto.UserSessionDto;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    private final UserService userService;

    @PostMapping("/api/posts")
    public ResponseEntity<?> save(@LoginUser UserSessionDto user,
                                  @RequestBody @Valid PostsSaveRequestDto requestDto, BindingResult bindingResult) {

        JSONObject result = new JSONObject();

        // BindingResult 클래스는 @Valid에 대한 결과값을 가지고 있다.
        if(bindingResult.hasErrors()) {
            List<JSONObject> errors = new ArrayList<JSONObject>();

            bindingResult.getAllErrors().forEach(error -> {
                FieldError fieldError = (FieldError) error;

                JSONObject jsonData = new JSONObject();
                jsonData.put("fieldId", fieldError.getField());
                jsonData.put("message", error.getDefaultMessage());

                errors.add(jsonData);
            });

            result.put("type", "valid");
            result.put("data",  errors);

            // 에러 발생시 반환
            // .body("에러발생")이면 에러 발생시 String 에러발생 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(result.toString());
        }

        try {
            if (StringUtils.isEmpty(user.getId()) || StringUtils.isEmpty(userService.findById(user.getId()).getId()))
                throw new NoSuchElementException();

            requestDto.setUser(userService.findById(user.getId())); // 현재 로그인된 유저객체 값 세팅
            postsService.save(requestDto);

        }catch(NoSuchElementException se){
            result.put("type", "login");
            result.put("data", null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());

        }catch(Exception e){
            result.put("type", "error");
            result.put("data", null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());
        }
        return ResponseEntity.ok(requestDto);
    }

    @PutMapping("/api/posts/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @LoginUser UserSessionDto user,
                                    @RequestBody @Valid PostsUpdateRequestDto requestDto, BindingResult bindingResult) {

        JSONObject result = new JSONObject();

        // BindingResult 클래스는 @Valid에 대한 결과값을 가지고 있다.
        if(bindingResult.hasErrors()) {
            List<JSONObject> errors = new ArrayList<JSONObject>();

            bindingResult.getAllErrors().forEach(error -> {
                FieldError fieldError = (FieldError) error;

                JSONObject jsonData = new JSONObject();
                jsonData.put("fieldId", fieldError.getField());
                jsonData.put("message", error.getDefaultMessage());

                errors.add(jsonData);
            });

            result.put("type", "valid");
            result.put("data",  errors);

            // 에러 발생시 반환
            // .body("에러발생")이면 에러 발생시 String 에러발생 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(result.toString());
        }

        PostsResponseDto dto = postsService.findById(id);
        try{
            if (StringUtils.isEmpty(user.getId()) || StringUtils.isEmpty(userService.findById(user.getId()).getId()))
                throw new NoSuchElementException();

            if(user.getId().equals(dto.getUser().getId())){
                postsService.update(id, requestDto);
            }else {
                throw new IllegalArgumentException();
            }
        }catch(NoSuchElementException se){
            result.put("type", "login");
            result.put("data", null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());

        }catch(Exception e){
            result.put("type", "error");
            result.put("data", null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());
        }
        return ResponseEntity.ok(requestDto);
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @LoginUser UserSessionDto user) {
        JSONObject result = new JSONObject();

        PostsResponseDto dto = postsService.findById(id);
        try{
            if (StringUtils.isEmpty(user.getId()) || StringUtils.isEmpty(userService.findById(user.getId()).getId()))
                throw new NoSuchElementException();

            if(user.getId().equals(dto.getUser().getId())){
                postsService.delete(id);
            }else {
                throw new IllegalArgumentException();
            }
        }catch(NoSuchElementException se){
            result.put("type", "login");
            result.put("data", null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());

        }catch(Exception e){
            result.put("type", "error");
            result.put("data", null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());
        }
        return ResponseEntity.ok(id);
    }
}
