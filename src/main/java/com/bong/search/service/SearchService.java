package com.bong.search.service;

import com.bong.search.dto.BlogPageResponse;

public interface SearchService {

    boolean isSupported(ServiceType serviceType);

    BlogPageResponse findBlogsByKeyword(int page, int size, String sort, String keyword);

}
