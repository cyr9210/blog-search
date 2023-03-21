package com.bong.search.client;

import com.bong.search.dto.KakaoBlogSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoAPI", url = "https://dapi.kakao.com")
public interface KakaoClient {

    @GetMapping("/v2/search/blog")
    KakaoBlogSearchResponse findBlogs(@RequestHeader("Authorization") String authorization,
                                      @RequestParam("page") Integer page,
                                      @RequestParam("size") Integer size,
                                      @RequestParam("sort") String sort,
                                      @RequestParam("query") String query);

}
