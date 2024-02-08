package com.hmahn.board.web.posts;

import com.hmahn.board.annotation.LoginUser;
import com.hmahn.board.service.posts.PostsService;
import com.hmahn.board.web.posts.dto.PostsListResponseDto;
import com.hmahn.board.web.posts.dto.PostsResponseDto;
import com.hmahn.board.web.user.dto.UserSessionDto;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class PostsController {
    private final PostsService postsService;

    @GetMapping("/posts/list")
    public String postList(@RequestParam(required = false, defaultValue = "1") int page,
                           @LoginUser UserSessionDto user, HttpServletRequest request, Model model) throws Exception {
        if (user != null) {
            model.addAttribute("user", user);
        }

        String searchType       = request.getParameter("searchType");
        String searchKeyword    = request.getParameter("searchKeyword");

        PageRequest pageable = PageRequest.of(page-1, 10, Sort.by("id").descending());
        Page<PostsListResponseDto> list = postsService.findAll(searchType, searchKeyword, pageable);

        int blockLimit = 3;
        int nwPage = list.getPageable().getPageNumber() + 1;
        int stPage = (((int) Math.ceil((((double) pageable.getPageNumber() + 1) / blockLimit))) - 1) * blockLimit + 1;
        int edPage = Math.min((stPage + blockLimit - 1), Math.max(list.getTotalPages(), 1));




        System.out.println("nwPage=="+nwPage);
        System.out.println("stPage=="+stPage);
        System.out.println("edPage=="+edPage);
        System.out.println("(double) pageable.getPageNumber()=="+(double) pageable.getPageNumber());
        System.out.println("list.getTotalPages()=="+list.getTotalPages());

        ArrayList<Map<String, Object>> pages = new ArrayList<>();

        for(int idx=stPage; idx<=edPage; idx++){
            Map<String, Object> pageData = new HashMap<>();

            pageData.put("page", idx);
            pageData.put("curr", idx == nwPage);

            pages.add(pageData);
        }

        model.addAttribute("posts", list);
        model.addAttribute("pages", pages);
        model.addAttribute("next", nwPage + 1);
        model.addAttribute("prev", nwPage - 1);
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());

        //model.addAttribute("posts", postsService.findAllDesc());

        return "posts-list";
    }

    @GetMapping("/posts/view/{id}")
    public String postsView(@PathVariable Long id, @LoginUser UserSessionDto user,
                            HttpServletResponse response, Model model) throws Exception {

        PostsResponseDto dto = null;
        try {
            dto = postsService.findById(id);

            if(dto != null){
                model.addAttribute("post", dto);
            }else{
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException ie) {
            response.sendError(204, ie.getMessage());
        } catch (NullPointerException ne) {
            response.sendError(500, ne.getMessage());
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        }

        if (user != null) {
            model.addAttribute("user", user);

            if (user.getUsername().equals(dto.getAuthor())) {
                model.addAttribute("isAuthor", true);
            }else{
                model.addAttribute("isAuthor", false);
            }
        }else{
            model.addAttribute("isAuthor", false);
        }

        return "posts-view";
    }

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

        PostsResponseDto dto = null;
        try {
            dto = postsService.findById(id);

            if(dto != null){
                model.addAttribute("post", dto);

                // mustache 한계로 아래와 같이 설정
                model.addAttribute("category1", dto.getCategory().equals("유머"));
                model.addAttribute("category2", dto.getCategory().equals("잡담"));
                model.addAttribute("category3", dto.getCategory().equals("소식"));
            }else{
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException ie) {
            response.sendError(204, ie.getMessage());
        } catch (NullPointerException ne) {
            response.sendError(500, ne.getMessage());
        } catch (Exception e) {
            response.sendError(500, e.getMessage());
        }

        if (user != null) {
            model.addAttribute("user", user);

            if (user.getUsername().equals(dto.getAuthor())) {
                model.addAttribute("isAuthor", true);
            }else{
                model.addAttribute("isAuthor", false);
            }
        }else{
            return "redirect:/user/login";
        }

        return "posts-update";
    }
}
