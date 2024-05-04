package com.muglog.entity;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.awt.*;
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
public class Muglog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "store_seq", insertable = false, updatable = false)
    private Store store;

    @Column(name = "store_seq")
    private Long storeSeq;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "writer_id", insertable = false, updatable = false)
    private Member writer;

    @Column(name = "writer_id")
    private Long writerId;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDel = false;

    @CreatedDate
    private LocalDateTime regDate;

    @LastModifiedDate
    private LocalDateTime modDate;

    public Muglog(Long storeSeq, Long writerId){
        this.storeSeq = storeSeq;
        this.writerId = writerId;
    }
}
