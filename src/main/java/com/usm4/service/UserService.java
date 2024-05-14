package com.usm4.service;

import com.usm4.entity.AppUser;
import com.usm4.payload.LoginDto;
import com.usm4.payload.UserDto;

import java.util.List;

public interface UserService {
    public UserDto addUser(UserDto userDto);

    public boolean deleteUserById(long id);

    public List<UserDto> getAllUsers();

    public UserDto updateUserById(UserDto userDto, long id);

    public UserDto findUserByUsername(String username);

    public String verifyLogin(LoginDto loginDto);
}
