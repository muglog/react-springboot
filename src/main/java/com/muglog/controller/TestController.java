package com.muglog.controller;

import com.muglog.common_enum.FileType;
import com.muglog.dto.review.ReviewDto;
import com.muglog.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
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
    public List<String> upload(List<MultipartFile> files) {
        try{
            List<String> urlList = new ArrayList<>();

            for(MultipartFile multipartFile : files){
                urlList.add(s3Service.uploadFiles(multipartFile, FileType.REVIEW_PHOTO));
            }

            return urlList;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/api/edit")
    public void saveReview(@RequestBody ReviewDto reviewDto) {
        System.out.println(reviewDto);
    }
}
