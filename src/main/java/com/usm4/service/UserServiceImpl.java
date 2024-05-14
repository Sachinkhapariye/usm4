package com.usm4.service;

import com.usm4.entity.AppUser;
import com.usm4.payload.LoginDto;
import com.usm4.payload.UserDto;
import com.usm4.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private JWTService jwtService;

    private AppUserRepository appUserRepository;

    public UserServiceImpl(JWTService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        AppUser appUser = mapToEntity(userDto);
        AppUser saveUser = appUserRepository.save(appUser);
        UserDto dto = mapToDto(saveUser);
        return dto;
    }

    @Override
    public boolean deleteUserById(long id) {
        Optional<AppUser> byId = appUserRepository.findById(id);
        if (byId.isPresent()){
            appUserRepository.deleteById(id);
        }else {
            return false;
        }

        return true;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<AppUser> allUser = appUserRepository.findAll();
        List<UserDto> dtoAllUser = allUser.stream().map(u->mapToDto(u)).collect(Collectors.toList());
        return dtoAllUser;
    }

    @Override
    public UserDto updateUserById(UserDto userDto, long id) {
        Optional<AppUser> byId = appUserRepository.findById(id);
        if (byId.isPresent()){
            AppUser appUser = mapToEntity(userDto);
            appUser.setId(id);
            AppUser user = appUserRepository.save(appUser);
            return mapToDto(user);
        }
        return null;

    }

    @Override
    public UserDto findUserByUsername(String username) {
        Optional<AppUser> user = appUserRepository.findByUsername(username);
        if (user.isPresent()){
            AppUser appUser = user.get();
            return mapToDto(appUser);
        }
        return null;
    }

    @Override
    public String verifyLogin(LoginDto loginDto) {
        Optional<AppUser> byUsername = appUserRepository.findByUsername(loginDto.getUsername());
        AppUser appUser = new AppUser();
        if (byUsername.isPresent()){
            appUser = byUsername.get();
            if (BCrypt.checkpw(loginDto.getPassword(),appUser.getPassword())){
                 String UserToken = jwtService.generateToken(appUser);
                 return UserToken;
            }
        }
        return null;
    }









    public AppUser mapToEntity(UserDto userDto){
        AppUser appUser = new AppUser();
        appUser.setName(userDto.getName());
        appUser.setUsername(userDto.getUsername());
        appUser.setEmailId(userDto.getEmailId());
        appUser.setUserRole(userDto.getUserRole());
        appUser.setPassword(BCrypt.hashpw(userDto.getPassword(),BCrypt.gensalt(10)));
        return appUser;
    }
    public UserDto mapToDto(AppUser user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setEmailId(user.getEmailId());
        return dto;
    }


}
