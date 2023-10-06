package com.genival.home.broker.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMinDTO {
    private Long id;
    private Integer year;
    private String name;
    private String email;
    private String phone;
    private Integer zipCode;
}
