package com.example.UberAuthService.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver extends BaseModel {
    private String name;

    @Column(nullable = false,unique = true)
    private String licenseNumber;

    private String phoneNumber;

    @OneToMany(mappedBy = "driver",fetch = FetchType.LAZY)
    private List<Booking> bookings;
}
