package com.bong.history.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class KeywordHistoriesResponse {

    private final List<KeywordHistoryDto> histories;

    public KeywordHistoriesResponse(List<KeywordHistoryDto> histories) {
        this.histories = histories;
    }
}
