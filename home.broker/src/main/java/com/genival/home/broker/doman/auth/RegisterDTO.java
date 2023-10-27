package com.genival.home.broker.doman.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;


public class RegisterDTO {

    @NotBlank(message = "Invalid field")
    @Pattern(regexp = "^[A-Z]+(.)*", message = "First letter of the name must be capitalized")
    private String name;
    @NotEmpty(message = "Invalid field")
    private String address;
    @CPF(message = "Invalid field")
    private String CPF;

    private String phone;

    @NotEmpty(message = "Invalid field")
    private String login;
    @NotEmpty(message = "Invalid field")
    private String password;
    @NotNull(message = "Invalid field")
    private Integer userType;

    public RegisterDTO() {
    }

    public RegisterDTO(String name, String address, String CPF, String phone, String login, String password, Integer userType) {
        this.name = name;
        this.address = address;
        this.CPF = CPF;
        this.phone = phone;
        this.login = login;
        this.password = password;
        this.userType = userType;
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
}
