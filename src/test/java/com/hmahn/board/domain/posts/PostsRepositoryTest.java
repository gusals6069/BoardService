package com.hmahn.board.domain.posts;

import com.hmahn.board.domain.user.Role;
import com.hmahn.board.domain.user.User;
import com.hmahn.board.domain.user.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;
    @Autowired
    UserRepository userRepository;

    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void post_load() throws Exception {

        User user = userRepository.save(User.builder()
                .username("테스트")
                .email("test@email.com")
                .picture("")
                .role(Role.USER)
                .build());

        //given
        String title = "테스트_제목";
        String content = "테스트_본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .user(user)
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
        assertThat(posts.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    public void post_time_register() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);

        User user = userRepository.save(User.builder()
                .username("테스트")
                .email("test@email.com")
                .picture("")
                .role(Role.USER)
                .build());

        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .user(user)
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>createdDate="+ posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());

        LocalDateTime createDate = LocalDateTime.parse(posts.getCreatedDate(), DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        LocalDateTime modifyDate = LocalDateTime.parse(posts.getModifiedDate(), DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));

        assertThat(createDate).isAfter(now);
        assertThat(modifyDate).isAfter(now);
    }
}
