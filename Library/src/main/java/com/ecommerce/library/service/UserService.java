package com.ecommerce.library.service;

import com.ecommerce.library.dto.UserDto;
import com.ecommerce.library.model.User;

public interface UserService {
    UserDto save(UserDto userDto);
    User findByUsername(String username);
    User saveInfo(UserDto user);
}
