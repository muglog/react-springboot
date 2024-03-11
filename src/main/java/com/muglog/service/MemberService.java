package com.muglog.service;

import com.muglog.dto.MemberDto;

public interface MemberService {

    Long findMemIdByEmailAndLoginType(String email, String loginType);
    Long save(MemberDto memberDto);
    void updateLastLoginDate(Long userId);
}
