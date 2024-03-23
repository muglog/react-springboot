package com.muglog.service;

import com.muglog.dto.MemberDto;
import com.muglog.entity.Member;
import com.muglog.repository.MemberRepository;
import com.muglog.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long getLoginMemIdAndUpdateLastLoginDate(MemberDto memberDto) {
        String email = memberDto.getEmail();
        String loginType = memberDto.getLoginType();
        String memNm = memberDto.getMemNm();

        Member loginMember = memberRepository.findByEmailAndLoginType(email, loginType).orElse(null);
        if (loginMember == null) {
            loginMember = memberRepository.save(new Member(email, loginType, memNm));
        }

        loginMember.setLastLoginDate(LocalDateTime.now());

        return loginMember.getMemId();
    }

    @Transactional
    public ResponseEntity<?> processSocialLogin(String provider, Map<String, Object> response) {
        String email = (String) response.get("email");
        String name = (String) response.get("name");

        MemberDto memberDto = MemberDto.userInfoToMemberDto(provider, name, email);
        Long userId = this.getLoginMemIdAndUpdateLastLoginDate(memberDto);

        String jwt = JwtUtil.createJwtToken(userId);

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("jwt", jwt);
        resMap.put("email", email);
        resMap.put("name", name);
        return new ResponseEntity<>(resMap, new HttpHeaders(), HttpStatus.OK);
    }
}
