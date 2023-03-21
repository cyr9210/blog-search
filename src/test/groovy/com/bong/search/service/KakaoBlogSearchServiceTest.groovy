package com.bong.search.service

import com.bong.fixture.BlogSearchResponseFixture
import com.bong.search.client.KakaoClient
import spock.lang.Specification

class KakaoBlogSearchServiceTest extends Specification {

    private KakaoBlogSearchService sut

    private static final AUTHORIZATION = "auth-mock"
    private KakaoClient kakaoClientMock = Mock()

    def setup() {
        sut = new KakaoBlogSearchService(AUTHORIZATION, kakaoClientMock)
    }

    def "검색어로 블로그 목록 조회 시, 카카오 블로그 검색 API에 요청한다."() {
        given:
        def response = BlogSearchResponseFixture.stubKakaoBlogSearchResponse()

        when:
        def blogPageResponse = sut.findBlogsByKeyword(1, 10, "accuracy", "test")

        then:
        1 * kakaoClientMock.findBlogs(AUTHORIZATION, 1, 10, "accuracy", "test") >> response
        with(blogPageResponse) {
            it.meta.totalCount == 641639
            it.meta.pageableCount == 793
            !it.meta.end
            it.blogs.blogName == ["부산발도르프학교 - 세상을 변화시키는 배움"]
        }

     }
}
