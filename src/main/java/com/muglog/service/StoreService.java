package com.muglog.service;

import com.muglog.entity.Store;
import com.muglog.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public List<Store> getStoreByStoreNmAndMapXY(List<Store> stores) {
        List<Store> storeList = new ArrayList<>();
        try {
            stores.forEach(s -> {
                String storeNm = s.getStoreNm();
                Integer katechX = s.getKatechX();
                Integer katechY = s.getKatechY();

                Store store = storeRepository.findByStoreNmAndKatechXAndKatechY(storeNm, katechX, katechY);
                if (store == null) {
                    store = storeRepository.save(s);
                }
                storeList.add(store);
            });
        } catch (Exception e) {
            log.error("Exception :: {}", e);
        }
        return storeList;
    }

//    public String getStoreImg(String keyword){
//        try {
//            WebClient webClient = WebClient.create("https://openapi.naver.com");
//            Map<String, Object> response = webClient.get()
//                    .uri(uriBuilder -> uriBuilder.path("/v1/search/image")
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
//        }catch (Exception e){
//            log.error("Exception :: {}", e);
//        }
//    }
}
