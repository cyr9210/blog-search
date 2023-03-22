package com.bong.history.service

import com.bong.blog.event.BlogSearchEvent
import com.bong.history.repository.KeywordHistoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification

import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Sql("classpath:keywordHistorySampleData.sql")
@ActiveProfiles("test")
@SpringBootTest
class ConcurrencyTest extends Specification{

    @Autowired
    private KeywordHistoryService keywordHistoryService

    @Autowired
    private KeywordHistoryRepository keywordHistoryRepository

    def "근접한 시간에 같은 검색어를 검색했을 때, 정상적으로 검색횟수가 기록된다."() {
        given:
        def keyword = "keyword"
        def threadSize = 100
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ExecutorService service = Executors.newFixedThreadPool(threadSize)

        when:
        for (i in 0..<threadSize) {
            service.execute(() -> {
                keywordHistoryService.searchKeyword(new BlogSearchEvent(keyword))
                countDownLatch.countDown()
            })
        }
        countDownLatch.await()

        then:
        def history = keywordHistoryRepository.findByKeyword(keyword).get()
        history.getCount() == 103
    }

}
