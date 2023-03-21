package com.bong.search.service;

import com.bong.search.client.KakaoClient;
import com.bong.search.dto.BlogDto;
import com.bong.search.dto.BlogPageResponse;
import com.bong.search.dto.KakaoBlogSearchResponse;
import com.bong.search.dto.PageResponseMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KakaoBlogSearchService implements SearchService {

    private final ServiceType serviceType;
    private final String authorization;
    private final KakaoClient kakaoClient;

    public KakaoBlogSearchService(@Value("${api-key.kakao}") String authorization, KakaoClient kakaoClient) {
        this.serviceType = ServiceType.KAKAO;
        this.authorization = authorization;
        this.kakaoClient = kakaoClient;
    }

    @Override
    public boolean isSupported(ServiceType serviceType) {
        return ServiceType.KAKAO == serviceType;
    }

    @Override
    public BlogPageResponse findBlogsByKeyword(int page, int size, String sort, String keyword) {
        log.info("find kakao blogs.");
        try {
            KakaoBlogSearchResponse response = kakaoClient.findBlogs(authorization, page, size, sort, keyword);
            KakaoBlogSearchResponse.Meta responseMeta = response.getMeta();
            PageResponseMeta meta = new PageResponseMeta(responseMeta.getTotalCount(), responseMeta.getPageableCount(), responseMeta.isEnd());
            List<BlogDto> blogs = response.getDocuments().stream().map(BlogDto::new).collect(Collectors.toList());
            return new BlogPageResponse(meta, blogs);
        } catch (Exception e) {
            throw new RuntimeException("kakao api can not available.", e);
        }
    }
}
