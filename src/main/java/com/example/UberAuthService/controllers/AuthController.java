package com.example.UberAuthService.controllers;

import com.example.UberAuthService.dto.AuthRequestDto;
import com.example.UberAuthService.dto.AuthResponseDto;
import com.example.UberAuthService.dto.PassengerDto;
import com.example.UberAuthService.dto.PassengerSignupRequestDto;
import com.example.UberAuthService.services.AuthService;
import com.example.UberAuthService.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Value("${cookie.expiry}")
    private int cookieExpiry;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager,JwtService jwtService){
        this.authService=authService;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }
    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signup(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
       PassengerDto response= authService.signupPassenger(passengerSignupRequestDto);
       return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(),authRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
            String jwtToken=jwtService.createToken(authRequestDto.getEmail());
            ResponseCookie cookie=ResponseCookie.from("JwtToken",jwtToken)
                            .httpOnly(true)
                            .secure(false)
                            .maxAge(cookieExpiry)
                            .path("/")
                            .build();
            response.setHeader(HttpHeaders.SET_COOKIE,cookie.toString());
            return new ResponseEntity<>(AuthResponseDto.builder().success(true).build(),HttpStatus.OK);
        }
        return new ResponseEntity<>("Auth not successful",HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(HttpServletRequest request){
       return new ResponseEntity<>("Success",HttpStatus.OK);
    }
}
