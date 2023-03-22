package com.bong.blog.controller

import com.bong.base.BaseControllerTest
import com.bong.blog.dto.SortType
import com.bong.blog.exception.ErrorCode
import com.bong.fixture.BlogSearchResponseFixture
import com.bong.search.client.KakaoClient
import com.bong.search.client.NaverClient
import com.bong.search.exception.ClientErrorException
import org.springframework.boot.test.mock.mockito.MockBean

import static org.mockito.ArgumentMatchers.anyInt
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.Mockito.when
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class BlogControllerTest extends BaseControllerTest {

    @MockBean
    private KakaoClient kakaoClientMock

    @MockBean
    private NaverClient naverClient

    def "KakaoAPI를 통해 키워드로 블로그 검색을 한다."() {
        given:
        def page = 1
        def size = 10
        def sort = SortType.RECENCY
        def keyword = "집짓기"

        and:
        def response = BlogSearchResponseFixture.stubKakaoBlogSearchResponse()
        when(kakaoClientMock.findBlogs(anyString(), anyInt(), anyInt(), anyString(), anyString())).thenReturn(response)

        expect:
        mvc.perform(get("/blogs/search")
                .param("page", page.toString())
                .param("size", size.toString())
                .param("sort", sort.name())
                .param("keyword", keyword)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.meta.totalCount').value(641639))
                .andExpect(jsonPath('$.meta.pageableCount').value(793))
                .andExpect(jsonPath('$.meta.end').value(false))
                .andExpect(jsonPath('$.blogs.[0].title').value("부산발도르프학교 3학년 <b>집</b><b>짓기</b>"))
    }

    def "KakaoAPI 가 불가능할 시, NaverAPI를 통해 키워드로 블로그 검색을 한다."() {
        given:
        def page = 1
        def size = 10
        def sort = SortType.RECENCY
        def keyword = "집짓기"

        and:
        def response = BlogSearchResponseFixture.stubNaverBlogSearchResponse()
        when(kakaoClientMock.findBlogs(anyString(), anyInt(), anyInt(), anyString(), anyString())).thenThrow(ClientErrorException.class)
        when(naverClient.findBlogs(anyString(), anyString(), anyInt(), anyInt(), anyString(), anyString())).thenReturn(response)

        expect:
        mvc.perform(get("/blogs/search")
                .param("page", page.toString())
                .param("size", size.toString())
                .param("sort", sort.name())
                .param("keyword", keyword)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.meta.totalCount').value(206988))
                .andExpect(jsonPath('$.meta.pageableCount').value(20699))
                .andExpect(jsonPath('$.meta.end').value(false))
                .andExpect(jsonPath('$.blogs.[0].blogName').value("불타는지방"))
    }

    def "가능할 블로그 검색 client가 없으면, 500 error 발생한다. - CAN_NOT_CALL_CLIENT"() {
        given:
        def page = 1
        def size = 10
        def sort = SortType.RECENCY
        def keyword = "집짓기"

        and:
        when(kakaoClientMock.findBlogs(anyString(), anyInt(), anyInt(), anyString(), anyString())).thenThrow(ClientErrorException.class)
        when(naverClient.findBlogs(anyString(), anyString(), anyInt(), anyInt(), anyString(), anyString())).thenThrow(ClientErrorException.class)

        expect:
        mvc.perform(get("/blogs/search")
                .param("page", page.toString())
                .param("size", size.toString())
                .param("sort", sort.name())
                .param("keyword", keyword)
        )
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath('$.code').value(ErrorCode.CAN_NOT_CALL_CLIENT.name()))
                .andExpect(jsonPath('$.message').value("can not find blogs"))
    }
}
