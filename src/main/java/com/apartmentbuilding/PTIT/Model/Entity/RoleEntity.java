package com.apartmentbuilding.PTIT.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column()
    private String id;
    @Column()
    private String name;
    @Column(nullable = false, unique = true)
    private String code;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<UserEntity> users;

    public RoleEntity(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
