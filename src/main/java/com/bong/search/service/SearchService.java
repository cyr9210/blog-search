package com.bong.search.service;

import com.bong.blog.dto.BlogDto;
import java.util.List;

public interface SearchService {

    List<BlogDto> findBlogsByKeyword(String keyword);
}
