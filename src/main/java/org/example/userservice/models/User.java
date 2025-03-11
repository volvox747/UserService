package org.example.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel
{
private String name;
private String email;
private String hashedPassword;
private boolean isEmailVerified;
@ManyToMany
public List<Role> roles;
}
