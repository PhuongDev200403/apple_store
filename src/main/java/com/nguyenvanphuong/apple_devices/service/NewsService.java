package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.NewsRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.NewsResponse;

import java.io.IOException;
import java.util.List;

public interface NewsService {
    //Phương thức tạo mới một tin tức
    NewsResponse createNews(NewsRequest request) throws IOException;

    //Phương thức cập nhật tin tức
    NewsResponse updateNews(NewsRequest request, Long id);

    //Phương thức lấy tất ca tin tức
    List<NewsResponse> getAllNews();

    //Phương thức lấy tin tức theo id
    NewsResponse getNewsById(Long id);

    //Phương thức xóa tin tức
    void deleteNews(Long id);

    //phương thức lấy tin tức đang còn active
    List<NewsResponse> getNewsByIsActiveTrue();

    //Phương thức lấy tin tức nổi bật
    List<NewsResponse> getNewsByIsFeaturedTrue();
}
