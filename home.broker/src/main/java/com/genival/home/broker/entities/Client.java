package com.genival.home.broker.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_client")
public class Client {
    private static long serial;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String CPF;
    private String phone;
    private String login;
    private String password;
    private Integer userType;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Use the appropriate date format
    private LocalDateTime dateCreation;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Use the appropriate date format
    private LocalDateTime dateModification;

    private boolean hasAccount;
    private Integer quantityOrder;

    public Client() {
        this.id = ++Client.serial;
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

//    @Override
//    public String toString() {
//        return "-----------------------------------------------------------------------\n"
//                + "Identificacao (id): " + id + "\n"
//                + "Nome              : " + name + "\n"
//                + "CPF               : " + CPF + "\n"
//                + "Endereco          : " + address + "\n"
//                + "Telefone          : " + password + "\n"
//                + "Login             : " + login + "\n"
//                + "Tipo de Usuário   : " + searchType(userType) + "\n"
//                + "Data Criacao      : " + dateCreation+ "\n"
//                + "Data Modificacao  : " + dateModification + "\n";
//    }
}
