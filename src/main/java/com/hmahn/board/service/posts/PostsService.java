package com.hmahn.board.service.posts;

import com.hmahn.board.domain.posts.Posts;
import com.hmahn.board.domain.posts.PostsRepository;
import com.hmahn.board.web.posts.dto.PostsListResponseDto;
import com.hmahn.board.web.posts.dto.PostsResponseDto;
import com.hmahn.board.web.posts.dto.PostsSaveRequestDto;
import com.hmahn.board.web.posts.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        posts.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getCategory());

        return id;
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());

        postsRepository.delete(posts);
    }

    public PostsResponseDto findById(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());

        return new PostsResponseDto(posts);
    }

    //@Transactional(readOnly = true) 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도 개선
    /*@Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {

        //.map(PostsListResponseDto::new) => .map(posts -> new PostsListResponseDto(posts))
        // postsRepository findAllDesc 메소드에서 가져온 Posts의 stream을 map을 통해 PostsListResponseDto로 변환

        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }*/

    @Transactional(readOnly = true)
    public Page<PostsListResponseDto> findAll(String searchType, String searchKeyword, Pageable pageable) {
        int nowPage = pageable.getPageNumber() - 1; // page 위치에 있는 값은 0부터 시작한다.
        int perPage = 10; // 한페이지에 보여줄 글 개수

        Page<Posts> postsPage = null;

        // postsPage = postsRepository.findAll(PageRequest.of(nowPage, perPage, Sort.by(Sort.Direction.DESC, "id")));

        if(StringUtils.isEmpty(searchType) || StringUtils.isEmpty(searchKeyword)){
            postsPage = postsRepository.findAll(pageable);
        }else{
            if("title".equals(searchType)){
                postsPage = postsRepository.findByTitleContains(searchKeyword, pageable);
            }else if("content".equals(searchType)){
                postsPage = postsRepository.findByContentContains(searchKeyword, pageable);
            }else if("author".equals(searchType)){
                postsPage = postsRepository.findByAuthorContains(searchKeyword, pageable);
            }else{
                postsPage = postsRepository.findAll(pageable);
            }
        }

        return postsPage.map(PostsListResponseDto::new);
    }

}