package com.muglog.controller;

import com.muglog.dto.StoreDto;
import com.muglog.entity.Store;
import com.muglog.service.StoreService;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/edit")
@RequiredArgsConstructor
@Log4j2
public class EditController {

    private final StoreService storeService;

    @Value("${naver.store.search.clientId}")
    private String clientId;

    @Value("${naver.store.search.clientSecret}")
    private String clientSecret;

    @GetMapping("/store/search")
    public ResponseEntity<?> editStore(@RequestParam String keyword) {
        Gson gson = new Gson();

        try {
            WebClient webClient = WebClient.create("https://openapi.naver.com");
            Map<String, Object> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/v1/search/local.json")
                            .queryParam("query", keyword)
                            .queryParam("display", 5)
                            .queryParam("sort", "random")
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .header("X-Naver-Client-Id", clientId)
                    .header("X-Naver-Client-Secret", clientSecret)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            String items = gson.toJson(response.get("items"));

            List<Store> stores = new ArrayList<>();
            Type type = new TypeToken<List<StoreDto>>() {}.getType();
            List<StoreDto> storeDtos = gson.fromJson(items, type);
            storeDtos.forEach(dto ->
                            //TODO 이미지 저장
//                    String imgPath = storeService.getStoreImg();
                            stores.add(StoreDto.dtoToEntity(dto))
            );

            //저장
            List<Store> res = storeService.getStoreByStoreNmAndMapXY(stores);

            //유저 로그 저장
            return new ResponseEntity<>(stores, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception :: {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
