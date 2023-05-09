package com.hsms.mybatis.model;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private String name;

    private String password;

    private String address;

    private String email;

    private Integer state;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", state=" + state +
                '}';
    }
}
