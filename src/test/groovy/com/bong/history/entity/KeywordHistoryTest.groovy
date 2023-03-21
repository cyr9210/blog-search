package com.bong.history.entity

import spock.lang.Specification

class KeywordHistoryTest extends Specification {

    def "검색어를 검색하면 검색어 이력의 count가 1 오른다."() {
        when:
        def history = new KeywordHistory("test")

        then:
        history.getCount() == 1

        when:
        history.searchKeyword()

        then:
        history.getCount() == 2
    }
}
