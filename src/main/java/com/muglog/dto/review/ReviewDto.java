package com.muglog.dto.review;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReviewDto {
    private Long storeSeq;
    private List<MenuReviewDto> reviews;
}
