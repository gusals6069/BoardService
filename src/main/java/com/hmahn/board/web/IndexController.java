package com.hmahn.board.web;

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
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(@LoginUser UserSessionDto user, Model model) throws Exception {
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(@LoginUser UserSessionDto user, Model model) throws Exception {
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }else{
            return "redirect:/userLogin";
        }

        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, @LoginUser UserSessionDto user,
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
            model.addAttribute("category1", dto.getCategory().equals("유머") ? true : false);
            model.addAttribute("category2", dto.getCategory().equals("잡담") ? true : false);
            model.addAttribute("category3", dto.getCategory().equals("소식") ? true : false);
        }else{
            response.sendError(204);
        }

        if (user == null || !user.getName().equals(dto.getAuthor())) {
            return "redirect:/userLogin";
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

        if (user != null && user.getName().equals(dto.getAuthor())) {
            model.addAttribute("isAuthor", true);
        }else{
            model.addAttribute("isAuthor", false);
        }

        return "posts-view";
    }
}