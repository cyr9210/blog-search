package com.bong.search.dto;

import lombok.Getter;

@Getter
public class PageResponseMeta {

    private final int totalCount;
    private final int pageableCount;
    private final boolean end;

    public PageResponseMeta(int totalCount, int pageableCount, boolean end) {
        this.totalCount = totalCount;
        this.pageableCount = pageableCount;
        this.end = end;
    }
}
