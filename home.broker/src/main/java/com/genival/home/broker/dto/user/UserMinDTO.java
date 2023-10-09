package com.genival.home.broker.dto.user;

import com.genival.home.broker.entities.User;
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



    public UserMinDTO(User user) {
        this.id = user.getId();
        this.year = user.getYear();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.zipCode = user.getZipCode();
    }
}
