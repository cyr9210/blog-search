package com.bong.search.service


import com.bong.search.client.KakaoClient
import spock.lang.Specification

class ExternalBlogSearchServiceTest extends Specification {

    private ExternalBlogSearchService sut

    private KakaoClient kakaoClientMock = Mock()

    def setup() {
        sut = new ExternalBlogSearchService(kakaoClientMock)
    }

    def "검색어로 블로그 목록 조회 시, 카카오 블로그 검색 API에 요청한다."() {
        when:
        sut.findBlogsByKeyword(1, 10, "accuracy", "test")

        then:
        1 * kakaoClientMock.findBlogs(_, 1, 10, "accuracy", "test")
    }
}
