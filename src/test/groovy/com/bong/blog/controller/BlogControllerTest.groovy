package com.bong.blog.controller

import com.bong.base.BaseControllerTest
import com.bong.blog.dto.SortType
import com.bong.search.client.KakaoClient
import com.bong.search.dto.BlogSearchResponse
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

    def "키워드로 블로그 목록을 조회한다."() {
        given:
        def page = 1
        def size = 10
        def sort = SortType.RECENCY
        def keyword = "집짓기"

        and:
        def response = convertJsonResource("kakaoBlogSearchResponse", BlogSearchResponse.class)
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
                .andExpect(jsonPath('$.meta.currentPage').value(1))
                .andExpect(jsonPath('$.meta.pageableCount').value(793))
                .andExpect(jsonPath('$.meta.end').value(false))
                .andExpect(jsonPath('$.blogs.[0].title').value("부산발도르프학교 3학년 <b>집</b><b>짓기</b>"))
    }
}
