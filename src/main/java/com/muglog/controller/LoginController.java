package com.muglog.controller;

import com.muglog.dto.MemberDto;
import com.muglog.dto.login.LoginRequest;
import com.muglog.service.MemberService;
import com.muglog.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MemberService memberService;

    @PostMapping("/googleLogin")
    public ResponseEntity<?> googleLogin(@RequestBody LoginRequest loginRequest){
        try{
            // 프론트에서 받은 assessToken을 사용해 구글 유저 정보 조회
            WebClient client = WebClient.create("https://www.googleapis.com");
            Map<String, Object> response = client
                    .get()
                    .uri(
                            uriBuilder ->
                                    uriBuilder
                            .path("/oauth2/v1/userinfo")
                            .queryParam("access_token", loginRequest.getAccessToken())
                            .build()
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            // 반환값에서 email, name 추출
            String email = response.get("email").toString();
            String name = response.get("name").toString();
            String jwt = "";

            // 받아온 email, name을 memberDto에 매핑
            MemberDto memberDto = MemberDto.userInfoToMemberDto("google", name, email);

            // member정보가 없으면 저장, 있으면 마지막 로그인날짜 업데이트
            Long userId = memberService.findMemIdByEmailAndLoginType(email, "google");
            if(userId == null) {
                userId = memberService.save(memberDto);
            } else {
                memberService.updateLastLoginDate(userId);
            }

            if(userId > 0){
                jwt = JwtUtil.createJwtToken(userId);
            }

            Map<String,Object> resMap = new HashMap<>();
            resMap.put("jwt", jwt);
            resMap.put("email", email);
            resMap.put("name", name);

            return new ResponseEntity<>(resMap, new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception", e);
            return ResponseEntity.badRequest().build();
        }
    }

}
