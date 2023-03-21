package com.bong.blog.event;

import lombok.Getter;

@Getter
public class BlogSearchEvent {

    private String keyword;

    public BlogSearchEvent(String keyword) {
        this.keyword = keyword;
    }
}
