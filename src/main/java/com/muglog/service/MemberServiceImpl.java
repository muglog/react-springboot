package com.muglog.service;

import com.muglog.dto.MemberDto;
import com.muglog.mapper.MemberMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberMapper memberMapper;

    // memId 조회
    @Override
    public Long findMemIdByEmailAndLoginType(String email, String loginType) {
        return memberMapper.findMemIdByEmailAndLoginType(email, loginType);
    }

    // member 저장
    // TODO upsert로 할 경우 mem_id가 자동증가됨(재확인 필요)
    @Override
    public Long save(MemberDto memberDto) {
        try{
            return memberMapper.save(memberDto);
        }catch (Exception e){
            log.error("Exception", e);
            throw new RuntimeException("Failed to save member");
        }
    }

    // 마지막 로그인 날짜 업데이트
    @Override
    public void updateLastLoginDate(Long userId) {
        memberMapper.updateLastLoginDate(userId);
    }
}
