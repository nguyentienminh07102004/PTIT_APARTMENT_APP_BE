package com.apartmentbuilding.PTIT.Domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "jwts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtEntity {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "expiry")
    private Date expiry; // refresh token
    @Column(name = "device")
    private String device;
    @Column(name = "refreshToken")
    private String refreshToken;
    @OneToOne
    @JoinColumn(name = "userEmail", referencedColumnName = "email")
    private UserEntity user;
}
