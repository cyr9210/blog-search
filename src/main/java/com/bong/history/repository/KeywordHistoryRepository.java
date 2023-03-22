package com.bong.history.repository;

import com.bong.history.entity.KeywordHistory;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface KeywordHistoryRepository extends JpaRepository<KeywordHistory, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<KeywordHistory> findByKeyword(String keyword);

    default List<KeywordHistory> findTop10() { return findTop10ByOrderByCountDescId(); }

    List<KeywordHistory> findTop10ByOrderByCountDescId();


}
