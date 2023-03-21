package com.bong.search.service


import com.bong.fixture.BlogSearchResponseFixture
import com.bong.search.client.NaverClient
import spock.lang.Specification

class NaverBlogSearchServiceTest extends Specification {

    private NaverBlogSearchService sut

    private static final CLIENT_ID = "id-mock"
    private static final SECRET = "secret-mock"

    private NaverClient naverClientMock = Mock()

    def setup() {
        sut = new NaverBlogSearchService(CLIENT_ID, SECRET, naverClientMock)
    }

    def "검색어로 블로그 목록 조회 시, 카카오 블로그 검색 API에 요청한다."() {
        given:
        def response = BlogSearchResponseFixture.stubNaverBlogSearchResponse()

        when:
        def blogPageResponse = sut.findBlogsByKeyword(page, size, "sim", "test")

        then:
        1 * naverClientMock.findBlogs(CLIENT_ID, SECRET, start, size, "sim", "test") >> response
        with(blogPageResponse) {
            it.meta.totalCount == 206988
            it.meta.pageableCount == pageableCount
            !it.meta.end
            it.blogs.blogName == ["불타는지방"]
        }

        where:
        page | size || start | pageableCount
        1    | 10   || 1     | 20699
        2    | 20   || 21    | 10350
    }
}
