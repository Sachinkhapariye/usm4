package com.usm4.controller;

import com.usm4.entity.AppUser;
import com.usm4.exception.ResourceNotFound;
import com.usm4.payload.JwtResponse;
import com.usm4.payload.LoginDto;
import com.usm4.payload.UserDto;
import com.usm4.repository.AppUserRepository;
import com.usm4.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private UserService userService;



    public AuthController(UserService userService, AppUserRepository appUserRepository) {
        this.userService = userService;
    }

    @PostMapping("/{addUser}")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto){
        UserDto user = userService.addUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam long id){
        boolean status = userService.deleteUserById(id);
        if (status == false){
            try {
                throw new ResourceNotFound();
            }catch (Exception e){
                return new ResponseEntity<>("Could not be deleted!!",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("Record is deleted!!",HttpStatus.OK);
    }

    @GetMapping("/list-allUser")
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<UserDto> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@RequestParam long id){
        UserDto updateUser = userService.updateUserById(userDto, id);
        if (updateUser != null){
            return new ResponseEntity<>(updateUser,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findUser")
    public ResponseEntity<UserDto> findUser(@RequestParam String username){
        UserDto userByUsername = userService.findUserByUsername(username);
        if (userByUsername != null){
            return new ResponseEntity<>(userByUsername,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }


    @PostMapping("/login")
    public ResponseEntity<?> verifyLogin(@RequestBody LoginDto loginDto){
        String UserToken = userService.verifyLogin(loginDto);
        if (UserToken != null){
            JwtResponse response = new JwtResponse();
            response.setToken(UserToken);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid username/password",HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/profile")
    public AppUser getCurrentUser(@AuthenticationPrincipal AppUser appUser){
        return appUser;
    }
    //@AuthenticationPrincipal it automatically based on the current user loggedin it will automatically fetched the user detail

}
