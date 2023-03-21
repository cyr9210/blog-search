package com.bong.blog.controller;

import com.bong.blog.dto.PageInfo;
import com.bong.blog.service.BlogService;
import com.bong.search.dto.BlogPageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Validated
@RestController
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blogs/search")
    public ResponseEntity<BlogPageResponse> findBlogs(@Valid PageInfo pageInfo, @NotBlank String keyword) {
        BlogPageResponse response = blogService.findByKeyword(pageInfo, keyword);
        return ResponseEntity.ok(response);
    }

}
