package com.bong.base

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Disabled
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
@Disabled
class BaseControllerTest extends Specification {

    @Autowired
    protected MockMvc mvc

    @Autowired
    protected ObjectMapper objectMapper

    protected <T> T convertJsonResource(String resourceName, Class<T> valueType) throws IOException {
        def path = String.format("/response/%s.json", resourceName)
        def response = this.getClass().getResource(path)
        return objectMapper.readValue(response, valueType)
    }
}