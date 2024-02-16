package com.hmahn.board.domain.user;

import com.hmahn.board.domain.BaseTimeEntity;

import com.hmahn.board.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /*@OneToMany(mappedBy = "user")
    private List<Posts> posts;*/

    @Builder
    public User(String username, String email, String picture, Role role) {
        this.username   = username;
        this.email      = email;
        this.picture    = picture;
        this.role       = role;
    }

    public User update(String username, String picture) {
        this.username   = username;
        this.picture    = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
