package org.example.userservice.repos;

import org.example.userservice.models.Token;
import org.example.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token,Long>
{
    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(String token, boolean isDeleted, long expiryTime);
}
