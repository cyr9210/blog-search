package com.bong.search.service;

import com.bong.search.client.KakaoClient;
import com.bong.search.dto.BlogSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExternalBlogSearchService implements SearchService {

    @Value("${api-key.kakao}")
    private String authorization;

    private final KakaoClient kakaoClient;

    public ExternalBlogSearchService(KakaoClient kakaoClient) {
        this.kakaoClient = kakaoClient;
    }

    @Override
    public BlogSearchResponse findBlogsByKeyword(int page, int size, String sort, String keyword) {
        return kakaoClient.findBlogs(authorization, page, size, sort, keyword);
    }
}
