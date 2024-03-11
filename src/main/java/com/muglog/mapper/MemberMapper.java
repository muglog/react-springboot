package com.muglog.mapper;

import com.muglog.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberMapper {
    Long findMemIdByEmailAndLoginType(String email, String loginType);
    Long save(MemberDto memberDto);
    void updateLastLoginDate(Long userId);


}
