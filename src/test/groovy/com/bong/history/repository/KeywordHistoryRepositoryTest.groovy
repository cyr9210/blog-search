package com.bong.history.repository

import com.bong.history.entity.KeywordHistory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification

@Sql("classpath:keywordHistorySampleData.sql")
@ActiveProfiles("test")
@DataJpaTest
class KeywordHistoryRepositoryTest extends Specification {

    @Autowired
    private KeywordHistoryRepository sut

    def "검색어 이력을 저장한다."() {
        when:
        def history = sut.save(new KeywordHistory("test"))

        then:
        with(history) {
            history.id != null
            history.count == 1
            history.keyword == "test"
            history.createdAt != null
            history.updatedAt != null
        }
    }

    def "검색어 이력을 검색어로 조회한다."() {
        when:
        def optionalHistory = sut.findByKeyword("keyword")

        then:
        optionalHistory.isPresent()
        with(optionalHistory.get()) {
            it.id == 101
            it.keyword == "keyword"
            it.count == 3
        }
    }

    def "검색횟수순으로 top10 검색이력을 조회한다."() {
        when:
        def top10 = sut.findTop10()

        then:
        top10.size() == 10
        top10.id == [111, 110, 109, 108, 107, 106, 105, 101, 104, 103]
    }
}
