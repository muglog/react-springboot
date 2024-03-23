package com.muglog.dto.review;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuReviewDto {
    private Long menuSeq;
    private String menuNm;
    private Integer rating;
    private String review;
    private List<String> imgUrls;
}
