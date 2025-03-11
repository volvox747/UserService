package org.example.userservice.controllers;

import org.example.userservice.dto.LoginDTO;
import org.example.userservice.dto.SignUpDTO;
import org.example.userservice.dto.UserResponseDTO;
import org.example.userservice.exceptions.UserNotFoundException;
import org.example.userservice.models.Token;
import org.example.userservice.models.User;
import org.example.userservice.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController
{
    IUserService userService;
    UserController(IUserService userService)
    {
        this.userService=userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpDTO signUpDTO)
    {
        User user = userService.signup(signUpDTO.getName(), signUpDTO.getEmail(), signUpDTO.getPassword());
        ResponseEntity<User> response= new  ResponseEntity<>(user, HttpStatus.CREATED);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody LoginDTO loginDTO) throws UserNotFoundException
    {
        Token token = userService.login(loginDTO);
        ResponseEntity<Token> response= new  ResponseEntity<>(token, HttpStatus.OK);
        return response;
    }


    @GetMapping("/validate/{token}")
    public ResponseEntity<UserResponseDTO> validateToken(@PathVariable String token)
    {
        User user = userService.validateToken(token);
        UserResponseDTO userResponseDTO = UserResponseDTO.from(user);
        ResponseEntity<UserResponseDTO> response= new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
        return response;
    }

}
