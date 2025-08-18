package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.NewsRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ApiResponse;
import com.nguyenvanphuong.apple_devices.dtos.response.NewsResponse;
import com.nguyenvanphuong.apple_devices.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    NewsService newsService;
    //Phương thức tạo mới tin tức
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> createNews(@ModelAttribute NewsRequest request) throws IOException {
        return ApiResponse.builder()
                .result(newsService.createNews(request))
                .build();
    }

    //Phương thức lấy tất cả danh sách
    @GetMapping()
    public ApiResponse<List<NewsResponse>> getAll(){
        return ApiResponse.<List<NewsResponse>>builder()
                .result(newsService.getAllNews())
                .build();
    }

    //Phương thức lấy theo id
    @GetMapping("/{id}")
    public ApiResponse<NewsResponse> getNewsById(@PathVariable Long id){
        return ApiResponse.<NewsResponse>builder()
                .result(newsService.getNewsById(id))
                .build();
    }

    //Phương thức xóa tin tức của admin
    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteNews(@PathVariable Long id){
        newsService.deleteNews(id);
        return ApiResponse.builder()
                .result("Xóa thành công")
                .build();
    }

    //Phướng thức lấy danh sách tin tức nổi bật
    @GetMapping("/is_featred")
    public ApiResponse<List<NewsResponse>> getNewsByIsFeatured(){
        return ApiResponse.<List<NewsResponse>>builder()
                .result(newsService.getNewsByIsFeaturedTrue())
                .build();
    }
}
