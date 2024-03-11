package com.muglog.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class MemberDto {
    private Long memId;
    private String memNm;
    private String email;
    private String nickname;
    private String loginType;
    private String role;
    private Timestamp lastLoginDate;

    public static MemberDto userInfoToMemberDto(String provider, String name, String email) {
        return MemberDto.builder()
                .memNm(name)
                .email(email)
                .loginType(provider)
                .build();
    }

}
