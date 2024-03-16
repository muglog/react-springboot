package com.muglog.repository;

import com.muglog.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailAndLoginType(String email, String socialLoginType);
}
