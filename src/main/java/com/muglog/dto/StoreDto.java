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
    private Double lat;
    private Double lng;
    private String category;
    private String telephone;
    private String thumbNail;

    public static Store dtoToEntity(StoreDto dto) {
        return Store.builder()
                    .storeNm(dto.title)
                    .storeAddress(dto.roadAddress)
                    .lat(dto.lat)
                    .lng(dto.lng)
                    .category(dto.category)
                    .telephone(dto.telephone)
                    .thumbNail(dto.thumbNail)
                    .build();
    }

}
