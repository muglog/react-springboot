package com.muglog.entity;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
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
public class MenuPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_review_seq")
    private MenuReview menuReview;

    @Column(nullable = false, length = 2048)
    private String imgUrl;

    @CreatedDate
    private LocalDateTime regDate;

    @Builder.Default
    private Boolean isDel = false;

}
