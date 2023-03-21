package com.bong.fixture;

import com.bong.search.dto.KakaoBlogSearchResponse;
import com.bong.search.dto.NaverBlogSearchResponse;
import com.bong.util.ConvertJsonUtil;

import java.io.IOException;

public class BlogSearchResponseFixture {

    public static KakaoBlogSearchResponse stubKakaoBlogSearchResponse() throws IOException {
        return ConvertJsonUtil.convertJsonResource("kakaoBlogSearchResponse", KakaoBlogSearchResponse.class);
    }

    public static NaverBlogSearchResponse stubNaverBlogSearchResponse() throws IOException {
        return ConvertJsonUtil.convertJsonResource("naverBlogSearchResponse", NaverBlogSearchResponse.class);
    }
}
