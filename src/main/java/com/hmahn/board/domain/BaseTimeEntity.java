package com.hmahn.board.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    // @getter : 접근자 메소드 자동 생성 (getName)
    // @MappedSuperclass : JPA Entity 클래스가 이 어노테이션이 달린 클래스를 상속할 경우 해당 클래스에 있는 필드도 컬럼으로 인식
    // @EntityListeners(AuditingEntityListener.class) : 해당 클래스에 Auditing 기능을 포함 (JPA CREATE/UPDATE 시, 입력/수정 시간을 매기는 기능)

    // @CreatedDate : Entity가 생성되어 저장될 때 시간을 자동으로 저장
    // @LastModifiedDate : Entity를 수정할 때 시간을 자동으로 저장

    // @PrePersist : 해당 Entity가 저장되기 이전에 실행
    // @PreUpdate : 해당 Entity를 업데이트하기 이전에 실행

    @CreatedDate
    private String createdDate;

    @LastModifiedDate
    private String modifiedDate;

    @PrePersist
    public void onPrePersist(){
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    @PreUpdate
    public void onPreUpdate(){
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }
}

