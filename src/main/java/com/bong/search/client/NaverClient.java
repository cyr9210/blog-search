package com.bong.search.client;

import com.bong.search.dto.NaverBlogSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "NaverAPI", url = "https://openapi.naver.com")
public interface NaverClient {

    @GetMapping("/v1/search/blog.json")
    NaverBlogSearchResponse findBlogs(@RequestHeader("X-Naver-Client-Id") String id,
                                      @RequestHeader("X-Naver-Client-Secret") String secret,
                                      @RequestParam("start") Integer start,
                                      @RequestParam("display") Integer size,
                                      @RequestParam("sort") String sort,
                                      @RequestParam("query") String query);
}
