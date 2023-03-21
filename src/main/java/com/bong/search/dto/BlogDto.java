package com.bong.search.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlogDto {

    private final String title;
    private final String contents;
    private final String url;
    private final String blogName;
    private final String thumbnail;
    private final LocalDateTime createdAt;

    public BlogDto(KakaoBlogSearchResponse.Document blog) {
        this.title = blog.getTitle();
        this.contents = blog.getContents();
        this.url = blog.getUrl();
        this.blogName = blog.getBlogname();
        this.thumbnail = blog.getThumbnail();
        this.createdAt = blog.getDatetime().toLocalDateTime();
    }

    public BlogDto(NaverBlogSearchResponse.Item blog) {
        this.title = blog.getTitle();
        this.contents = blog.getDescription();
        this.url = blog.getLink();
        this.blogName = blog.getBloggername();
        this.thumbnail = null;
        this.createdAt = blog.getPostdate().atStartOfDay();
    }
}
