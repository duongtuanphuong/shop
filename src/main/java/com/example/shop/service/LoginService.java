package com.example.shop.service;

import com.example.shop.entity.User;
import com.example.shop.model.dto.UserDto;

public interface LoginService {
    User findByUsername(String username);

    User save(UserDto userDto);

    User customerSave(UserDto userDto);

    User updateUser(User user);
}
