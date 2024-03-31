package com.muglog.dto.review;

import com.muglog.entity.MenuReview;
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
    private List<String> newPhotos;

    public static MenuReview dtoToEntity(MenuReviewDto dto){
        return MenuReview.builder()
                .menuSeq(dto.getMenuSeq())
                .customMenuNm(dto.getMenuNm())
                .rating(dto.getRating())
                .reviewContent(dto.getReview())
                .imageUrlArray(dto.getNewPhotos().toArray(String[]::new))
                .build();
    }
}
