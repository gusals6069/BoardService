package com.hmahn.board.web;

import com.hmahn.board.service.posts.PostsService;
import com.hmahn.board.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) throws Exception {
        //UserSessionDto user = (UserSessionDto) httpSession.getAttribute("user");
        //if (user != null) {
        //  model.addAttribute("userName", user.getName());
        //}
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() throws Exception {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) throws Exception {
        PostsResponseDto dto = postsService.findById(id);

        model.addAttribute("post", dto);

        // 머스타치에서는 JSTL과 같은 문법을 쓸 수 없기 때문에 이렇게 구현
        model.addAttribute("category1", dto.getCategory().equals("유머") ? true : false);
        model.addAttribute("category2", dto.getCategory().equals("잡담") ? true : false);
        model.addAttribute("category3", dto.getCategory().equals("소식") ? true : false);

        return "posts-update";
    }
}