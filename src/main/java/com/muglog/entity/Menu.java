package com.muglog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @Column(nullable = false)
    private String menuNm;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "cate1_seq")
    private MenuCategory cate1;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "cate2_seq")
    private MenuCategory cate2;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "cate3_seq")
    private MenuCategory cate3;

    @Builder.Default
    private Integer reviewCnt = 0;

    @CreatedDate
    private LocalDateTime regDate;
}
