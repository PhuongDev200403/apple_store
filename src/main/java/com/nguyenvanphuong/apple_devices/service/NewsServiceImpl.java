package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.NewsRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.NewsResponse;
import com.nguyenvanphuong.apple_devices.entity.News;
import com.nguyenvanphuong.apple_devices.exception.AppException;
import com.nguyenvanphuong.apple_devices.exception.ErrorCode;
import com.nguyenvanphuong.apple_devices.mapper.NewsMapper;
import com.nguyenvanphuong.apple_devices.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService{
    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    private static final String UPLOAD_DIR = "uploads/images/";

    @Override
    public NewsResponse createNews(NewsRequest request) throws IOException {
        // Kiểm tra trùng title
        if (newsRepository.existsByTitle(request.getTitle())) {
            throw new AppException(ErrorCode.NEWS_EXISTED);
        }

        // Mapping từ request
        News news = newsMapper.toNews(request);
        news.setPublishedAt(LocalDateTime.now());

        // Xử lý upload ảnh
        MultipartFile file = request.getImageUrl();
        if (file != null && !file.isEmpty()) {
            try {
                Files.createDirectories(Paths.get(UPLOAD_DIR));

                // Làm sạch tên file
                String originalFileName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
                String fileName = System.currentTimeMillis() + "_" + originalFileName;

                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Lưu đường dẫn ảnh (có thể chỉ lưu tên file hoặc full URL tùy cách bạn muốn FE lấy ảnh)
                news.setImageUrl(fileName);

            } catch (IOException e) {
                throw new AppException(ErrorCode.UPLOAD_FAILED);
            }
        }

        // Lưu tin tức
        newsRepository.save(news);
        return newsMapper.toNewsResponse(news);
    }

    @Override
    public NewsResponse updateNews(NewsRequest request, Long id) {
        return null;
    }

    @Override
    public List<NewsResponse> getAllNews() {
        return newsRepository.findAll().stream()
                .map(newsMapper::toNewsResponse)
                .toList();
    }

    @Override
    public NewsResponse getNewsById(Long id) {
        //tìm kiếm theo id
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NEWS_NOT_FOUND));

        return newsMapper.toNewsResponse(news);
    }

    @Override
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public List<NewsResponse> getNewsByIsFeaturedTrue() {
        List<News> newsList = newsRepository.findByIsFeaturedTrue();
        return newsList.stream()
                .map(newsMapper::toNewsResponse)
                .toList();
    }
}
