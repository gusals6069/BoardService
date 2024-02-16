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
import org.springframework.util.StringUtils;
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
    public String postList(@RequestParam(required = false, defaultValue = "1") int pageNo,
                           @LoginUser UserSessionDto user, HttpServletRequest request, Model model) throws Exception {
        if (user != null) {
            model.addAttribute("user", user);
        }

        ArrayList<Map<String, Object>> pages = new ArrayList<>();
        ArrayList<Map<String, Object>> types = new ArrayList<>();

        String searchType    = StringUtils.isEmpty(request.getParameter("searchType")) ? "" : request.getParameter("searchType");
        String searchKeyword = StringUtils.isEmpty(request.getParameter("searchKeyword")) ? "" : request.getParameter("searchKeyword");

        PageRequest pageable = PageRequest.of(pageNo-1, 10, Sort.by("id").descending());
        Page<PostsListResponseDto> list = postsService.findAll(searchType, searchKeyword, pageable);

        int blockLimit = 5;
        int nwPage = pageable.getPageNumber() + 1;
        int stPage = (((int) Math.ceil((double) nwPage / blockLimit) - 1) * blockLimit) + 1;
        int edPage = (((int) Math.ceil((double) nwPage / blockLimit)) * blockLimit);
        if( edPage > list.getTotalPages() ){
            edPage = Math.max(list.getTotalPages(), 1);
        }

        String[] searchTypeArray = {"title", "content", "author"}; // 공통코드 작성생략

        for(int idx=stPage; idx<=edPage; idx++){
            Map<String, Object> pageData = new HashMap<>();

            pageData.put("page", idx);
            pageData.put("curr", idx == nwPage);

            pages.add(pageData);
        }

        for(int idx=0; idx<=2; idx++){
            Map<String, Object> typeData = new HashMap<>();

            typeData.put("type", searchTypeArray[idx]);
            typeData.put("curr", !StringUtils.isEmpty(searchType) && searchType.equals(searchTypeArray[idx]));

            types.add(typeData);
        }

        model.addAttribute("posts", list);
        model.addAttribute("pages", pages);
        model.addAttribute("types", types);

        model.addAttribute("curr",  nwPage);
        model.addAttribute("next", (nwPage + 1));
        model.addAttribute("prev", (nwPage - 1));
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());

        model.addAttribute("searchType",    searchType);
        model.addAttribute("searchKeyword", searchKeyword);

        return "posts-list";
    }

    @GetMapping("/posts/view/{id}")
    public String postsView(@PathVariable Long id, @LoginUser UserSessionDto user,
                            HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        String pageNo        = StringUtils.isEmpty(request.getParameter("pageNo")) ? "1" : request.getParameter("pageNo");
        String searchType    = StringUtils.isEmpty(request.getParameter("searchType")) ? "" : request.getParameter("searchType");
        String searchKeyword = StringUtils.isEmpty(request.getParameter("searchKeyword")) ? "" : request.getParameter("searchKeyword");

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

            if(user.getId().equals(dto.getUser().getId())){
                model.addAttribute("isAuthor", true);
            }else{
                model.addAttribute("isAuthor", false);
            }
        }else{
            model.addAttribute("isAuthor", false);
        }

        model.addAttribute("pageNo",        pageNo);
        model.addAttribute("searchType",    searchType);
        model.addAttribute("searchKeyword", searchKeyword);

        return "posts-view";
    }

    @GetMapping("/posts/save")
    public String postsSave(@LoginUser UserSessionDto user,
                            HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        if (user != null) {
            model.addAttribute("user", user);
        }else{
            return "redirect:/user/login";
        }

        ArrayList<Map<String, Object>> category = new ArrayList<>();

        String pageNo        = StringUtils.isEmpty(request.getParameter("pageNo")) ? "1" : request.getParameter("pageNo");
        String searchType    = StringUtils.isEmpty(request.getParameter("searchType")) ? "" : request.getParameter("searchType");
        String searchKeyword = StringUtils.isEmpty(request.getParameter("searchKeyword")) ? "" : request.getParameter("searchKeyword");

        String[] cateArray = {"유머", "잡담", "소식"}; // 공통코드 작성생략

        for(int idx=0; idx<=2; idx++){
            Map<String, Object> cateData = new HashMap<>();

            cateData.put("cate", cateArray[idx]);
            cateData.put("curr", false);

            category.add(cateData);
        }

        model.addAttribute("category",      category);

        model.addAttribute("pageNo",        pageNo);
        model.addAttribute("searchType",    searchType);
        model.addAttribute("searchKeyword", searchKeyword);

        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, @LoginUser UserSessionDto user,
                              HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        ArrayList<Map<String, Object>> category = new ArrayList<>();

        String pageNo        = StringUtils.isEmpty(request.getParameter("pageNo")) ? "1" : request.getParameter("pageNo");
        String searchType    = StringUtils.isEmpty(request.getParameter("searchType")) ? "" : request.getParameter("searchType");
        String searchKeyword = StringUtils.isEmpty(request.getParameter("searchKeyword")) ? "" : request.getParameter("searchKeyword");

        PostsResponseDto dto = null;
        try {
            dto = postsService.findById(id);

            if(dto != null){
                model.addAttribute("post", dto);

                String[] cateArray = {"유머", "잡담", "소식"}; // 공통코드 작성생략

                for(int idx=0; idx<=2; idx++){
                    Map<String, Object> cateData = new HashMap<>();

                    cateData.put("cate", cateArray[idx]);
                    cateData.put("curr", !StringUtils.isEmpty(dto.getCategory()) && dto.getCategory().equals(cateArray[idx]));

                    category.add(cateData);
                }

                model.addAttribute("category",  category);

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

            if(user.getId().equals(dto.getUser().getId())){
                model.addAttribute("isAuthor", true);
            }else{
                model.addAttribute("isAuthor", false);
            }
        }else{
            return "redirect:/user/login";
        }

        model.addAttribute("pageNo",        pageNo);
        model.addAttribute("searchType",    searchType);
        model.addAttribute("searchKeyword", searchKeyword);

        return "posts-update";
    }
}
