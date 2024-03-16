package com.muglog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String memNm;

    private String nickname;

    @Column(nullable = false)
    private String loginType;

    @ColumnDefault("'USER'")
    @Column(nullable = false)
    private String role;

    @CreatedDate
    private LocalDateTime regDate;

    private LocalDateTime lastLoginDate;

    public Member(String email, String loginType, String memNm) {
        this.email = email;
        this.loginType = loginType;
        this.memNm = memNm;
        this.role = "USER";
    }
}
