package com.apartmentbuilding.PTIT.Model.Entity;

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

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column()
    private String id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column()
    private String password;
    @Column()
    private String fullName;
    @Column()
    private Date dateOfBirth;
    @Column()
    private String phoneNumber;
    @Column(unique = true)
    private String identityNumber;
    @Column()
    private String nationality;

    @ManyToOne
    @JoinColumn(name = "roleCode", referencedColumnName = "code")
    private RoleEntity role;

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    @Cascade(value = {CascadeType.PERSIST, CascadeType.REMOVE})
    private JwtEntity jwt;

    @OneToMany(mappedBy = "user")
    private List<ApartmentEntity> apartments;

    @OneToMany(mappedBy = "user")
    private List<NotificationTargetEntity> notifications;
}
