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
        //Tìm kiếm xem tin tức có tồn tại hay không
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NEWS_NOT_FOUND));

        news.setTitle(request.getTitle());
        news.setContent(request.getContent());
        news.setIsFeatured(request.getIsFeatured());
        MultipartFile file = request.getImageUrl();
        if(file != null && !file.isEmpty()){
            try {
                Files.createDirectories(Paths.get(UPLOAD_DIR));

                // Làm sạch tên file
                String originalFileName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
                String fileName = System.currentTimeMillis() + "_" + originalFileName;

                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                news.setImageUrl(fileName);

            } catch (IOException e) {
                throw new AppException(ErrorCode.UPLOAD_FAILED);
            }
        }
        return newsMapper.toNewsResponse(newsRepository.save(news));
    }


    //chức năng lấy tất cả chỉ có admin mới lấy được bao gồm cả tin tức đã được xóa
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
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NEWS_NOT_FOUND));
        news.setIsActive(!news.getIsActive());
        newsRepository.save(news);
    }

    //Lấy danh sách tin tức đang active
    //Endpoint public
    @Override
    public List<NewsResponse> getNewsByIsActiveTrue() {
        return newsRepository.findByIsActiveTrue().stream()
                .map(newsMapper::toNewsResponse)
                .toList();
    }

    //Lấy danh sách nổi bật
    //Endpoint public
    @Override
    public List<NewsResponse> getNewsByIsFeaturedTrue() {
        List<News> newsList = newsRepository.findByIsFeaturedTrue();
        return newsList.stream()
                .map(newsMapper::toNewsResponse)
                .toList();
    }
}
