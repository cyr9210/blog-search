package com.bong.blog.service

import com.bong.blog.dto.PageInfo
import com.bong.blog.dto.SortType
import com.bong.blog.event.BlogSearchEvent
import com.bong.search.dto.BlogDto
import com.bong.search.dto.BlogPageResponse
import com.bong.search.dto.PageResponseMeta
import com.bong.search.service.ServiceType
import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification

class BlogServiceTest extends Specification {

    private BlogService sut
    private SearchServiceDelegator searchServiceDelegatorMock = Mock()
    private ApplicationEventPublisher publisherMock = Mock()

    def setup() {
        sut = new BlogService(searchServiceDelegatorMock, publisherMock)
    }

    def "검색어로 블로그 목록을 조회한다."() {
        given:
        def pageInfo = Stub(PageInfo) {
            it.page >> 1
            it.size >> 10
            it.sort >> SortType.RECENCY
        }
        def keyword = "test"

        and:
        def response = stubResponse()

        when:
        def result = sut.findByKeyword(pageInfo, keyword)

        then:
        1 * searchServiceDelegatorMock.findBlogsByKeyword(ServiceType.KAKAO, pageInfo, keyword) >> Optional.of(response)
        0 * searchServiceDelegatorMock.findBlogsByKeyword(ServiceType.NAVER, pageInfo, keyword)
        1 * publisherMock.publishEvent(_ as BlogSearchEvent)
        with(result) {
            it.meta.totalCount == 1
            it.meta.pageableCount == 1
            it.meta.end

            it.blogs.size() == 1
            it.blogs.title == ["test success"]
            it.blogs.contents == ["test success"]
        }
    }

    private BlogPageResponse stubResponse() {
        Stub(BlogPageResponse) {
            it.meta >> Stub(PageResponseMeta) {
                it.totalCount >> 1
                it.pageableCount >> 1
                it.end >> true
            }
            def stubBlogDto = Stub(BlogDto) {
                it.title >> "test success"
                it.contents >> "test success"
            }
            it.blogs >> [stubBlogDto]
        }
    }


    def "kakao가 응답이 없으면 naver로 블로그를 검색한다."() {
        given:
        def pageInfo = Stub(PageInfo) {
            it.page >> 1
            it.size >> 10
            it.sort >> SortType.RECENCY
        }
        def keyword = "test"

        and:
        def response = stubResponse()

        when:
        def result = sut.findByKeyword(pageInfo, keyword)

        then:
        1 * searchServiceDelegatorMock.findBlogsByKeyword(ServiceType.KAKAO, pageInfo, keyword) >> Optional.empty()
        1 * searchServiceDelegatorMock.findBlogsByKeyword(ServiceType.NAVER, pageInfo, keyword) >> Optional.of(response)
        1 * publisherMock.publishEvent(_ as BlogSearchEvent)
        with(result) {
            it.meta.totalCount == 1
            it.meta.pageableCount == 1
            it.meta.end

            it.blogs.size() == 1
            it.blogs.title == ["test success"]
            it.blogs.contents == ["test success"]
        }
    }
}
