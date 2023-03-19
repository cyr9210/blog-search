package com.bong.blog.controller;

import com.bong.blog.service.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blogs/search")
    public ResponseEntity findBlogs(String keyword) {
        blogService.searchByKeyword(keyword);
        return null;
    }

}
