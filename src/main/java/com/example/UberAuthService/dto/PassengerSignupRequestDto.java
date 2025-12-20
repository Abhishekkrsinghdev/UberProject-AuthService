package com.example.UberAuthService.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PassengerSignupRequestDto {
   private String email;

   private String password;

   private String phoneNumber;

   private String name;
}
