package com.bong.search.service;

import com.bong.search.client.NaverClient;
import com.bong.search.dto.BlogDto;
import com.bong.search.dto.BlogPageResponse;
import com.bong.search.dto.NaverBlogSearchResponse;
import com.bong.search.dto.PageResponseMeta;
import com.bong.search.exception.ClientErrorException;
import com.bong.search.exception.ClientNotCallableException;
import com.bong.search.exception.GeneralClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NaverBlogSearchService implements SearchService {

    private final ServiceType serviceType;
    private final String clientId;
    private final String clientSecret;
    private final NaverClient naverClient;

    public NaverBlogSearchService(@Value("${api-key.naver.id}") String clientId,
                                  @Value("${api-key.naver.secret}") String clientSecret,
                                  NaverClient naverClient) {
        this.serviceType = ServiceType.NAVER;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.naverClient = naverClient;
    }

    @Override
    public boolean isSupported(ServiceType serviceType) {
        return ServiceType.NAVER == serviceType;
    }

    @Override
    public BlogPageResponse findBlogsByKeyword(int page, int size, String sort, String keyword) {
        log.info("find naver blogs.");
        int start = ((page - 1) * size) + 1;
        try {
            NaverBlogSearchResponse response = naverClient.findBlogs(clientId, clientSecret, start, size, sort, keyword);
            PageResponseMeta meta = getMeta(response.getTotal(), size, start);
            List<BlogDto> blogs = response.getItems().stream().map(BlogDto::new).collect(Collectors.toList());
            return new BlogPageResponse(meta, blogs);
        } catch (ClientErrorException | GeneralClientException e) {
            throw new ClientNotCallableException("naver api can not available.", e);
        }
    }

    private PageResponseMeta getMeta(int totalCount, int size, int start) {
        boolean end = totalCount <= start + size;
        int pageableCount = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
        return new PageResponseMeta(totalCount, pageableCount , end);
    }
}
