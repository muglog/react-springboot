package com.muglog.controller;


import com.muglog.dto.review.MenuReviewDto;
import com.muglog.dto.review.MuglogDto;
import com.muglog.repository.MenuReviewRepository;
import com.muglog.repository.MuglogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final MuglogRepository muglogRepository;
    private final MenuReviewRepository menuReviewRepository;

    @GetMapping("")
    public ResponseEntity<?> getMuglogs() {
        try {
            List<MuglogDto> muglogs = muglogRepository.findAll()
                    .stream()
                    .map(muglog -> MuglogDto.entityToDto(muglog))
                    .map(muglogDto -> {
                        muglogDto.setReviews(menuReviewRepository
                                .findAllByMuglogSeqOrderByThread(muglogDto.getMuglogSeq())
                                .stream().map(menuReview -> MenuReviewDto.entityToDto(menuReview))
                                .collect(Collectors.toList()));
                        return muglogDto;
                    }).collect(Collectors.toList());
            return new ResponseEntity<>(muglogs, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
