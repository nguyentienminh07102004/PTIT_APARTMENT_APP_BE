package com.apartmentbuilding.PTIT.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "identityNumber", unique = true)
    private String identityNumber;

    @ManyToOne
    @JoinColumn(name = "roleCode", referencedColumnName = "code")
    private RoleEntity role;

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    @Cascade(value = {CascadeType.PERSIST, CascadeType.REMOVE})
    private JwtEntity jwt;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ApartmentEntity> apartments;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<NotificationEntity> notifications;
}
