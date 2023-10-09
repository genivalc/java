package com.genival.home.broker.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "user_year", nullable = false, unique = false)
    private Integer year;
    @Column(name = "user_password", length = 8, nullable = false, unique = false)
    private String password;

    @Column(name = "user_name", nullable = false, unique = false)
    private String name;

    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_type", nullable = false, unique = false)
    private String type;

    @Column(name = "user_cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @Column(name = "user_phone", length = 11, nullable = false, unique = true)
    private String phone;

    @Column(name = "user_zipcode", length = 8, nullable = false, unique = true)
    private Integer zipCode;

    @CreatedDate
    @Column(name = "user_dateregister")
    private LocalDateTime dateRegister;

    @LastModifiedDate
    @Column(name = "user_dataupgrade")
    private LocalDateTime dataUpgrade;

    public User(Long id, Integer year, String password, String name, String email, String type, String cpf, String phone, Integer zipCode) {
        this.id = id;
        this.year = year;
        this.password = password;
        this.name = name;
        this.email = email;
        this.type = type;
        this.cpf = cpf;
        this.phone = phone;
        this.zipCode = zipCode;
        this.dateRegister = dateRegister;
        this.dataUpgrade = dataUpgrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
