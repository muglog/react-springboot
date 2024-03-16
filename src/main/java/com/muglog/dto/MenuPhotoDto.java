package com.muglog.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MenuPhotoDto {

    private Long seq;
    private Long menuReviewSeq;
    private String imgUrl;
    private LocalDateTime regDate;
    private Boolean isDel;
}
