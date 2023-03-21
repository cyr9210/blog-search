package com.bong.history.service;

import com.bong.blog.event.BlogSearchEvent;
import com.bong.history.dto.KeywordHistoriesResponse;
import com.bong.history.dto.KeywordHistoryDto;
import com.bong.history.entity.KeywordHistory;
import com.bong.history.repository.KeywordHistoryRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class KeywordHistoryService {

    private final KeywordHistoryRepository keywordHistoryRepository;

    public KeywordHistoryService(KeywordHistoryRepository keywordHistoryRepository) {
        this.keywordHistoryRepository = keywordHistoryRepository;
    }

    @EventListener
    @Transactional
    public void searchKeyword(BlogSearchEvent event) {
        String keyword = event.getKeyword();
        keywordHistoryRepository.findByKeyword(keyword)
                .ifPresentOrElse(KeywordHistory::searchKeyword, () -> saveNewHistory(keyword));
    }

    private void saveNewHistory(String keyword) {
        keywordHistoryRepository.save(new KeywordHistory(keyword));
    }

    @Transactional(readOnly = true)
    public KeywordHistoriesResponse findTop10() {
        List<KeywordHistory> top10 = keywordHistoryRepository.findTop10();
        AtomicInteger rank = new AtomicInteger();
        List<KeywordHistoryDto> histories = top10.stream()
                .map(history -> convertDto(rank, history))
                .collect(Collectors.toList());

        return new KeywordHistoriesResponse(histories);
    }

    private KeywordHistoryDto convertDto(AtomicInteger rank, KeywordHistory history) {
        rank.getAndIncrement();
        return new KeywordHistoryDto(rank.get(), history.getKeyword(), history.getCount());
    }
}
