package com.bong.search.service;

import com.bong.search.dto.BlogSearchResponse;

public interface SearchService {

    BlogSearchResponse findBlogsByKeyword(int page, int size, String sort, String keyword);

}
