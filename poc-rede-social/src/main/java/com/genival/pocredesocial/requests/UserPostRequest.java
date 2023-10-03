package com.genival.pocredesocial.requests;

import com.genival.pocredesocial.core.UserRolesTypeEnum;
import com.genival.pocredesocial.core.UserSexEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;


public class UserPostRequest {
    @NotEmpty(message = "The user cannot be empty")
    @NotNull(message = "The user cannot be null")
    private String username;
    private Date birthDate;
    private UserSexEnum sex;
    private String email;
    private String phone;
    private String password;
    private UserRolesTypeEnum roles;
}
