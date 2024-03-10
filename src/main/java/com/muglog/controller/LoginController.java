package com.muglog.controller;

import com.muglog.dto.LoginRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @PostMapping("/googleLogin")
    public ResponseEntity<?> googleLogin(@RequestBody LoginRequest loginRequest){
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

        try{
            // 반환값에서 email, name 추출
            String email = response.get("email").toString();
            String name = response.get("name").toString();
            String jwt = "임시 jwt"; // TODO jwt 토큰 생성

            // TODO member_info 테이블에 upsert

            Map<String,Object> resMap = new HashMap<>();
            resMap.put("jwt", jwt);
            resMap.put("email", email);
            resMap.put("name", name);

            return new ResponseEntity<>(resMap, new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
