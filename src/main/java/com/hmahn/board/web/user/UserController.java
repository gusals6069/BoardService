package com.hmahn.board.web.user;

import com.hmahn.board.annotation.LoginUser;
import com.hmahn.board.web.user.dto.UserSessionDto;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    @GetMapping("/user/login")
    public String login(Model model) throws Exception {
        return "user-login";
    }

    @GetMapping("/user/logout")
    public String logout(Model model) throws Exception {
        return "redirect:/logout";
    }

    @GetMapping("/user/changePassword")
    public String changePassword(@LoginUser UserSessionDto user,  Model model) throws Exception {
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "user-pass";
    }
}
