package org.example.userservice.services;

import org.example.userservice.dto.LoginDTO;
import org.example.userservice.exceptions.UserNotFoundException;
import org.example.userservice.models.Token;
import org.example.userservice.models.User;
public interface IUserService
{
    User signup(String name, String email, String hashedPassword);
    Token login(LoginDTO loginDTO) throws UserNotFoundException;
    User validateToken(String token);
}
