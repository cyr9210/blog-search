package com.bong.history.repository;

import com.bong.history.entity.KeywordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeywordHistoryRepository extends JpaRepository<KeywordHistory, Long> {

    Optional<KeywordHistory> findByKeyword(String keyword);

    default List<KeywordHistory> findTop10() { return findTop10ByOrderByCountDescId(); }

    List<KeywordHistory> findTop10ByOrderByCountDescId();


}
