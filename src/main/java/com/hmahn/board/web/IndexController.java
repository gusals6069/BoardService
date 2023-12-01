package com.hmahn.board.web;

import com.hmahn.board.annotation.LoginUser;
import com.hmahn.board.service.posts.PostsService;
import com.hmahn.board.web.user.dto.UserSessionDto;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(@LoginUser UserSessionDto user, Model model) throws Exception {
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "index";
    }

    @GetMapping("/about")
    public String about(@LoginUser UserSessionDto user, Model model) throws Exception {
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "about";
    }
}