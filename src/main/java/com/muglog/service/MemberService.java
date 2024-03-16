package com.muglog.service;

import com.muglog.dto.MemberDto;
import com.muglog.entity.Member;
import com.muglog.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long getLoginMemIdAndUpdateLastLoginDate(MemberDto memberDto) {
        String email = memberDto.getEmail();
        String loginType = memberDto.getLoginType();
        String memNm = memberDto.getMemNm();

        Member loginMember = memberRepository.findByEmailAndLoginType(email, loginType).orElse(null);
        if(loginMember == null){
            loginMember = memberRepository.save(new Member(email, loginType, memNm));
        }

        loginMember.setLastLoginDate(LocalDateTime.now());

        return loginMember.getMemId();
    }
}
