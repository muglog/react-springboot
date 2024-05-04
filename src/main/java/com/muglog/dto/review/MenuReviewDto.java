package com.muglog.dto.review;

import com.muglog.entity.MenuReview;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
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

    public static MenuReviewDto entityToDto(MenuReview menuReview) {
        return MenuReviewDto.builder()
                .menuSeq(menuReview.getMenuSeq())
                .rating(menuReview.getRating())
                .menuNm(menuReview.getCustomMenuNm())
                .review(menuReview.getReviewContent())
                .newPhotos(Arrays.stream(menuReview.getImageUrlArray()).toList())
                .build();
    }
}
