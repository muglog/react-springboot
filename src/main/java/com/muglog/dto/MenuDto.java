package com.muglog.dto;

import com.muglog.entity.Menu;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuDto {
    private Long seq;
    private String menuNm;
    private Integer reviewCnt;
    private String finalCategoryNm;

    public static MenuDto entityToDto(Menu menu) {
        return MenuDto.builder()
                .seq(menu.getSeq())
                .menuNm(menu.getMenuNm())
                .finalCategoryNm(getFinalCategoryNm(menu))
                .reviewCnt(menu.getReviewCnt())
                .build();
    }

    public static String getFinalCategoryNm(Menu menu) {
        String finalCategory = "";
        if (menu.getCate1() != null) {
            finalCategory += menu.getCate1().getCateNm();
        }
        if (menu.getCate2() != null) {
            finalCategory +=  " > " + menu.getCate2().getCateNm();
        }
        if (menu.getCate3() != null) {
            finalCategory +=  " > " + menu.getCate3().getCateNm();
        }
        return finalCategory;
    }

}
