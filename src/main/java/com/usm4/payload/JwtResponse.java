package com.usm4.payload;

import lombok.Data;

@Data
public class JwtResponse {

    private String type="Bearer";
    private String token;
}

//Hello JwtResponse