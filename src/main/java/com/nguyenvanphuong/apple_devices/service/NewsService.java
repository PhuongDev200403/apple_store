package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.NewsRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.NewsResponse;

import java.io.IOException;

public interface NewsService {
    //Phương thức tạo mới một tin tức
    NewsResponse createNews(NewsRequest request) throws IOException;
}
