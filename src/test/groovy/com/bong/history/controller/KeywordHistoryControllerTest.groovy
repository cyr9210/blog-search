package com.bong.history.controller

import com.bong.base.BaseControllerTest
import org.springframework.test.context.jdbc.Sql

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Sql("classpath:keywordHistorySampleData.sql")
class KeywordHistoryControllerTest extends BaseControllerTest {

    def "검색회수 순 top10 검색이력을 조회한다."() {
        expect:
        mvc.perform(get("/histories/top10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.histories.[0].keyword').value("keyword11"))
                .andExpect(jsonPath('$.histories.[9].keyword').value("keyword3"))
    }
}
