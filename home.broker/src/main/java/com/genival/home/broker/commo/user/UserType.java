package com.genival.home.broker.commo.user;

import lombok.Data;

@Data
public class UserType {
    public enum UserTypeEnum{
        USER, ADMIN
    }
}
