package com.example.UberAuthService.helpers;

import com.example.UberProject_EntityService.models.Passenger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//why we need this class?
//Because spring security works on UserDetails polymorphic type for auth

public class AuthPassengerDetails extends Passenger implements UserDetails {
    private final String username; //the unique identifier for the user is email but spring security convention refers it as a username

    private final String password;

    public AuthPassengerDetails(Passenger passenger){
        this.username=passenger.getEmail();
        this.password=passenger.getPassword();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
