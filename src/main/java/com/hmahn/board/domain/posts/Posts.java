package com.hmahn.board.domain.posts;

import com.hmahn.board.domain.BaseTimeEntity;
import com.hmahn.board.domain.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 필수 어노테이션일수록 클래스와 가까이 붙여둔다.
// lombok 어노테이션은 편리하지만 Entity 어노테이션보다는 중요도가 낮으므로 클래스와 먼 맨 위쪽에 위치하였다.
// Entity 어노테이션은 DB테이블과 링크될 클래스를 명시할 때 추가한다. (JPA)
// Entity 어노테이션이 선언된 테이블에서는 Getter 어노테이션만 선언해야 한다. (no Setter), Setter가 없는 대신 생성자를 생성된다.

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    // id : 해당 테이블의 PK값을 나타냄
    // GeneratedValue : stratgy를 GenerationType.IDENTITY로 설정해야 auto_increment가 적용됨 (스프링부트 2.0부터)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Column : 해당 테이블의 컬럼을 나타냄 (기본값 length = 255)
    //Column 어노테이션은 author 컬럼처럼 어노테이션을 붙이지 않아도 자동 적용되지만
    //Column 어노테이션에 옵션이 필요할 경우에는 붙임 (length를 조절하거나 type을 변경할 때)

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String category;

    private String author;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(referencedColumnName = "id", name = "user_id", nullable = false)
    private User user;

    //Builder 어노테이션이 선언될 경우, 해당 클래스의 빌더패턴 클래스를 생성
    //Builder 어노테이션이 생성자에 선언될 경우, 생성자에 포함된 필드만 빌더에 포함 (ex: Posts.builder().title(title).build())
    @Builder
    public Posts(String title, String content, String category, String author, User user){
        this.title      = title;
        this.content    = content;
        this.category   = category;
        this.author     = author;
        this.user       = user;
    }

    public void update(String title, String content, String category){
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
