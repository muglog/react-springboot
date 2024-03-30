package com.muglog.entity;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
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
public class MenuReview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "moglog_seq")
    private Muglog muglog;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "menu_seq")
    private Menu menu;

    private String customMenuNm;

    @Column(nullable = false)
    private Integer rating;

    private String simpleReviewArray;

    @Column(nullable = false)
    private String reviewContent;

    @Type(StringArrayType.class)
    @Column(columnDefinition = "text[]")
    private String[] imageUrlArray;

    @Column(nullable = false)
    private Integer thread;

    @Builder.Default
    private Integer bookmarkCnt = 0;

    @Builder.Default
    private Boolean isDel = false;

    @CreatedDate
    private LocalDateTime regDate;
}
