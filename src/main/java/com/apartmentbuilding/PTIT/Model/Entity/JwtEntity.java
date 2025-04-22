package com.apartmentbuilding.PTIT.Model.Entity;

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
    @Column()
    private String id;
    @Column()
    private Date expiry;
    @Column()
    private String device;
    @Column()
    private String refreshToken;
    @OneToOne
    @JoinColumn(name = "userEmail", referencedColumnName = "email")
    private UserEntity user;
}
