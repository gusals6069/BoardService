package com.hmahn.board.web.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class userController {

    @GetMapping("/userLogin")
    public String login(Model model) throws Exception {
        return "login";
    }
}
