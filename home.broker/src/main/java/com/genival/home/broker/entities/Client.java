package com.genival.home.broker.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_client")
public class Client implements UserDetails {
    private static long serial;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, unique = false)
    private String name;
    @Column(nullable = true, unique = false)
    private String address;
    @Column(nullable = true, unique = true)
    private String CPF;
    @Column(nullable = true, unique = false)
    private String phone;

    @Column(nullable = true, unique = true)
    private String login;
    @Column(nullable = true, unique = false)
    private String password;
    @Column(nullable = true, unique = false)
    private Integer userType;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Use the appropriate date format
    private LocalDateTime dateCreation;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Use the appropriate date format
    private LocalDateTime dateModification;

    private boolean hasAccount;
    private Integer quantityOrder;

    public Client() {
    }

    public Client(String name, String address, String cpf, String phone, String login, String encryptedPassword, Integer userType) {
        this.id = ++Client.serial;
        this.name = name;
        this.address = address;
        this.CPF = cpf;
        this.phone = phone;
        this.login = login;
        this.password = encryptedPassword;
        this.userType = userType;
        this.dateCreation = dateModification = LocalDateTime.now();
    }

    public static long getSerial() {
        return serial;
    }

    public static void setSerial(long serial) {
        Client.serial = serial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    public boolean isHasAccount() {
        return hasAccount;
    }

    public void setHasAccount(boolean hasAccount) {
        this.hasAccount = hasAccount;
    }

    public Integer getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(Integer quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.CPF);
        hash = 97 * hash + Objects.hashCode(this.login);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        return Objects.equals(this.CPF, other.CPF);
    }


    public String searchType(Integer type) {
        if (type == 1) {
            return "Administrador";
        } else if (type == 2) {
            return "Cliente";
        } else if (type == 3) {
            return "Cliente 2";
        } else {
            return null;
        }
    }

    public boolean validType(Integer id) {
        return id == 1 || id == 2;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userType == 1)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else if (this.userType == 2 || this.userType == 3) return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        else return null;
    }


    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
