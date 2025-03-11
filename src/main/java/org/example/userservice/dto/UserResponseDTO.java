package org.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.userservice.models.Role;
import org.example.userservice.models.User;

import java.util.List;

@Getter
@Setter
public class UserResponseDTO
{
    private String name;
    private String email;
    private List<Role> roleList;

    public static UserResponseDTO from(User user) {
        UserResponseDTO responseDto = new UserResponseDTO();
        responseDto.setEmail(user.getEmail());
        responseDto.setName(user.getName());
        responseDto.setRoleList(user.getRoles());

        return responseDto;
    }
}
