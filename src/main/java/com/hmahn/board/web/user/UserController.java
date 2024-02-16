package com.hmahn.board.web.user;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserController {
    @GetMapping("/user/login")
    public String login(Model model) throws Exception {
        return "login";
    }

    @GetMapping("/user/logout")
    public String logout(Model model) throws Exception {
        return "redirect:/logout";
    }
}
