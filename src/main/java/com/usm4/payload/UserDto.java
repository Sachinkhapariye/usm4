package com.usm4.payload;

import lombok.Data;

@Data
public class UserDto { //Pojo class dto - data transfer object class
    private Long id;
    private String name;
    private String username;
    private String emailId;
    private String password;

    private String userRole;

}
//Hii This is UserDto Payload Class
//And I am sachin khapariye