package com.example.shop.model.dto;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    
    private String name;
    
    @Size(min = 5,max = 30,message = "Invalid username! (5-30 characters)")
    private String username;

    @Size(min = 5,max = 15,message = "Invalid password! (5-15 characters)")
    private String password;

    private String repeatPassword;
}
