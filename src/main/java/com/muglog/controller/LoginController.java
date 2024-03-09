package com.muglog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @PostMapping("/googleLogin")
    public String googleLogin(String accessToken){
        // TODO 구글 로그인 프로세스 구현

        return "jwt 반환해야함!!";
    }

}
