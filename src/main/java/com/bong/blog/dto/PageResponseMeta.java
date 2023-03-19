package com.bong.blog.dto;

import lombok.Getter;

@Getter
public class PageResponseMeta {

    private final int totalCount;
    private final int currentPage;
    private final int pageableCount;
    private final boolean end;

    public PageResponseMeta(int totalCount, int currentPage, int pageableCount, boolean end) {
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.pageableCount = pageableCount;
        this.end = end;
    }
}
