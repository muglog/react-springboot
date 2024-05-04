package com.muglog.dto.review;


import com.muglog.entity.Muglog;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@Builder
public class MuglogDto {
    private Long muglogSeq;
    private Long storeId;
    private String storeAddr;
    private String storeNm;
    private String memNm;
    private List<MenuReviewDto> reviews;
    private String regDate;

    public static Muglog dtoToEntity(Long storeSeq, Long writerId){
        return Muglog.builder()
                .storeSeq(storeSeq)
                .writerId(writerId)
                .build();
    }

    public static MuglogDto entityToDto(Muglog muglog){
        return MuglogDto.builder()
                .muglogSeq(muglog.getSeq())
                .storeId(muglog.getStore().getStoreId())
                .storeNm(muglog.getStore().getStoreNm())
                .storeAddr(muglog.getStore().getStoreAddress())
                .memNm(muglog.getWriter().getMemNm())
                .regDate(muglog.getRegDate().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")))
                .build();
    }
}
