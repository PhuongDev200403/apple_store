package com.nguyenvanphuong.apple_devices.mapper;

import com.nguyenvanphuong.apple_devices.dtos.request.NewsRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.NewsResponse;
import com.nguyenvanphuong.apple_devices.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    @Mapping(target = "imageUrl", ignore = true) // map thủ công thuộc tính imageUrl
    News toNews(NewsRequest request);

    NewsResponse toNewsResponse(News news);
}
