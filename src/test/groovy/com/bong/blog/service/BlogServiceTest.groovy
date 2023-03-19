package com.bong.blog.service


import com.bong.blog.dto.PageInfo
import com.bong.blog.dto.SortType
import com.bong.search.dto.BlogSearchResponse
import com.bong.search.service.ExternalBlogSearchService
import spock.lang.Specification

import java.time.OffsetDateTime

class BlogServiceTest extends Specification {

    private BlogService sut
    private ExternalBlogSearchService externalBlogSearchServiceMock = Mock()

    def setup() {
        sut = new BlogService(externalBlogSearchServiceMock)
    }

    def "검색어로 블로그 목록을 조회한다."() {
        given:
        def pageInfo = Stub(PageInfo) {
            it.page >> requestPage
            it.size >> requestSize
            it.sort >> requestSort
        }

        when:
        def result = sut.findByKeyword(pageInfo, keyword)

        then:
        1 * externalBlogSearchServiceMock.findBlogsByKeyword(requestPage, requestSize, requestSort.getSortString(), keyword) >> Stub(BlogSearchResponse) {
            it.meta >> Stub(BlogSearchResponse.Meta) {
                it.totalCount >> 1
                it.pageableCount >> 1
                it.end >> true
            }

            def document = Stub(BlogSearchResponse.Document) {
                it.title >> "test success"
                it.contents >> "test success"
                it.datetime >> OffsetDateTime.now()
            }
            it.documents >> [document]
        }

        with(result) {
            it.meta.totalCount == 1
            it.meta.pageableCount == 1
            it.meta.end

            it.blogs.size() == 1
            it.blogs.title == ["test success"]
            it.blogs.contents == ["test success"]
        }

        where:
        requestPage | requestSize | requestSort       | keyword
        1           | 10          | SortType.ACCURACY | "test"
        2           | 20          | SortType.RECENCY  | "test"
        3           | 30          | SortType.ACCURACY | "success"
    }
}
