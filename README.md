# Apple Devices 

##  Author
**Nguyễn Văn Phương**  
 phuongnguyen20403@gmail.com  

## Giới thiệu 
**Apple Devices** là một dự án cá nhân được phát triển bằng **Spring Boot** nhằm mô phỏng hệ thống bán thiết bị điện tử Apple. 
Dự án giúp em củng cố kiến thức về **Spring Boot, RESTful API, JPA, MySQL, JWT Authentication và Docker**, sử dụng **Cloudinary** để lưu trữ ảnh sản phẩm. 
--- 
## Tính năng chính 
- **Admin**:
  + Quản lý người dùng(Thêm, sửa, xóa), quản lý đơn hàng(Xem, cập nhật trang thái)
  + Quản lý sản phẩm(Xem, thêm, sửa, xóa) , quản lý biến thể của sản phẩm
  + Quản lý danh mục(Thêm, sửa, xóa, xem), quản lý danh mục con(Thêm, sửa, xóa, xem)
  + Quản lý giỏ hàng(Xem), quản lý danh sách yêu thích(Xem)
  + Đăng nhập
- **User**:
  + Đăng nhập, đăng ký
  + Thêm sản phẩm vào giỏ hàng, xóa sản phẩm khỏi giỏ hàng, clear giỏ hàng, mua tất cả sản phẩm trong giỏ hàng
  + Thêm sản phẩm vào danh sách yêu thích, xóa sản phẩm khỏi danh sách yêu thích
  + Tìm kiếm sản phẩm, mua hàng
  + Đổi mật khẩu +Cập nhật thông tin cá nhân
---
## Kiến thức và công nghệ sử dụng 
| Công nghệ | Mô tả | 
|------------|-------| 
| **Java** | Ngôn ngữ chính | 
| **Spring Boot 3** | Framework backend | 
| **Spring Security + JWT** | Xác thực và phân quyền | 
| **Spring Data JPA** | Tương tác cơ sở dữ liệu | 
| **MySQL** | Hệ quản trị cơ sở dữ liệu | 
| **Docker** | Đóng gói và triển khai ứng dụng | 
| **Postman** | Kiểm thử API | 

## ER Diagram
<img width="1156" height="1066" alt="image" src="https://github.com/user-attachments/assets/463244f0-8329-49ff-a099-8b515d2dbe61" />

