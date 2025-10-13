# Sử dụng image JDK nhẹ
FROM amazoncorretto:21

# Đặt thư mục làm việc
WORKDIR /app

# Copy file jar vào container
COPY target/apple-devices-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Lệnh khởi chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]