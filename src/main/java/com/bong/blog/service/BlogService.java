package com.bong.blog.service;

import com.bong.blog.dto.PageInfo;
import com.bong.blog.event.BlogSearchEvent;
import com.bong.search.dto.BlogPageResponse;
import com.bong.search.service.ServiceType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class BlogService {

    private final SearchServiceDelegator searchServiceDelegator;
    private final ApplicationEventPublisher publisher;

    public BlogService(SearchServiceDelegator searchServiceDelegator, ApplicationEventPublisher publisher) {
        this.searchServiceDelegator = searchServiceDelegator;
        this.publisher = publisher;
    }

    public BlogPageResponse findByKeyword(PageInfo pageInfo, String keyword) {
        BlogPageResponse response = Arrays.stream(ServiceType.values())
                .map(serviceType -> searchServiceDelegator.findBlogsByKeyword(serviceType, pageInfo, keyword))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst().orElseThrow(() -> new RuntimeException("can not find."));

        publisher.publishEvent(new BlogSearchEvent(keyword));
        return response;
    }
}
