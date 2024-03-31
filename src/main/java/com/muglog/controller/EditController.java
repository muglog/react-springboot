package com.muglog.controller;

import com.muglog.dto.MenuDto;
import com.muglog.dto.review.MuglogDto;
import com.muglog.entity.Menu;
import com.muglog.service.EditService;
import com.muglog.service.StoreService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/edit")
@RequiredArgsConstructor
@Log4j2
public class EditController {

    private final StoreService storeService;

    private final EditService editService;

    @Value("${naver.store.search.clientId}")
    private String clientId;

    @Value("${naver.store.search.clientSecret}")
    private String clientSecret;

//    @GetMapping("/store/search")
//    public ResponseEntity<?> editStore(@RequestParam String keyword) {
//        Gson gson = new Gson();
//
//        try {
//            WebClient webClient = WebClient.create("https://openapi.naver.com");
//            Map<String, Object> response = webClient.get()
//                    .uri(uriBuilder -> uriBuilder.path("/v1/search/local.json")
//                            .queryParam("query", keyword)
//                            .queryParam("display", 5)
//                            .queryParam("sort", "random")
//                            .build())
//                    .accept(MediaType.APPLICATION_JSON)
//                    .header("X-Naver-Client-Id", clientId)
//                    .header("X-Naver-Client-Secret", clientSecret)
//                    .retrieve()
//                    .bodyToMono(Map.class)
//                    .block();
//
//            String items = gson.toJson(response.get("items"));
//
//            List<Store> stores = new ArrayList<>();
//            Type type = new TypeToken<List<StoreDto>>() {}.getType();
//            List<StoreDto> storeDtos = gson.fromJson(items, type);
//            storeDtos.forEach(dto ->
//                            //TODO 이미지 저장
////                    String imgPath = storeService.getStoreImg();
//                            stores.add(StoreDto.dtoToEntity(dto))
//            );
//
//            //저장
////            List<Store> res = storeService.getStoreByStoreNmAndMapXY(stores);
//
//            //유저 로그 저장
//            return new ResponseEntity<>(res, new HttpHeaders(), HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("Exception :: {}", e);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    //먹로그 등록
    @PostMapping("")
    public void saveReview(@RequestBody MuglogDto muglogDto, HttpServletRequest request) {
        try {
            String accessToken = request.getHeader("access_token");
            editService.save(muglogDto, accessToken);
        } catch (Exception e) {
            log.error("Exception", e);
            throw new RuntimeException(e);
        }
    }

    //메뉴 검색
    @GetMapping("/menu/search")
    public ResponseEntity<?> menuSearch(@RequestParam String menuNm) {
        try {
            Menu menu = editService.findByMenuNm(menuNm);
            MenuDto menuDto = MenuDto.entityToDto(menu);
            return new ResponseEntity<>(menuDto, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
