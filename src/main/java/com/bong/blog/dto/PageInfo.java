package com.bong.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@Getter @Setter
public class PageInfo {

    @Positive
    private int page = 1;

    @Positive @Max(50)
    private int size = 10;

    private SortType sort = SortType.ACCURACY;
}
