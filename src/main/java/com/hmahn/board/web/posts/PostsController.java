package com.hmahn.board.web.posts;

import com.hmahn.board.annotation.LoginUser;
import com.hmahn.board.service.posts.PostsService;
import com.hmahn.board.web.posts.dto.PostsResponseDto;
import com.hmahn.board.web.user.dto.UserSessionDto;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class PostsController {
    private final PostsService postsService;

    @GetMapping("/posts/save")
    public String postsSave(@LoginUser UserSessionDto user, Model model) throws Exception {
        if (user != null) {
            model.addAttribute("user", user);
        }else{
            return "redirect:/user/login";
        }

        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, @LoginUser UserSessionDto user,
                              HttpServletResponse response, Model model) throws Exception {
        if (user != null) {
            model.addAttribute("user", user);
        }else{
            return "redirect:/user/login";
        }

        PostsResponseDto dto = null;
        try {
            dto = postsService.findById(id);
        } catch (IllegalArgumentException ie) {
            response.sendError(204, ie.getMessage());
        } catch (NullPointerException ne) {
            response.sendError(500, ne.getMessage());
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        }

        if(dto != null){
            model.addAttribute("post", dto);
            model.addAttribute("category1", dto.getCategory().equals("유머") ? true : false);
            model.addAttribute("category2", dto.getCategory().equals("잡담") ? true : false);
            model.addAttribute("category3", dto.getCategory().equals("소식") ? true : false);
        }else{
            response.sendError(204);
        }

        return "posts-update";
    }

    @GetMapping("/posts/view/{id}")
    public String postsView(@PathVariable Long id, @LoginUser UserSessionDto user,
                            HttpServletResponse response, Model model) throws Exception {

        PostsResponseDto dto = null;
        try {
            dto = postsService.findById(id);
        } catch (IllegalArgumentException ie) {
            response.sendError(204, ie.getMessage());
        } catch (NullPointerException ne) {
            response.sendError(500, ne.getMessage());
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        }

        if(dto != null){
            model.addAttribute("post", dto);
        }else{
            response.sendError(204);
        }

        if (user != null && user.getUsername().equals(dto.getAuthor())) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("isAuthor", true);
        }else{
            model.addAttribute("username", null);
            model.addAttribute("isAuthor", false);
        }

        return "posts-view";
    }
}
