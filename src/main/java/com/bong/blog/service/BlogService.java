package com.bong.blog.service;

import com.bong.blog.dto.BlogDto;
import com.bong.blog.dto.BlogPageResponse;
import com.bong.blog.dto.PageInfo;
import com.bong.blog.dto.PageResponseMeta;
import com.bong.search.dto.BlogSearchResponse;
import com.bong.search.service.ExternalBlogSearchService;
import com.bong.search.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {

    private final SearchService searchService;

    public BlogService(ExternalBlogSearchService searchService) {
        this.searchService = searchService;
    }

    public BlogPageResponse findByKeyword(PageInfo pageInfo, String keyword) {
        BlogSearchResponse response = searchService.findBlogsByKeyword(pageInfo.getPage(), pageInfo.getSize(),
                pageInfo.getSort().getSortString(), keyword);
        BlogSearchResponse.Meta meta = response.getMeta();
        PageResponseMeta pageMeta = new PageResponseMeta(meta.getTotalCount(), pageInfo.getPage(), meta.getPageableCount(), meta.isEnd());
        List<BlogDto> blogs = response.getDocuments().stream().map(BlogDto::new).collect(Collectors.toList());
        return new BlogPageResponse(pageMeta, blogs);
    }
}
