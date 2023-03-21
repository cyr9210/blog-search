package com.bong.history.service

import com.bong.blog.event.BlogSearchEvent
import com.bong.history.entity.KeywordHistory
import com.bong.history.repository.KeywordHistoryRepository
import spock.lang.Specification

class KeywordHistoryServiceTest extends Specification {

    private KeywordHistoryService sut

    private KeywordHistoryRepository keywordHistoryRepositoryMock = Mock()

    def setup() {
        sut = new KeywordHistoryService(keywordHistoryRepositoryMock)
    }

    def "검색 이벤트가 발생했을 때, 해당 검색어가 검색된적이 없었다면 검색어 이력을 생성하여 저장한다."() {
        given:
        def event = new BlogSearchEvent("test")

        when:
        sut.searchKeyword(event)

        then:
        1 * keywordHistoryRepositoryMock.findByKeyword("test") >> Optional.empty()
        1 * keywordHistoryRepositoryMock.save({
            it.keyword == "test"
            it.count == 1
        } as KeywordHistory)
    }

    def "검색 이벤트가 발생했을 때, 해당 검색어 이력에 검색횟수가 1 오른다. "() {
        given:
        def event = new BlogSearchEvent("test")
        def historyMock = Mock(KeywordHistory) {
            it.id >> 123L
            it.keyword >> "test"
            it.count >> 1L
        }

        when:
        sut.searchKeyword(event)

        then:
        1 * keywordHistoryRepositoryMock.findByKeyword("test") >> Optional.of(historyMock)
        1 * historyMock.searchKeyword()
    }

    def "검색회수 순으로 top10을 조횐한다."() {
        given:
        keywordHistoryRepositoryMock.findTop10() >> [stubHistory(101L, "keyword", 2),
                                                     stubHistory(102L, "keyword2", 1)]

        when:
        def historiesResponse = sut.findTop10()

        then:
        with(historiesResponse) {
            it.histories.rank == [1, 2]
            it.histories.keyword == ["keyword", "keyword2"]
            it.histories.count == [2, 1]
        }
    }

    private KeywordHistory stubHistory(Long id, String keyword, Long count) {
        Stub(KeywordHistory) {
            it.id >> id
            it.keyword >> keyword
            it.count >> count
        }
    }
}
