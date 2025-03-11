package org.example.userservice.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.userservice.dto.LoginDTO;
import org.example.userservice.exceptions.UserNotFoundException;
import org.example.userservice.models.Token;
import org.example.userservice.models.User;
import org.example.userservice.repos.TokenRepo;
import org.example.userservice.repos.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    UserRepo userRepo;
    TokenRepo tokenRepo;
    BCryptPasswordEncoder passwordEncoder;
    public UserService(UserRepo userRepo,TokenRepo tokenRepo, BCryptPasswordEncoder passwordEncoder)
    {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User signup(String name, String email, String password)
    {
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setHashedPassword(passwordEncoder.encode(password));
        User user = userRepo.save(newUser);
        return user;
    }

    @Override
    public Token login(LoginDTO loginDTO) throws UserNotFoundException
    {
        Optional<User> user = userRepo.findByEmail(loginDTO.getEmail());
        if(user.isEmpty())
        {
            throw new UserNotFoundException("User with email "+loginDTO.getEmail()+" does not exist");
        }
        User existingUser = user.get();
        if(!passwordEncoder.matches(loginDTO.getPassword(),existingUser.getHashedPassword()))
        {
            throw new UserNotFoundException("Invalid Credentials");
        }
        // generate token and store it in DB
        Token token = generateToken(existingUser);
        token= tokenRepo.save(token);
        return token;
    }

    private Token generateToken(User user)
    {
        Token token = new Token();
        token.setValue(RandomStringUtils.randomAlphanumeric(10));
        token.setExpiryAt(System.currentTimeMillis()+36000000);
        token.setUser(user);
        return token;
    }

    @Override
    public User validateToken(String token)
    {
        Optional<Token> tokenResult = tokenRepo.findByValueAndDeletedAndExpiryAtGreaterThan(token,false,System.currentTimeMillis());

        if(tokenResult.isEmpty())
            return null;
        return tokenResult.get().getUser();
    }
}
