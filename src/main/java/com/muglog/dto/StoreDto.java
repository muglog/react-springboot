package com.muglog.dto;

import com.muglog.entity.Store;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class StoreDto {

    private Long seq;
    private String title;
    private String roadAddress;
    private Integer mapx;
    private Integer mapy;
    private String category;
    private String telephone;
    private String thumbNail;

    public static Store dtoToEntity(StoreDto dto) {
        return Store.builder()
                    .storeNm(dto.title)
                    .storeAddress(dto.roadAddress)
                    .katechX(dto.mapx)
                    .katechY(dto.mapy)
                    .category(dto.category)
                    .telephone(dto.telephone)
                    .thumbNail(dto.thumbNail)
                    .build();
    }

}
