package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.UserDto;
import com.ecommerce.library.model.User;
import com.ecommerce.library.repository.UserRepository;
import com.ecommerce.library.repository.RoleRepository;
import com.ecommerce.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto save(UserDto userDto) {
        User customer = new User();
        customer.setUsername(userDto.getUsername());
        customer.setPassword(userDto.getPassword());
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        customer.setCreated_at(LocalDateTime.now());
        customer.setUpdated_at(LocalDateTime.now());

        User customerSave = userRepository.save(customer);
        return mapToDto(customerSave);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveInfo(UserDto user) {
        User userSaved = userRepository.findByUsername(user.getUsername());
        userSaved.setPassword(user.getPassword());
        userSaved.setUpdated_at(LocalDateTime.now());
        return userRepository.save(userSaved);
    }

    private UserDto mapToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
