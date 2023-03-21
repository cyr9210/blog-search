package com.bong.search.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class NaverBlogSearchResponse {

    private String lastBuildDate;
    private int total;
    private int start;
    private int display;

    private List<Item> items;

    @Getter
    public static class Item {
        private String title;
        private String description;
        private String bloggerlink;
        private String bloggername;
        private String link;

        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate postdate;
    }
}
