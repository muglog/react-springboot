package com.muglog.controller;

import com.muglog.common_enum.FileType;
import com.muglog.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final FileUtil s3Service;

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

    @PostMapping("/api/test/upload")
    public String upload(MultipartFile multipartFile) throws IOException {
        return s3Service.uploadFiles(multipartFile, FileType.REVIEW_PHOTO);
    }
}
