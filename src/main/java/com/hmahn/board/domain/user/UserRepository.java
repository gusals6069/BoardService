package com.hmahn.board.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// 인터페이스 생성 후, JpaRepository<Entity 클래스, PK 타입>을 상속하면, 기본적인 CRUD 메소드가 자동 생성됨
// Repository 인터페이스는 Entity 클래스와 같은 경로에 같이 있어야한다.
// Optional : java8에서 신규추가된 객체로 null일 수 있는 객체를 감싸는 wrapper 클래스

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}