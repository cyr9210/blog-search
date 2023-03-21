package com.bong.search.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BlogPageResponse {

    private final PageResponseMeta meta;
    private final List<BlogDto> blogs;

    public BlogPageResponse(PageResponseMeta meta, List<BlogDto> blogs) {
        this.meta = meta;
        this.blogs = blogs;
    }

}
