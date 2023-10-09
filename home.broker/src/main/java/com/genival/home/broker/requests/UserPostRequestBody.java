package com.genival.home.broker.requests;
import lombok.Data;

@Data
public class UserPostRequestBody {
    private Integer year;
    private String password;
    private String name;
    private String email;
    private String type;
    private String cpf;
    private String phone;
    private Integer zipCode;

}
