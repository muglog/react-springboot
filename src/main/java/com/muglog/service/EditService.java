package com.muglog.service;

import com.muglog.dto.review.MenuReviewDto;
import com.muglog.dto.review.MuglogDto;
import com.muglog.entity.*;
import com.muglog.repository.*;
import com.muglog.utils.JwtUtil;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
@Log4j2
public class EditService {

    @Value("${kakao.appKey}")
    private String kakaoAppKey;
    private final MuglogRepository muglogRepository;
    private final MenuRepository menuRepository;
    private final MenuReviewRepository menuReviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public void save(MuglogDto muglogDto, String accessToken) {
        Gson gson = new Gson();
        List<MenuReview> menuReviews = new ArrayList<>();
        try {
            //store insert
            Long storeSeq = muglogDto.getStoreId();
            Store store = storeRepository.findById(storeSeq).orElse(null);
            if (store == null) {
                WebClient webClient = WebClient.create("https://dapi.kakao.com");
                Map<String, Object> response = webClient.get()
                        .uri(uriBuilder -> uriBuilder.path("/v2/local/search/keyword.json")
                                .queryParam("query", muglogDto.getStoreNm())
                                .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "KakaoAK " + kakaoAppKey)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();

                String items = gson.toJson(response.get("documents"));

                Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();
                List<Map<String, Object>> storeDtos = gson.fromJson(items, type);
                for (Map<String, Object> s : storeDtos) {
                    if (muglogDto.getStoreId() == Long.parseLong(String.valueOf(s.get("id")))) {
                        store = Store.builder()
                                .storeId(Long.parseLong(String.valueOf(muglogDto.getStoreId())))
                                .storeNm(String.valueOf(s.get("place_name")))
                                .storeAddress(String.valueOf(s.get("road_address_name")))
                                .lat(Double.parseDouble((String) s.get("x")))
                                .lng(Double.parseDouble((String) s.get("y")))
                                .category(String.valueOf(s.get("category_name")))
                                .telephone(String.valueOf(s.get("phone")))
                                .build();

                        storeRepository.save(store);
                        break;
                    }
                }
            }

            //muglog insert
            Long userId = Long.parseLong(JwtUtil.getUserIdByJwtToken(accessToken));
            Member member = memberRepository.findByMemId(userId);
            Muglog muglog = MuglogDto.dtoToEntity(store.getStoreId(), member.getMemId());
            Muglog res = muglogRepository.save(muglog);

            //menuReview insert
            IntStream.range(0, muglogDto.getReviews().size()).forEach(idx -> {
                MenuReview menuReview = MenuReviewDto.dtoToEntity(muglogDto.getReviews().get(idx));
                menuReview.setMuglogSeq(res.getSeq());
                menuReview.setThread(idx);
                menuReviews.add(menuReview);
            });

            menuReviewRepository.saveAll(menuReviews);
        } catch (Exception e) {
            log.error("Exception", e);
        }

    }

    public Menu findByMenuNm(String menuNm) {
        return menuRepository.findTopByMenuNmContainingIgnoreCase(menuNm)
                .orElseThrow(RuntimeException::new);
    }

}
