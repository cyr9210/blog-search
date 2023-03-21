package com.bong.history.dto;

import lombok.Getter;

@Getter
public class KeywordHistoryDto {

    private final int rank;
    private final String keyword;
    private final long count;

    public KeywordHistoryDto(int rank, String keyword, long count) {
        this.rank = rank;
        this.keyword = keyword;
        this.count = count;
    }
}
