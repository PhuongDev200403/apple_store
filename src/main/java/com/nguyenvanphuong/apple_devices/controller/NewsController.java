package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.NewsRequest;
import com.nguyenvanphuong.apple_devices.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    NewsService newsService;
    //Phương thức tạo mới tin tức
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createNews(@ModelAttribute NewsRequest request) throws IOException {
        return new ResponseEntity<>(newsService.createNews(request), HttpStatus.CREATED);
    }
}