## Ví dụ API 
**Endpoint** 
#### Đăng nhập 
POST /api/auth/login 
**Request body:**
```json
{
    "email":"vtlo@gmail.com",
    "password":"123456789"
}
```
**Response**
{
    "code": 0,
    "result": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwidXNlcm5hbWUiOiJ2dGxvQGdtYWlsLmNvbSIsInN1YiI6InZ0bG9AZ21haWwuY29tIiwiaWF0IjoxNzYwMzUzNjMwLCJleHAiOjE3NjAzNTcyMzB9.pW0AwEGW2kpZRedYgXcFF9Me392Sn81jH9iU1248a-Y"
}
#### Lấy tất cả biến thể sản phẩm
GET /api/variants
```json
{
    "code": 0,
    "result": [
        {
            "id": 1,
            "price": 25990000.00,
            "status": "ACTIVE",
            "imageUrl": "1757924998409_12-hinh-anh-iphone-15-pro-max-didongviet.jpg",
            "quantity": 99,
            "memory": "128",
            "sku": "IP15-BLACK-128",
            "color": "black",
            "specifications": "{\n  \"ram\": \"8GB\",\n  \"storage\": \"256GB\",\n  \"chip\": \"A17 Pro\",\n  \"screen_size\": \"6.7 inch\",\n  \"battery\": \"4422 mAh\",\n  \"camera\": \"48MP + 12MP + 12MP\",\n  \"os\": \"iOS 17\",\n  \"weight\": \"221g\"\n}",
            "slug": "iphone-15-pro-max-blue-256gb",
            "createdAt": "2025-09-15T15:34:35.633794",
            "updatedAt": "2025-10-17T16:00:51.987719",
            "productId": 1
        },
        {
            "id": 2,
            "price": 25990000.00,
            "status": "OUT_OF_STOCK",
            "imageUrl": "1757926602911_12-hinh-anh-iphone-15-pro-max-didongviet.jpg",
            "quantity": 0,
            "memory": "128",
            "sku": "IP15-WHITE-128",
            "color": "white",
            "specifications": "{\n  \"ram\": \"8GB\",\n  \"storage\": \"256GB\",\n  \"chip\": \"A17 Pro\",\n  \"screen_size\": \"6.7 inch\",\n  \"battery\": \"4422 mAh\",\n  \"camera\": \"48MP + 12MP + 12MP\",\n  \"os\": \"iOS 17\",\n  \"weight\": \"221g\"\n}",
            "slug": "iphone-15-pro-max-blue-256gb",
            "createdAt": "2025-09-15T15:56:42.935972",
            "updatedAt": "2025-09-15T15:56:42.935972",
            "productId": 1
        },
        {
            "id": 3,
            "price": 26000000.00,
            "status": "ACTIVE",
            "imageUrl": "1757927141517_hinh-anh-iphone-15-pro-max-sieu-dep-12.webp",
            "quantity": 20,
            "memory": "128",
            "sku": "IP15-RED-128",
            "color": "red",
            "specifications": "{\n  \"ram\": \"8GB\",\n  \"storage\": \"256GB\",\n  \"chip\": \"A17 Pro\",\n  \"screen_size\": \"6.7 inch\",\n  \"battery\": \"4422 mAh\",\n  \"camera\": \"48MP + 12MP + 12MP\",\n  \"os\": \"iOS 17\",\n  \"weight\": \"221g\"\n}",
            "slug": "iphone-15-pro-max-red-128gb",
            "createdAt": "2025-09-15T16:05:41.577819",
            "updatedAt": "2025-10-17T15:59:59.694211",
            "productId": 1
        },
        {
            "id": 4,
            "price": 30000000.00,
            "status": "ACTIVE",
            "imageUrl": "https://res.cloudinary.com/dcv3lxcux/image/upload/v1760153536/apple-devices/e56079ec-78ec-4e2d-b357-d61a2dff96ff.webp",
            "quantity": 9,
            "memory": null,
            "sku": "IP15-RED-256",
            "color": "red",
            "specifications": "{\n  \"ram\": \"8GB\",\n  \"storage\": \"256GB\",\n  \"chip\": \"A17 Pro\",\n  \"screen_size\": \"6.7 inch\",\n  \"battery\": \"4422 mAh\",\n  \"camera\": \"48MP + 12MP + 12MP\",\n  \"os\": \"iOS 17\",\n  \"weight\": \"221g\"\n}",
            "slug": "iphone-15-pro-max-red-256gb",
            "createdAt": "2025-10-11T10:32:17.165468",
            "updatedAt": "2025-10-11T11:07:07.796058",
            "productId": 1
        },
        {
            "id": 5,
            "price": 30000000.00,
            "status": "ACTIVE",
            "imageUrl": "https://res.cloudinary.com/dcv3lxcux/image/upload/v1760675814/apple-devices/6c944c39-042e-44ce-8aa2-51e24d0e337d.webp",
            "quantity": 25,
            "memory": "256",
            "sku": "IP15-PINK-256",
            "color": "pink",
            "specifications": "{\n  \"ram\": \"8GB\",\n  \"storage\": \"256GB\",\n  \"chip\": \"A17 Pro\",\n  \"screen_size\": \"6.7 inch\",\n  \"battery\": \"4422 mAh\",\n  \"camera\": \"48MP + 12MP + 12MP\",\n  \"os\": \"iOS 17\",\n  \"weight\": \"221g\"\n}",
            "slug": "iphone-15-pro-max-pink-256gb",
            "createdAt": "2025-10-17T11:36:54.48433",
            "updatedAt": "2025-10-17T11:36:54.48433",
            "productId": 1
        },
        {
            "id": 6,
            "price": 30000000.00,
            "status": "ACTIVE",
            "imageUrl": "https://res.cloudinary.com/dcv3lxcux/image/upload/v1760690952/apple-devices/be7c7878-f9c7-4b0c-a9ad-80dcd8ee4bc7.webp",
            "quantity": 20,
            "memory": "256",
            "sku": "IP15-YELOW-256",
            "color": "yellow",
            "specifications": "{\n  \"ram\": \"8GB\",\n  \"storage\": \"256GB\",\n  \"chip\": \"A17 Pro\",\n  \"screen_size\": \"6.7 inch\",\n  \"battery\": \"4422 mAh\",\n  \"camera\": \"48MP + 12MP + 12MP\",\n  \"os\": \"iOS 17\",\n  \"weight\": \"221g\"\n}",
            "slug": "iphone-15-pro-max-yellow-256gb",
            "createdAt": "2025-10-17T15:49:12.095761",
            "updatedAt": "2025-10-17T15:49:12.096076",
            "productId": 1
        }
    ]
}
```

## Video test Api
https://youtu.be/MVMSWIjZhEc?si=UAHN8duYz5Ph_UPO

## Build and Deployment

**Local**
``` bash
mvn clean package
java -jar target/apple-devices-0.0.1-SNAPSHOT.jar
```
** Docker image **
``` bash
docker build -t apple-devices:0.0.1 .
docker run --name apple-devices -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://172.17.0.2:3306/apple-store apple-devices:0.0.1
```


