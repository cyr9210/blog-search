package com.bong.blog.service

import com.bong.blog.dto.PageInfo
import com.bong.blog.dto.SortType
import com.bong.search.dto.BlogPageResponse
import com.bong.search.service.KakaoBlogSearchService
import com.bong.search.service.NaverBlogSearchService
import com.bong.search.service.ServiceType
import spock.lang.Specification

class SearchServiceDelegatorTest extends Specification {

    private SearchServiceDelegator sut
    private KakaoBlogSearchService kakaoBlogSearchServiceMock = Mock()
    private NaverBlogSearchService naverBlogSearchServiceMock = Mock()

    def setup() {
        sut = new SearchServiceDelegator([kakaoBlogSearchServiceMock, naverBlogSearchServiceMock])
    }

    def "kakao 에 블로그 검색을 요청한다."() {
        given:
        def pageInfo = Stub(PageInfo) {
            it.page >> 1
            it.size >> 10
            it.sort >> SortType.RECENCY
        }

        when:
        sut.findBlogsByKeyword(ServiceType.KAKAO, pageInfo, "keyword")

        then:
        1 * kakaoBlogSearchServiceMock.isSupported(ServiceType.KAKAO) >> true
        1 * kakaoBlogSearchServiceMock.findBlogsByKeyword(1, 10, "recency", "keyword") >> Stub(BlogPageResponse)
        0 * naverBlogSearchServiceMock.isSupported(_)
        0 * naverBlogSearchServiceMock.findBlogsByKeyword(_, _, _, _)
    }

    def "Naver 에 블로그 검색을 요청한다."() {
        def pageInfo = Stub(PageInfo) {
            it.page >> 1
            it.size >> 10
            it.sort >> SortType.RECENCY
        }

        when:
        sut.findBlogsByKeyword(ServiceType.NAVER, pageInfo, "keyword")

        then:
        1 * kakaoBlogSearchServiceMock.isSupported(ServiceType.NAVER) >> false
        0 * kakaoBlogSearchServiceMock.findBlogsByKeyword(_, _, _, _)
        1 * naverBlogSearchServiceMock.isSupported(ServiceType.NAVER) >> true
        1 * naverBlogSearchServiceMock.findBlogsByKeyword(1, 10, "date", "keyword") >> Stub(BlogPageResponse)
    }
}
