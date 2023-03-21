package com.bong.history.controller;

import com.bong.history.dto.KeywordHistoriesResponse;
import com.bong.history.service.KeywordHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeywordHistoryController {

    private final KeywordHistoryService keywordHistoryService;

    public KeywordHistoryController(KeywordHistoryService keywordHistoryService) {
        this.keywordHistoryService = keywordHistoryService;
    }

    @GetMapping("/histories/top10")
    public ResponseEntity<KeywordHistoriesResponse> findTop10() {
        KeywordHistoriesResponse response = keywordHistoryService.findTop10();
        return ResponseEntity.ok(response);
    }
}
