package com.example.UberAuthService.dto;

import com.example.UberProject_EntityService.models.Passenger;
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

    public static PassengerDto toDto(Passenger p){
        return  PassengerDto.builder()
                .id(p.getId().toString())
                .name(p.getName())
                .email(p.getEmail())
                .password(p.getPassword())
                .phoneNumber(p.getPhoneNumber())
                .name(p.getName())
                .createdAt(p.getCreatedAt())
                .build();
    }
}
