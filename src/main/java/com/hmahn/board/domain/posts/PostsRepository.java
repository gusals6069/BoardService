package com.hmahn.board.domain.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// 인터페이스 생성 후, JpaRepository<Entity 클래스, PK 타입>을 상속하면, 기본적인 CRUD 메소드가 자동 생성됨
// Repository 인터페이스는 Entity 클래스와 같은 경로에 같이 있어야한다.
public interface PostsRepository extends JpaRepository<Posts, Long>{
    Page<Posts> findAll(Pageable pageable);

    Page<Posts> findByTitleContains(String searchKeyword, Pageable pageable);

    Page<Posts> findByContentContains(String searchKeyword, Pageable pageable);

    Page<Posts> findByAuthorContains(String searchKeyword, Pageable pageable);
}
