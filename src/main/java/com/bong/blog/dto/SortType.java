package com.bong.blog.dto;

import lombok.Getter;

@Getter
public enum SortType {
    ACCURACY("accuracy", "정확도순"),
    RECENCY("recency", "최신순");

    private final String sortString;
    private final String meaning;

    SortType(String sortString, String meaning) {
        this.sortString = sortString;
        this.meaning = meaning;
    }
}
