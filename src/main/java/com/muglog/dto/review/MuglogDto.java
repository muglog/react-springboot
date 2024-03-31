package com.muglog.dto.review;

import com.muglog.dto.StoreDto;
import com.muglog.entity.Muglog;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MuglogDto {
    private String storeNm;
    private Long storeId;
    private StoreDto storeDto;
    private List<MenuReviewDto> reviews;

    public static Muglog dtoToEntity(Long storeSeq, Long writerId){
        return Muglog.builder()
                .storeSeq(storeSeq)
                .writerId(writerId)
                .build();
    }
}
