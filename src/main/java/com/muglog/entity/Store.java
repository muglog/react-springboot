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
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @Column(nullable = false)
    private String storeNm;

    @Column(nullable = false)
    private String storeAddress; // 도로명 주소

    @Column(name = "katech_x", nullable = false)
    private Integer katechX;

    @Column(name = "katech_y", nullable = false)
    private Integer katechY;

    private String category;

    private String telephone;

    @ColumnDefault("0")
    private Integer reviewCnt;

    @ColumnDefault("0.0")
    private Float agvRating;

    private String thumbNail;

    @CreatedDate
    private LocalDateTime regDate;

    @LastModifiedDate
    private LocalDateTime modDate;

}
