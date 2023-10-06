package com.genival.home.broker.dto.user;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private Integer year;
    private String password;
    private String name;
    private String email;
    private String type;
    private String cpf;
    private String phone;
    private Integer zipCode;
}
