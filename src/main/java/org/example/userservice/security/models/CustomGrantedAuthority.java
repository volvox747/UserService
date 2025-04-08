package org.example.userservice.security.models;



import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.example.userservice.models.Role;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
@Getter
@Setter
public class CustomGrantedAuthority implements GrantedAuthority {
    private String authority;
    public CustomGrantedAuthority(Role role) {
        this.authority = role.getValue();
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}