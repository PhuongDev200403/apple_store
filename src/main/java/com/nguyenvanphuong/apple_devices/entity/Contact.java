package com.nguyenvanphuong.apple_devices.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "contacts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "fullname")
    String fullName;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;

    @Column(name = "message")
    String message;

    @Column(name = "create_date")
    LocalDateTime createDate;

    @Column(name = "status")
    Status status;

    @Column(name = "lat")
    float lat;

    @Column(name = "lng")
    float lng;
}
