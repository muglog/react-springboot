package com.muglog.controller;

import com.muglog.dto.login.LoginRequest;
import com.muglog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberService memberService;

    @Value("${naver_login_client_id}")
    private String naverClientId;
    @Value("${naver_login_client_secret}")
    private String naverClientSecret;

    @PostMapping("/googleLogin")
    public ResponseEntity<?> googleLogin(@RequestBody LoginRequest loginRequest) {
        try {
            WebClient webClient = WebClient.create("https://www.googleapis.com");
            Map<String, Object> response = webClient.get()
                    .uri(uriBuilder ->
                            uriBuilder.path("/oauth2/v1/userinfo")
                                    .queryParam("access_token", loginRequest.getAccessToken())
                                    .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return memberService.processSocialLogin("google", response);
        } catch (Exception e) {
            logger.error("Exception", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/naverLogin")
    public ResponseEntity<?> naverLogin(@RequestBody Map<String,Object> param) {
        try {
            String code = String.valueOf(param.get("code"));
            String state = String.valueOf(param.get("state"));
            WebClient webClient = WebClient.create("https://nid.naver.com");
            Map<String, Object> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/oauth2.0/token")
                            .queryParam("grant_type", "authorization_code")
                            .queryParam("client_id", naverClientId)
                            .queryParam("client_secret", naverClientSecret)
                            .queryParam("code", code)
                            .queryParam("state", state)
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            String accessToken = (String) response.get("access_token");

            Map<String, Object> userInfo = (Map<String, Object>) WebClient.create("https://openapi.naver.com").get()
                    .uri("/v1/nid/me")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block()
                    .get("response");

            return memberService.processSocialLogin("naver", userInfo);
        } catch (Exception e) {
            logger.error("Exception", e);
            return ResponseEntity.badRequest().build();
        }
    }
}

