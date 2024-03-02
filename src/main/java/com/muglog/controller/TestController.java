package com.muglog.controller;

import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    private String test(){
        return "test";
    }

    @GetMapping("/api/test/getChar")
    private String getChar(@RequestParam(required = false, defaultValue = "0") Integer index){
        String hello = "안녕하세요";
        int strLength = 5;

        return String.valueOf(hello.charAt(index % strLength));
    }
}
