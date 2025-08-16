package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.NewsRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.NewsResponse;
import com.nguyenvanphuong.apple_devices.entity.News;
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

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService{
    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    private static final String UPLOAD_DIR = "uploads/images/";

    @Override
    public NewsResponse createNews(NewsRequest request) throws IOException {
        if (newsRepository.existsByTitle(request.getTitle())){
            throw new RuntimeException("Tin tức đã tồn tại");
        }
        News news = newsMapper.toNews(request);
        news.setPublishedAt(LocalDateTime.now());

        MultipartFile file = request.getImageUrl();
        if (file != null && !file.isEmpty()) {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            news.setImageUrl(fileName);
        }

        newsRepository.save(news);
        return newsMapper.toNewsResponse(news);
    }
}
