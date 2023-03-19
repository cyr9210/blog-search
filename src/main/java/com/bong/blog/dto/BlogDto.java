package com.bong.blog.dto;

import com.bong.search.dto.BlogSearchResponse;
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

    public BlogDto(BlogSearchResponse.Document kakaoBlog) {
        this.title = kakaoBlog.getTitle();
        this.contents = kakaoBlog.getContents();
        this.url = kakaoBlog.getUrl();
        this.blogName = kakaoBlog.getBlogname();
        this.thumbnail = kakaoBlog.getThumbnail();
        this.createdAt = kakaoBlog.getDatetime().toLocalDateTime();
    }
}
