package com.bong.blog.service;

import com.bong.blog.dto.PageInfo;
import com.bong.search.dto.BlogPageResponse;
import com.bong.search.exception.ClientNotCallableException;
import com.bong.search.service.SearchService;
import com.bong.search.service.ServiceType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchServiceDelegator {

    private final List<SearchService> services;

    public SearchServiceDelegator(List<SearchService> services) {
        this.services = services;
    }

    public Optional<BlogPageResponse> findBlogsByKeyword(ServiceType serviceType, PageInfo pageInfo, String keyword) {
        try {
            return find(serviceType, pageInfo, keyword);
        } catch (ClientNotCallableException e) {
            return Optional.empty();
        }

    }

    private Optional<BlogPageResponse> find(ServiceType serviceType, PageInfo pageInfo, String keyword) {
        return services.stream()
                .filter(service -> service.isSupported(serviceType))
                .map(service -> service.findBlogsByKeyword(pageInfo.getPage(), pageInfo.getSize(), pageInfo.getSort().getSortString(serviceType), keyword))
                .findFirst();
    }
}
