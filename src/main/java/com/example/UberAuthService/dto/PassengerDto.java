package com.example.UberAuthService.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengerDto {
    private String id;

    private String name;

    private String email;

    private String password;//encrypted password

    private String phoneNumber;

    private LocalDateTime createdAt;
}
